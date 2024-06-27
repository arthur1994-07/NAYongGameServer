package com.common.nayong.service;

import com.common.core.base.helper.StringHelper;
import com.common.core.web.permission.PermissionSystem;
import com.common.core.web.security.jwt.HttpJwtHelper;
import com.common.nayong.constants.SignerNameConstant;
import com.common.nayong.core.SignerProvider;
import com.common.nayong.data.SecurityTokenInfo;
import com.common.nayong.model.UserModel;
import com.common.nayong.permission.LicenseRightAnnotation;
import com.common.nayong.repo.UserRepository;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService {
    @Autowired private PermissionSystem<LicenseRightAnnotation> mPermissionSystem;
    @Autowired @Qualifier(SignerNameConstant.authentication)
    private SignerProvider mSignerProvider;
    @Autowired private UserRepository mUserRepository;

    public interface PasswordChecker {
        boolean check(String storedPassword, String superUserPassword);
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public synchronized SecurityTokenInfo login(String username, PasswordChecker checker) throws Throwable {
        if (StringHelper.isNullOrEmpty(username)) throw new Exception("Username not found");

        var userEntity = mUserRepository.findByUserID(username).orElseThrow(() -> new Exception("Username not found"));
        var storedPassword = userEntity.getUserPass();

        var stringMatch = checker.check(storedPassword, null);
        if (!stringMatch) throw new Error("User password does not match");
        var user = new UserModel(userEntity);
        return createTokenInfo(user);
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public SecurityTokenInfo anonymousLogin() throws Exception {
        return createTokenInfo(UserModel.anonymousUser(mPermissionSystem, true));
    }

    @Retryable(value = {LockAcquisitionException.class }, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public SecurityTokenInfo refreshToken(UserModel user) throws Exception {
        var anonymousModel = !user.isAnonymous() ? null :
                UserModel.anonymousUser(mPermissionSystem, user.getAuthorities().isEmpty());
        var entity = mUserRepository.findByUserNum(user.getId()).orElseThrow(() -> new Exception("User not found"));
        //TODO: give rights base on userType
        var newModel = anonymousModel != null ? anonymousModel :
                new UserModel(entity, user.getIdentity().idToken());
        return createTokenInfo(newModel);
    }


    private SecurityTokenInfo createTokenInfo(UserModel user) throws Exception {
        var duration = mSignerProvider.getExpirationPeriod();
        var token = HttpJwtHelper.create(mSignerProvider.getWriteSigner(), String.valueOf(user.getId()),
                duration, user.getClaims());
        return new SecurityTokenInfo(token, System.currentTimeMillis() + duration.toMillis());
    }
}


