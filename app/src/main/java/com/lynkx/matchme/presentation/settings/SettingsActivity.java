package com.lynkx.matchme.presentation.settings; // Vérifie bien que c'est ton package

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.lynkx.matchme.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // On lie le Java au XML que tu as déjà fait
        setContentView(R.layout.activity_settings);

        // On gère le bouton retour (la flèche en haut à gauche)
        ImageButton btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish()); // Ferme cette page et revient à l'accueil
        }
    }
}
