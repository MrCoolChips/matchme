package com.lynkx.matchme.data.remote;

import org.json.JSONObject;

public class AuthApi extends HttpClient {

    //Kavi
    public static String login(String email, String password) throws Exception {
        String url = BASE + "LY-connexion.php";

        JSONObject body = new JSONObject();
        body.put("mail_user_4Wb3", email);
        body.put("pwd_user_x5X8", password);

        return postJson(url, body);
    }

    public static String register(String email, String password, String birthday_ddmmyyyy, String langue2, String pseudo) throws Exception {

        String url = BASE + "LY-inscription3.php";

        JSONObject body = new JSONObject();
        body.put("mail_user_6T4e", email);
        body.put("pwd_user_55tF", password);
        body.put("birthday_user_Fj39", birthday_ddmmyyyy);
        body.put("langue_b35Q", langue2);
        body.put("pseudo_user_5vC7", pseudo);

        return postJson(url, body);
    }
}
