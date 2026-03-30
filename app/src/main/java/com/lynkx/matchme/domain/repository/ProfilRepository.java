package com.lynkx.matchme.domain.repository;

import com.lynkx.matchme.domain.model.AuthResult;

public interface ProfilRepository {
    AuthResult updateProfile(String idUser, String pseudo, String gender, String texte,
                             String country, String city, String phone, String loisirs);
}