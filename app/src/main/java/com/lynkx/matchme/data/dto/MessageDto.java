package com.lynkx.matchme.data.dto;

import com.google.gson.annotations.SerializedName;

public class MessageDto {
    @SerializedName("id_msg_Gw95")
    public String id;

    @SerializedName("id_expediteur_h97E")
    public String idExpediteur;

    @SerializedName("id_destinataire_h97E")
    public String idDestinataire;

    @SerializedName("contenu_msg_h97E")
    public String contenu;

    @SerializedName("date_envoi_h97E")
    public String date;

    @SerializedName("lu_h97E")
    public int estLu; // 0 pour non lu, 1 pour lu
}