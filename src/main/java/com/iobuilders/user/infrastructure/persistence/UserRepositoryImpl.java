package com.iobuilders.user.infrastructure.persistence;

import com.iobuilders.user.domain.UserRepository;
import com.iobuilders.user.domain.dto.UserDTO;
import com.iobuilders.user.domain.dto.UserID;
import com.iobuilders.user.domain.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {


    private final UserJPARepository userJPARepository;

    @Autowired
    public UserRepositoryImpl(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public UserID create(UserDTO user) {
        UserEntity entity = getEntityFrom(user);
        UserEntity response = userJPARepository.save(entity);

        return new UserID(response.getId());
    }

    @Override
    public void delete(Long id) {
        userJPARepository.deleteById(id);
    }

    @Override
    public UserDTO update(Long id, UserDTO user) {
        UserEntity oldEntity = userJPARepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        oldEntity.setName(user.getName());
        oldEntity.setSurname(user.getSurname());

        UserEntity newEntity = userJPARepository.save(oldEntity);

        return getDTOFrom(newEntity);
    }

    @Override
    public UserDTO findById(Long id) {
        UserEntity userEntity = userJPARepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return getDTOFrom(userEntity);
    }

    @Override
    public UserDTO findByUserNameAndPassword(String userName, String password) {
        UserEntity userEntity = userJPARepository.findByUserNameAndPassword(userName, password).orElseThrow(() -> new UserNotFoundException(userName));
        return getDTOFrom(userEntity);
    }


    private UserEntity getEntityFrom(UserDTO user) {
        return UserEntity.builder()
                .userName(user.getUserName())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .build();
    }

    private UserDTO getDTOFrom(UserEntity userEntity) {
        return new UserDTO(userEntity.getId(), userEntity.getUserName(), userEntity.getPassword(), userEntity.getName(), userEntity.getSurname());
    }
}
