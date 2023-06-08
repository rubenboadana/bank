package com.iobuilders.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserJPARepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserNameAndPassword(String userName, String password);
}