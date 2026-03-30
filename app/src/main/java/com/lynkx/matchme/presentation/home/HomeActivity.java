package com.lynkx.matchme.presentation.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lynkx.matchme.R;
import com.lynkx.matchme.presentation.adapter.MatchAdapter;
import com.lynkx.matchme.presentation.settings.SettingsActivity;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MatchAdapter adapter;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 1. On trouve le RecyclerView dans XML
        recyclerView = findViewById(R.id.recycler_matchs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. On récupère le ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // 3. On observe la liste de matchs
        viewModel.getMatches().observe(this, list -> {
            View emptyState = findViewById(R.id.empty_state); // On récupère le nuage

            if (list != null && !list.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                emptyState.setVisibility(View.GONE);

                // On donne la liste, le nuage et le recycler à l'adapter
                adapter = new MatchAdapter(list, emptyState, recyclerView);
                recyclerView.setAdapter(adapter);

                TextView txtCount = findViewById(R.id.txt_match_count);
                txtCount.setText(list.size() + " profils compatibles");
            } else {
                recyclerView.setVisibility(View.GONE);
                emptyState.setVisibility(View.VISIBLE);

                TextView txtCount = findViewById(R.id.txt_match_count);
                txtCount.setText("0 profils compatibles");
            }
        });
        viewModel.loadMatchsDirectement();

        ImageButton btnSettings = findViewById(R.id.btn_settings);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. On crée l'Intent pour aller vers SettingsActivity
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
