package com.lynkx.matchme.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.lynkx.matchme.DefProfilActivity;
import com.lynkx.matchme.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;

    private final String BASIC_USER = "36sah3KC3C";
    private final String BASIC_PASS = "v2RRh225Pc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> loginToServer(username, password)).start();
        });
    }

    private String basicAuthHeader(){
        String credentials = BASIC_USER + ":" + BASIC_PASS;
        return "Basic " + Base64.encodeToString(credentials.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
    }

    private void loginToServer(String username, String password) {

        try {
            URL url = new URL("http://www.nicolas-denis.net/lynkx/Scripts/LY-connexion.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty("Authorization", basicAuthHeader());
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("mail_user_4Wb3", username);
            jsonBody.put("pwd_user_x5X8", password);

            OutputStream os = conn.getOutputStream();
            os.write(jsonBody.toString().getBytes());
            os.close();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                sb.append(line);
            }

            String response = sb.toString();
            Log.d("LOGIN", response);

            runOnUiThread(() -> {
                try {
                    JSONObject json = new JSONObject(response);

                    if(json.getInt("agree") == 0){
                        Toast.makeText(this, "Login correct", Toast.LENGTH_SHORT).show();
                        String idUser = json.getString("id_user");

                        Intent intent = new Intent(this, DefProfilActivity.class);
                        intent.putExtra("id_user", idUser);
                        startActivity(intent);

                    } else {
                        Toast.makeText(this, "Login incorrect", Toast.LENGTH_SHORT).show();
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }
            });

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}