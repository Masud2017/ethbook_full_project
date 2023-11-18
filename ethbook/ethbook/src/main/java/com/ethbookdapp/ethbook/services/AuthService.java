package com.ethbookdapp.ethbook.services;

import com.ethbookdapp.ethbook.models.ResponseStatus;
import com.ethbookdapp.ethbook.models.User;

public interface AuthService {
    ResponseStatus auth(String username,String password);
    ResponseStatus register(User user);
}
