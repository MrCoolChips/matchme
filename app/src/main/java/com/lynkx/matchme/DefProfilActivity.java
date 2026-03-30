package com.lynkx.matchme;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DefProfilActivity extends AppCompatActivity {

    ImageView imgLeft, imgRight;
    Button btnNext, btnBack;
    TextView tvSkip;

    int step = 0;
    int choix = -1;
    ArrayList<Integer> choixList = new ArrayList<>();
    String idUser;

    ArrayList<String> urlImagesLeft  = new ArrayList<>();
    ArrayList<String> urlImagesRight = new ArrayList<>();

    String credentials = "36sah3KC3C:v2RRh225Pc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_def_profil);

        imgLeft  = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        btnNext  = findViewById(R.id.btnNext);
        btnBack  = findViewById(R.id.btnBack);
        tvSkip   = findViewById(R.id.tvSkip);

        idUser = getIntent().getStringExtra("id_user");

        fetchQuestions();

        imgLeft.setOnClickListener(v -> {
            choix = 0;
            saveAndNext();
        });

        imgRight.setOnClickListener(v -> {
            choix = 1;
            saveAndNext();
        });

        btnNext.setOnClickListener(v -> {
            choix = -1;
            saveAndNext();
        });

        btnBack.setOnClickListener(v -> {
            if (step > 0) {
                step--;
                choix = (step < choixList.size()) ? choixList.get(step) : -1;
                loadImages();
            }
        });

        tvSkip.setOnClickListener(v -> finish());
    }

    private void saveAndNext() {
        if (step < choixList.size()) {
            choixList.set(step, choix);
        } else {
            choixList.add(choix);
        }

        if (choix != -1) {
            new Thread(this::sendChoice).start();
        }

        if (step < urlImagesLeft.size() - 1) {
            step++;
            choix = (step < choixList.size()) ? choixList.get(step) : -1;
            loadImages();
        } else {
            Toast.makeText(this, "Profil terminé !", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // 🔥 FETCH QUESTIONS
    private void fetchQuestions() {
        new Thread(() -> {
            try {
                URL url = new URL("http://www.nicolas-denis.net/lynkx/Scripts/LY-def_profil.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String auth = "Basic " + android.util.Base64.encodeToString(
                        credentials.getBytes(), android.util.Base64.NO_WRAP);

                conn.setRequestProperty("Authorization", auth);
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonSend = new JSONObject();
                jsonSend.put("id_user", idUser);

                OutputStream os = conn.getOutputStream();
                os.write(jsonSend.toString().getBytes());
                os.close();

                InputStream is = conn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) sb.append(line);
                reader.close();

                String response = sb.toString();
                Log.d("DEF_PROFIL_DEBUG", "REPONSE = " + response);

                JSONObject json = new JSONObject(response);
                JSONArray questions = json.getJSONArray("question");

                for (int i = 0; i < questions.length(); i++) {
                    JSONObject q = questions.getJSONObject(i);

                    if (q.getInt("etat") == 1) {

                        // 🔥 URL IMAGE DIRECTE (mais protégée → auth obligatoire)
                        String left  = "https://nicolas-denis.net/lynkx/Images/" + q.getInt("id_img1") + ".jpg";
                        String right = "https://nicolas-denis.net/lynkx/Images/" + q.getInt("id_img2") + ".jpg";

                        urlImagesLeft.add(left);
                        urlImagesRight.add(right);

                        Log.d("DEF_PROFIL_DEBUG", "IMG LEFT  = " + left);
                        Log.d("DEF_PROFIL_DEBUG", "IMG RIGHT = " + right);
                    }
                }

                runOnUiThread(this::loadImages);

            } catch (Exception e) {
                Log.e("DEF_PROFIL_DEBUG", "ERREUR FETCH", e);
            }
        }).start();
    }

    // 🔥 LOAD IMAGE AVEC AUTH (REMPLACE GLIDE)
    private void loadImages() {
        Log.d("DEF_PROFIL_DEBUG", "STEP = " + step);

        if (step < urlImagesLeft.size()) {

            loadImageWithAuth(urlImagesLeft.get(step), imgLeft);
            loadImageWithAuth(urlImagesRight.get(step), imgRight);
        }
    }

    // 🔥 CHARGEMENT IMAGE AUTH
    private void loadImageWithAuth(String imageUrl, ImageView imageView) {
        new Thread(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                String auth = "Basic " + android.util.Base64.encodeToString(
                        credentials.getBytes(), android.util.Base64.NO_WRAP);

                conn.setRequestProperty("Authorization", auth);

                int code = conn.getResponseCode();
                Log.d("DEF_PROFIL_DEBUG", "IMG CODE = " + code + " | " + imageUrl);

                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);

                runOnUiThread(() -> imageView.setImageBitmap(bitmap));

            } catch (Exception e) {
                Log.e("DEF_PROFIL_DEBUG", "ERREUR LOAD : " + imageUrl, e);
            }
        }).start();
    }

    // 🔥 SEND CHOICE
    private void sendChoice() {
        try {
            URL url = new URL("http://www.nicolas-denis.net/lynkx/Scripts/LY-def_profil.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String auth = "Basic " + android.util.Base64.encodeToString(
                    credentials.getBytes(), android.util.Base64.NO_WRAP);

            conn.setRequestProperty("Authorization", auth);
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();
            json.put("id_user", idUser);
            json.put("step", step);
            json.put("choix_list", new JSONArray(choixList));

            OutputStream os = conn.getOutputStream();
            os.write(json.toString().getBytes());
            os.close();

            int responseCode = conn.getResponseCode();
            Log.d("DEF_PROFIL_DEBUG", "SEND CODE = " + responseCode);

        } catch (Exception e) {
            Log.e("DEF_PROFIL_DEBUG", "ERREUR SEND", e);
        }
    }
}