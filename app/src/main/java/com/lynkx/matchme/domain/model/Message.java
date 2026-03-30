package com.lynkx.matchme.domain.model;

public class Message {
    private final String id;
    private final String contenu;
    private final String date;
    private final boolean envoyerParMoi; // Pour savoir si on affiche la bulle à gauche ou à droite

    public Message(String id, String contenu, String date, boolean envoyerParMoi) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
        this.envoyerParMoi = envoyerParMoi;
    }

    // Getters
    public String getId() { return id; }
    public String getContenu() { return contenu; }
    public String getDate() { return date; }
    public boolean isEnvoyerParMoi() { return envoyerParMoi; }
}