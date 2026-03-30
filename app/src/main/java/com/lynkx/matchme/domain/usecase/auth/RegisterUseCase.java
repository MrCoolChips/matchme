package com.lynkx.matchme.domain.usecase.auth;

import com.lynkx.matchme.domain.model.AuthResult;
import com.lynkx.matchme.domain.repository.AuthRepository;

public class RegisterUseCase {

    private final AuthRepository repo;

    public RegisterUseCase(AuthRepository repo) {
        this.repo = repo;
    }

    public AuthResult execute(String email, String pass, String birthday, String langue, String pseudo) {
        return repo.register(email, pass, birthday, langue, pseudo);
    }
}