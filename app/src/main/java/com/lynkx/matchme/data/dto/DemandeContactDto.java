package com.lynkx.matchme.data.dto;

import com.google.gson.annotations.SerializedName;

public class DemandeContactDto {
    public String idDemande;

    @SerializedName("id_expediteur")
    public String idExpediteur;

    @SerializedName("pseudo_expediteur")
    public String pseudoExpediteur;

    @SerializedName("msg_content")
    public String message;

    @SerializedName("status_code")
    public int statusCode;
}