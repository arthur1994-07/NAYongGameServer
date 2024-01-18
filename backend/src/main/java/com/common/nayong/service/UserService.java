package com.common.nayong.service;

import com.common.nayong.data.UserInfo;
import com.common.nayong.repo.UserRepository;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired private UserRepository mUserRepository;


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
