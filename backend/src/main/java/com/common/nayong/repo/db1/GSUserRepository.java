package com.common.nayong.repo.db1;

import com.common.nayong.entity.GSUserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Qualifier("RanUserDataSource")
public interface GSUserRepository extends JpaRepository<GSUserEntity, Integer> {
    Optional<GSUserEntity> findByUserID(String username);
}
