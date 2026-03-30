package com.lynkx.matchme.domain.repository;

import com.lynkx.matchme.domain.model.AuthResult;

public interface AuthRepository {
    AuthResult register(String email, String password, String birthdayDdMmYyyy, String langue, String pseudo);
    //AuthResult login(String email, String password);
}