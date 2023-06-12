package com.iobuilders.infrastructure.persistence;

import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.dto.RegisterRequest;
import com.iobuilders.domain.dto.UserID;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {


    private final UserJPARepository userJPARepository;

    @Autowired
    public UserRepositoryImpl(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public UserID create(RegisterRequest registerRequest) {
        UserEntity entity = getEntityFrom(registerRequest);
        UserEntity response = userJPARepository.save(entity);

        return new UserID(response.getId());
    }

    @Override
    public Optional<RegisterRequest> findByUserNameAndPassword(String userName, String password) {
        Optional<UserEntity> userEntity = userJPARepository.findByUserNameAndPassword(userName, password);
        return getDTOFrom(userEntity);
    }

    @Override
    public Optional<RegisterRequest> findByUserName(String userName) {
        Optional<UserEntity> userEntity = userJPARepository.findByUserName(userName);
        return getDTOFrom(userEntity);
    }

    @Override
    public void bindWallet(RegisterRequest registerRequest, String walletId) {
        UserEntity entity = getEntityFrom(registerRequest);
        entity.setWalletId(walletId);
        userJPARepository.save(entity);
    }


    private UserEntity getEntityFrom(RegisterRequest registerRequest) {
        return UserEntity.builder()
                .id(registerRequest.id())
                .userName(registerRequest.userName())
                .password(registerRequest.password())
                .name(registerRequest.name())
                .surname(registerRequest.surname())
                .build();
    }

    private Optional<RegisterRequest> getDTOFrom(UserEntity userEntity) {
        return Optional.of(new RegisterRequest(userEntity.getId(), userEntity.getUserName(), userEntity.getPassword(), userEntity.getName(), userEntity.getSurname()));
    }

    private Optional<RegisterRequest> getDTOFrom(Optional<UserEntity> optionalUserEntity) {
        if (optionalUserEntity.isEmpty()) {
            return Optional.empty();
        }
        return getDTOFrom(optionalUserEntity.get());
    }
}
