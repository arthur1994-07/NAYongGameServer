package com.common.nayong.controller;

import com.common.core.web.struct.JsonRespond;
import com.common.nayong.data.UserInfo;
import com.common.nayong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    public static class IDRequest {
        public int id;
    }

    public static class CreateRequest {
        public String username;
        public String password;
        public String email;
    }

    @Autowired private UserService mService;

    @PostMapping(value = "/public/add")
    public ResponseEntity<JsonRespond<Void>> create(@RequestBody CreateRequest request) throws Throwable {
        mService.create(request.username, request.password, request.email);
        return ResponseEntity.ok(new JsonRespond<>(null));
    }

    @PostMapping(value = "/public/delete")
    public ResponseEntity<JsonRespond<Void>> remove(@RequestBody IDRequest request) throws Throwable {
        mService.remove(request.id);
        return ResponseEntity.ok(new JsonRespond<>(null));
    }

    @PostMapping(value = "/public/list")
    public ResponseEntity<JsonRespond<UserInfo.Data[]>> list() throws Throwable {
        return ResponseEntity.ok(new JsonRespond<>(mService.getAll()));
    }

    @PostMapping(value = "/public/get")
    public ResponseEntity<JsonRespond<UserInfo.Data>>get(@RequestBody IDRequest request) throws Throwable {
        var data = mService.getById(request.id);
        return ResponseEntity.ok(new JsonRespond<>(data));
    }
}
