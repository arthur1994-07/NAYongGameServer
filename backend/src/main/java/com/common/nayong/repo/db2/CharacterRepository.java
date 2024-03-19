package com.common.nayong.repo.db2;

import com.common.nayong.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("RanGameS1DataSource")
public interface CharacterRepository extends JpaRepository<CharacterEntity, Integer> {
    Optional<CharacterEntity> findByUserNum(int userNum);
}
