package com.lynkx.matchme.data.repository;

import org.json.JSONObject;

import com.lynkx.matchme.data.remote.AuthApi;
import com.lynkx.matchme.domain.model.AuthResult;
import com.lynkx.matchme.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {

    @Override
    public AuthResult register(String email, String pass, String birthday, String langue, String pseudo) {
        try {
            String json = AuthApi.register(email, pass, birthday, langue, pseudo);

            if (json == null || json.trim().isEmpty() || json.trim().equalsIgnoreCase("null")) {
                return AuthResult.fail("Réponse serveur vide");
            }

            JSONObject res = new JSONObject(json);

            int agree = 1;
            try { agree = res.getInt("agree"); }
            catch (Exception ignored) {
                try { agree = Integer.parseInt(res.optString("agree", "1")); }
                catch (Exception ignored2) { agree = 1; }
            }

            if (agree == 0) {
                String idUser = res.optString("id_user", "");
                if (idUser.isEmpty()) return AuthResult.fail("id_user manquant");
                return AuthResult.ok(idUser);
            }

            return AuthResult.fail("Email déjà utilisé");

        } catch (Exception e) {
            e.printStackTrace();
            return AuthResult.fail("Erreur: " + e.getMessage());
        }
    }
}