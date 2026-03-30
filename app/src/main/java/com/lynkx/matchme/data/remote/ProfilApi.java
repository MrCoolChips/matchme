package com.lynkx.matchme.data.remote;

import org.json.JSONObject;

public class ProfilApi extends HttpClient {
    //egemen
    public static String updateProfile(
            String idUser,
            String pseudo,
            String gender,
            String texte,
            String country2,
            String city,
            String phone,
            String loisirs
    ) throws Exception {

        String url = BASE + "LY-maj_profil4.php";

        JSONObject body = new JSONObject();
        body.put("id_user_Yd39", idUser);
        body.put("pseudo_user_AFJQ", pseudo);
        body.put("gender_user_57Tu", gender);
        body.put("texte_iZ55", texte);
        body.put("country_user_7T2p", country2);
        body.put("city_user_G5s6", city);
        body.put("num_user_c67V", phone);
        body.put("loisirs_4yt2", loisirs);

        return postJson(url, body);
    }
}
