package com.lynkx.matchme.data.repository;

import org.json.JSONObject;

import com.lynkx.matchme.data.remote.ProfilApi;
import com.lynkx.matchme.domain.model.AuthResult;
import com.lynkx.matchme.domain.repository.ProfilRepository;

public class ProfilRepositoryImpl implements ProfilRepository {

    @Override
    public AuthResult updateProfile(String idUser, String pseudo, String gender, String texte,
                                    String country, String city, String phone, String loisirs) {
        try {
            String json = ProfilApi.updateProfile(idUser, pseudo, gender, texte, country, city, phone, loisirs);

            if (json == null || json.trim().isEmpty() || json.trim().equalsIgnoreCase("null")) {
                return AuthResult.fail("Réponse serveur vide");
            }

            JSONObject res = new JSONObject(json);
            int agree = res.optInt("agree", 1);

            if (agree == 0) return AuthResult.ok(idUser);
            return AuthResult.fail("Échec mise à jour (agree=1)");

        } catch (Exception e) {
            e.printStackTrace();
            return AuthResult.fail("Erreur: " + e.getMessage());
        }
    }
}