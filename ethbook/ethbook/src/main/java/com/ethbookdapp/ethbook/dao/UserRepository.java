package com.ethbookdapp.ethbook.dao;

import com.ethbookdapp.ethbook.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByEmail(String email);
}
