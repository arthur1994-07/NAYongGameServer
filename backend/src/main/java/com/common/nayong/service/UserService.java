package com.common.nayong.service;

import com.common.nayong.Entity.UserEntity;
import com.common.nayong.data.UserInfo;
import com.common.nayong.enumerate.UserType;
import com.common.nayong.repo.UserRepository;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    @Autowired private UserRepository mUserRepository;

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public void create(String username, String password, String email) throws Exception {
        if (username == null) throw new Exception("invalid username");
        if (password == null) throw new Exception("password is required");
        if (mUserRepository.findByUserID(username).isPresent()) throw new AlreadyBuiltException("username exist");

        var entity = UserEntity.builder().userID(username).userPass(password).userEmail(email).userType(UserType.player.code).build();
        mUserRepository.saveAndFlush(entity);
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public void remove(int id) throws Exception {
        if (id < 0) throw new Exception("Invalid user id");
        var entity = mUserRepository.findById(id).orElseThrow(() -> new Exception("user not found"));
        mUserRepository.delete(entity);
        mUserRepository.flush();
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserInfo.Data[] getAll() {
        var entities = mUserRepository.findAll();
        return entities.stream().map(UserInfo.Data::new).toArray(UserInfo.Data[]::new);
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public UserInfo.Data getById(int id) throws Exception {
        var entity = mUserRepository.findById(id).orElseThrow(() -> new Exception("user account not found"));
        return new UserInfo.Data(entity);
    }
}
