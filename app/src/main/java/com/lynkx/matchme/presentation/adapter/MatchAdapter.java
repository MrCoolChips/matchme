package com.lynkx.matchme.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.lynkx.matchme.R;
import com.lynkx.matchme.domain.model.Match;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private List<Match> matchList;
    private View emptyStateView;
    private RecyclerView recyclerView;

    public MatchAdapter(List<Match> matchList, View emptyStateView, RecyclerView recyclerView) {
        this.matchList = matchList;
        this.emptyStateView = emptyStateView;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matchList.get(position);

        holder.txtPseudo.setText(match.getPseudo());
        holder.txtInfo.setText(match.getAge() + "25 ans");

        // BOUTON RETIRER (X)
        holder.btnRemove.setOnClickListener(v -> {
            int currentPos = holder.getAdapterPosition();
            if (currentPos != RecyclerView.NO_POSITION) {
                matchList.remove(currentPos);
                notifyItemRemoved(currentPos);
                notifyItemRangeChanged(currentPos, matchList.size());

                // SI LA LISTE EST VIDE, ON SWITCH LES VUES
                if (matchList.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyStateView.setVisibility(View.VISIBLE);
                }
            }
        });

        // BOUTON ENVOYER () : Demande de contact (serveur)
        holder.btnAdd.setOnClickListener(v -> {
            // Ici on appellera la méthode du ViewModel pour le script LY-echanges.php
        });
    }

    @Override
    public int getItemCount() {
        return matchList != null ? matchList.size() : 0;
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProfile;
        TextView txtPseudo, txtInfo;
        ImageButton btnAdd, btnRemove;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfile = itemView.findViewById(R.id.img_match_player);
            txtPseudo = itemView.findViewById(R.id.txt_match_pseudo);
            txtInfo = itemView.findViewById(R.id.txt_match_info);
            btnAdd = itemView.findViewById(R.id.btn_add_match);
            btnRemove = itemView.findViewById(R.id.btn_remove_match);
        }
    }
}