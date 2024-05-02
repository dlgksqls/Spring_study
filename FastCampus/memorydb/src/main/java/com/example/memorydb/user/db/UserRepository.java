package com.example.memorydb.user.db;

import com.example.memorydb.db.SimpleDataRepository;
import com.example.memorydb.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // select * from user where score >= ??
    public List<UserEntity> findAllByScoreGreaterThanEqual(int score);

    // select * from user where score >= ??
    // public List<UserEntity> findAllScoreGreaterThanEqual(int score);

    // select * from user where score >= ?? and score <= ??
    // min => ScoreGreaterThanEquel
    // max => ScoreLessThanEqual
    public List<UserEntity> findAllByScoreGreaterThanEqualAndScoreLessThanEqual(int min, int max);


}
