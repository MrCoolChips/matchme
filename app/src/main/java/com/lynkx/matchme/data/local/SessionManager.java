package com.lynkx.matchme.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String PREFS = "lynkx_prefs";
    public static final String KEY_ID_USER = "id_user";
    public static final String KEY_EMAIL = "email";

    public final SharedPreferences prefs;

    //egemen
    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    //egemen
    public void saveSession(String idUser, String email) {
        prefs.edit()
                .putString(KEY_ID_USER, idUser)
                .putString(KEY_EMAIL, email)
                .apply();
    }

    //egemen
    public String getIdUser() {
        return prefs.getString(KEY_ID_USER, null);
    }

    //egemen
    public void clear() {
        prefs.edit().clear().apply();
    }
}