package com.common.nayong.repo;

import com.common.nayong.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserID(String username);
    Optional<UserEntity> findByUserNum(long userNum);
}
