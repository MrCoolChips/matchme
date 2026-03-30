package com.lynkx.matchme.domain.usecase.profil;

import com.lynkx.matchme.domain.model.AuthResult;
import com.lynkx.matchme.domain.repository.ProfilRepository;

public class UpdateProfilUseCase {

    private final ProfilRepository repo;

    public UpdateProfilUseCase(ProfilRepository repo) {
        this.repo = repo;
    }

    public AuthResult execute(String idUser, String pseudo, String gender, String texte, String country, String city, String phone, String loisirs) {
        return repo.updateProfile(idUser, pseudo, gender, texte, country, city, phone, loisirs);
    }
}