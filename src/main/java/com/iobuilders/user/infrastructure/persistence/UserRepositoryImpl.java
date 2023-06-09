package com.iobuilders.user.infrastructure.persistence;

import com.iobuilders.user.domain.UserRepository;
import com.iobuilders.user.domain.dto.User;
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
    public UserID create(User user) {
        UserEntity entity = getEntityFrom(user);
        UserEntity response = userJPARepository.save(entity);

        return new UserID(response.getId());
    }

    @Override
    public void delete(Long id) {
        userJPARepository.deleteById(id);
    }

    @Override
    public User update(Long id, User user) {
        UserEntity oldEntity = userJPARepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        oldEntity.setName(user.name());
        oldEntity.setSurname(user.surname());

        UserEntity newEntity = userJPARepository.save(oldEntity);

        return getDTOFrom(newEntity);
    }

    @Override
    public User findById(Long id) {
        UserEntity userEntity = userJPARepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return getDTOFrom(userEntity);
    }

    @Override
    public User findByUserNameAndPassword(String userName, String password) {
        UserEntity userEntity = userJPARepository.findByUserNameAndPassword(userName, password).orElseThrow(() -> new UserNotFoundException(userName));
        return getDTOFrom(userEntity);
    }


    private UserEntity getEntityFrom(User user) {
        return UserEntity.builder()
                .userName(user.userName())
                .password(user.password())
                .name(user.name())
                .surname(user.surname())
                .build();
    }

    private User getDTOFrom(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getUserName(), userEntity.getPassword(), userEntity.getName(), userEntity.getSurname());
    }
}
