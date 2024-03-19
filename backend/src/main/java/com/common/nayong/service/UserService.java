package com.common.nayong.service;

import com.common.nayong.entity.GSUserEntity;
import com.common.nayong.data.GSUserInfo;
import com.common.nayong.enumerate.UserType;
import com.common.nayong.repo.db1.GSUserRepository;
import com.common.nayong.repo.db2.CharacterRepository;
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
    @Autowired private GSUserRepository mGSUserRepository;
    @Autowired private CharacterRepository mCharacterRepository;

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public void create(String username, String password, String email) throws Exception {
        if (username == null) throw new Exception("invalid username");
        if (password == null) throw new Exception("password is required");
        if (mGSUserRepository.findByUserID(username).isPresent()) throw new AlreadyBuiltException("username exist");

        var gsUserEntity = GSUserEntity.builder().userID(username).userPass(password).userEmail(email).userType(UserType.player.code).build();
        mGSUserRepository.saveAndFlush(gsUserEntity);
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public void remove(int id) throws Exception {
        if (id < 0) throw new Exception("Invalid user id");
        var entity = mGSUserRepository.findById(id).orElseThrow(() -> new Exception("user not found"));
        mGSUserRepository.delete(entity);
        mGSUserRepository.flush();
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public GSUserInfo.Data[] getAll() {
        var gsEntities = mGSUserRepository.findAll();
        return gsEntities.stream().map(GSUserInfo.Data::new).toArray(GSUserInfo.Data[]::new);
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public GSUserInfo.Data getById(int id) throws Exception {
        var entity = mGSUserRepository.findById(id).orElseThrow(() -> new Exception("user account not found"));
        return new GSUserInfo.Data(entity);
    }
}
