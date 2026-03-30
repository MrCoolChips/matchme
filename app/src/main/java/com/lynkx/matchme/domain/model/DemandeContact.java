package com.lynkx.matchme.domain.model;

/**
 * Classe métier représentant une requête de mise en relation[cite: 351].
 */
public class DemandeContact {
    private final String id;
    private final Utilisateur expediteur;
    private final Utilisateur destinataire;
    private final String messageInitial;
    private StatutDemande statut;

    public DemandeContact(String id, Utilisateur expediteur, Utilisateur destinataire, String messageInitial, StatutDemande statut) {
        this.id = id;
        this.expediteur = expediteur;
        this.destinataire = destinataire;
        this.messageInitial = messageInitial;
        this.statut = statut;
    }

    // Méthodes métier définies dans le plan de développement
    public void accepter() {
        this.statut = StatutDemande.ACCEPTEE;
    }

    public void refuser() {
        this.statut = StatutDemande.REFUSEE;
    }

    // Getters
    public String getId() { return id; }
    public Utilisateur getExpediteur() { return expediteur; }
    public Utilisateur getDestinataire() { return destinataire; }
    public String getMessageInitial() { return messageInitial; }
    public StatutDemande getStatut() { return statut; }
}
