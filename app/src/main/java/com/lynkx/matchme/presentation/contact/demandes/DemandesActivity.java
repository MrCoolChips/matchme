package com.lynkx.matchme.presentation.contact.demandes; // Ton package

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.lynkx.matchme.R;
import java.util.ArrayList;
import java.util.List;

public class DemandesActivity extends AppCompatActivity {

    private LinearLayout layoutEmpty;
    private RecyclerView recyclerView;
    // private ContactAdapter adapter; // Ton adapter pour plus tard

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_requests);

        // 1. Liaison des vues
        layoutEmpty = findViewById(R.id.layout_empty_contacts);
        recyclerView = findViewById(R.id.recycler_contact_requests);

        // Pour le moment liste vide
        List<String> demandesSimulees = new ArrayList<>();

        // 2. Gestion de l'affichage
        updateDisplay(demandesSimulees);
    }

    private void updateDisplay(List<String> list) {
        if (list == null || list.isEmpty()) {
            // Liste vide
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            // Liste avec données -> Affiche le Recycler
            layoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            // A faire
        }
    }
}
