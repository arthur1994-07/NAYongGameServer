package com.common.nayong.service;

import com.common.core.base.helper.StringHelper;
import com.common.core.web.security.jwt.HttpJwtHelper;
import com.common.nayong.core.SignerProvider;
import com.common.nayong.data.SecurityTokenInfo;
import com.common.nayong.model.UserModel;
import com.common.nayong.repo.db1.GSUserRepository;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    public interface PasswordChecker {
        boolean check(String storedPassword, String superUserPassword);
    }

    @Autowired private SignerProvider mSignerProvider;

    @Autowired private GSUserRepository mGSUserRepository;

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public synchronized SecurityTokenInfo login(String username, PasswordChecker checker) throws Throwable {
        if (StringHelper.isNullOrEmpty(username)) throw new Exception("Username not found");

        var userEntity = mGSUserRepository.findByUserID(username).orElseThrow(() -> new Exception("Username not found"));
        var storedPassword = userEntity.getUserPass();

        var stringMatch = checker.check(storedPassword, null);
        if (!stringMatch) throw new Error("User password does not match");
        var user = new UserModel(userEntity);
        return createTokenInfo(user);
    }

    public SecurityTokenInfo createTokenInfo(UserModel user) throws Exception {
        var duration = mSignerProvider.getExpirationPeriod();
        var token = HttpJwtHelper.create(mSignerProvider.getWriteSigner(), String.valueOf(user.getId()),
                duration, user.getClaims());
        return new SecurityTokenInfo(token, System.currentTimeMillis() + duration.toMillis());
    }
}


