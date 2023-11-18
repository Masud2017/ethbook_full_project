package com.ethbookdapp.ethbook.controllers;

import com.ethbookdapp.ethbook.models.ResponseStatus;
import com.ethbookdapp.ethbook.models.User;
import com.ethbookdapp.ethbook.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @GetMapping(value = "/auth")
    public ResponseEntity<ResponseStatus> auth(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(this.authService.auth(username,password));
    }

    @GetMapping(value = "/signup")
    public ResponseEntity<ResponseStatus> register(@RequestBody User user) {
        return ResponseEntity.ok(this.authService.register(user));
    }
}
