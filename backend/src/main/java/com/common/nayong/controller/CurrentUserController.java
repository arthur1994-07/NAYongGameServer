package com.common.nayong.controller;

import com.common.core.web.permission.PermissionSystem;
import com.common.core.web.struct.JsonRespond;
import com.common.nayong.data.UserInfo;
import com.common.nayong.model.UserModel;
import com.common.nayong.permission.LicenseRightAnnotation;
import com.common.nayong.service.AuthService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/current-user")
public class CurrentUserController {
    public static class ChangePasswordRequest {
        public String currentPassword;
        public String newPassword;
    }

    @Autowired private PermissionSystem<LicenseRightAnnotation> mPermissionSystem;

    @PostMapping(value = "/public/get-profile")
    public ResponseEntity<JsonRespond<UserInfo.Base>> getUserProfile() throws Throwable {
        var currentUser = UserModel.getCurrent();
//        if (currentUser == null) return null;
        if (currentUser == null) {
            var anonymous = UserModel.anonymousUser(mPermissionSystem, true);
            var anonymousUser = new UserInfo.Base();
            anonymousUser.UserId = anonymous.getIdentity().getUsername();
            anonymousUser.UserNum = (int) anonymous.getId();
            anonymousUser.UserType = anonymous.getIdentity().getUserType();
            return ResponseEntity.ok(new JsonRespond<>(anonymousUser));
        }
        var user = new UserInfo.Base();
        user.UserId = currentUser.getIdentity().getUsername();
        user.UserNum = (int) currentUser.getId();
        user.UserEmail = currentUser.getIdentity().getEmail();
        user.UserType = currentUser.getIdentity().getUserType();

        return ResponseEntity.ok(new JsonRespond<>(user));
    }
}
