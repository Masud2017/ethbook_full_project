package com.ethbookdapp.ethbook.services;

import com.ethbookdapp.ethbook.models.ResponseStatus;
import com.ethbookdapp.ethbook.models.User;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceImpl implements AuthService{
    @Override
    public ResponseStatus auth(String username, String password) {
        return null;
    }

    @Override
    public ResponseStatus register(User user) {
        return null;
    }
}
