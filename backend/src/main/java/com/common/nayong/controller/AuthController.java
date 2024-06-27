package com.common.nayong.controller;

import com.common.core.base.helper.StringHelper;
import com.common.core.web.struct.JsonRespond;
import com.common.nayong.data.SecurityTokenInfo;
import com.common.nayong.model.UserModel;
import com.common.nayong.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    public static class LoginRequest {
        public String username;
        public String password;
    }

    @Autowired private AuthService mAuthService;

    @PostMapping(value = "/public/login")
    public ResponseEntity<JsonRespond<SecurityTokenInfo>> login(@RequestBody LoginRequest request) throws Throwable {
        var token = mAuthService.login(request.username, (storedPassword, superUserPassword) ->
                superUserPassword == null ? StringHelper.equals(request.password, storedPassword) :
                    request.password.equals(superUserPassword));

        return ResponseEntity.ok(new JsonRespond<>(token));
    }

    @PostMapping(value = "/public/anonymous-login")
    public ResponseEntity<JsonRespond<SecurityTokenInfo>> anonymousLogin() throws Throwable {
        var token = mAuthService.anonymousLogin();
        return ResponseEntity.ok(new JsonRespond<>(token));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JsonRespond<SecurityTokenInfo>> refreshToken() throws Throwable {
        var authUser = UserModel.getCurrent();
        if (authUser == null) throw new Error("Token is expired");
        return ResponseEntity.ok(new JsonRespond<>(mAuthService.refreshToken(authUser)));
    }
}
