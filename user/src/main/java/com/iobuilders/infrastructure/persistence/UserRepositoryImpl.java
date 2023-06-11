package com.iobuilders.infrastructure.persistence;

import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.dto.User;
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
    public UserID create(User user) {
        UserEntity entity = getEntityFrom(user);
        UserEntity response = userJPARepository.save(entity);

        return new UserID(response.getId());
    }

    @Override
    public Optional<User> findByUserNameAndPassword(String userName, String password) {
        Optional<UserEntity> userEntity = userJPARepository.findByUserNameAndPassword(userName, password);
        return getDTOFrom(userEntity);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        Optional<UserEntity> userEntity = userJPARepository.findByUserName(userName);
        return getDTOFrom(userEntity);
    }

    @Override
    public void bindWallet(User user, String walletId) {
        UserEntity entity = getEntityFrom(user);
        entity.setWalletId(walletId);
        userJPARepository.save(entity);
    }


    private UserEntity getEntityFrom(User user) {
        return UserEntity.builder()
                .id(user.id())
                .userName(user.userName())
                .password(user.password())
                .name(user.name())
                .surname(user.surname())
                .build();
    }

    private Optional<User> getDTOFrom(UserEntity userEntity) {
        return Optional.of(new User(userEntity.getId(), userEntity.getUserName(), userEntity.getPassword(), userEntity.getName(), userEntity.getSurname()));
    }

    private Optional<User> getDTOFrom(Optional<UserEntity> optionalUserEntity) {
        if (optionalUserEntity.isEmpty()) {
            return Optional.empty();
        }
        return getDTOFrom(optionalUserEntity.get());
    }
}
