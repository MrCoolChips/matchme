package com.lynkx.matchme;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.util.Base64;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ProfilActivity extends AppCompatActivity {

    private TextView tvProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvProfil = findViewById(R.id.tvProfil);

        String idUser = getIntent().getStringExtra("id_user");
        if (idUser == null || idUser.isEmpty()) {
            Toast.makeText(this, "Erreur : id_user manquant", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Appel serveur dans un thread
        new Thread(() -> fetchProfil(idUser)).start();
    }

    private void fetchProfil(String idUser) {
        try {
            URL url = new URL("http://www.nicolas-denis.net/lynkx/Scripts/LY-rech_profil.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);

            // Auth HTTP BASIC
            String credentials = "36sah3KC3C:v2RRh225Pc";
            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
            conn.setRequestProperty("Authorization", auth);

            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");

            // JSON body
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id_user", idUser);

            OutputStream os = conn.getOutputStream();
            os.write(jsonBody.toString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();

            String responseText = sb.toString();
            Log.d("ProfilDebug", "Response: " + responseText);

            runOnUiThread(() -> {
                try {
                    JSONObject json = new JSONObject(responseText);

                    String pseudo = json.optString("pseudo_user", "N/A");
                    String gender = json.optString("gender_user", "N/A");
                    String birthday = json.optString("birthday_user", "N/A");
                    String country = json.optString("country_user", "N/A");
                    String city = json.optString("city_user", "N/A");
                    String texte = json.optString("texte", "");

                    String display = "Pseudo: " + pseudo + "\n"
                            + "Genre: " + gender + "\n"
                            + "Anniversaire: " + birthday + "\n"
                            + "Pays: " + country + "\n"
                            + "Ville: " + city + "\n"
                            + "Texte: " + texte;

                    tvProfil.setText(display);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ProfilActivity.this, "Erreur parsing JSON", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() ->
                    Toast.makeText(ProfilActivity.this, "Erreur connexion serveur", Toast.LENGTH_SHORT).show()
            );
        }
    }
}