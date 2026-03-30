package com.lynkx.matchme;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // 🔥 Récupération des données
        String pseudo = getIntent().getStringExtra("pseudo");
        String idUser = getIntent().getStringExtra("id_user");

        // ✅ Affichage simple
        Toast.makeText(this, "Connecté : " + pseudo, Toast.LENGTH_LONG).show();
    }
}