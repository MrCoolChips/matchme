package com.lynkx.matchme.domain.model;

public class AuthResult {
    public final boolean success;
    public final String idUser;
    public final String message;

    public AuthResult(boolean success, String idUser, String message) {
        this.success = success;
        this.idUser = idUser;
        this.message = message;
    }

    public static AuthResult ok(String idUser) {
        return new AuthResult(true, idUser, "OK");
    }

    public static AuthResult fail(String msg) {
        return new AuthResult(false, "", msg);
    }
}
