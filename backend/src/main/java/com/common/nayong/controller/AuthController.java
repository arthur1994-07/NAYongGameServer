package com.common.nayong.controller;

import com.common.core.base.helper.StringHelper;
import com.common.core.web.struct.JsonRespond;
import com.common.nayong.data.SecurityTokenInfo;
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
}
