package com.common.nayong.controller;

import com.common.core.web.struct.JsonRespond;
import com.common.nayong.data.UserInfo;
import com.common.nayong.model.UserModel;
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

    @PostMapping(value = "/public/get-profile")
    public ResponseEntity<JsonRespond<UserInfo.Base>> getUserProfile() throws Throwable {
        var currentUser = UserModel.getCurrent();
        if (currentUser == null) return null;
        var user = new UserInfo.Base();
        user.UserId = currentUser.getIdentity().getUsername();
        user.UserNum = (int) currentUser.getId();
        user.UserEmail = currentUser.getIdentity().getEmail();
        user.UserType = currentUser.getIdentity().getUserType();

        return ResponseEntity.ok(new JsonRespond<>(user));
    }
}
