package com.iobuilders.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserJPARepository extends JpaRepository<UserEntity, Long> {
}