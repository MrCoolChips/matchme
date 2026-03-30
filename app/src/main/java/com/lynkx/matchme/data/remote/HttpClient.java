package com.lynkx.matchme.data.remote;

import android.util.Base64;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient {
    public static final String BASE = "http://www.nicolas-denis.net/lynkx/Scripts/";

    private static final String BASIC_USER = "36sah3KC3C";
    private static final String BASIC_PASS = "v2RRh225Pc";

    //egemen
    private static String readAll(InputStream is) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        return sb.toString();
    }

    //egemen
    private static String basicAuthHeader() {
        String credentials = BASIC_USER + ":" + BASIC_PASS;
        String encoded = Base64.encodeToString(credentials.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
        return "Basic " + encoded;
    }

    //egemen
    public static String postJson(String urlStr, JSONObject jsonBody) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", basicAuthHeader());
        conn.setDoOutput(true);

        byte[] payload = jsonBody.toString().getBytes(StandardCharsets.UTF_8);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload);
        }

        int code = conn.getResponseCode();
        InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();
        return readAll(is);
    }
}
