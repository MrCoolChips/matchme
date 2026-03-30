package com.lynkx.matchme.domain.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Match{
    private String id_utilisateur;
    private String pseudo;
    private String photo;
    private String dateAnniversaire;

    public Match(String id_utilisateur, String pseudo, String photo, String dateAnniversaire) {
        this.id_utilisateur = id_utilisateur;
        this.pseudo = pseudo;
        this.photo = photo;
        this.dateAnniversaire = dateAnniversaire;
    }

    // Getters
    public String getId_utilisateur() { return id_utilisateur; }
    public String getPseudo() { return pseudo; }
    public String getPhoto() { return photo; }
    public String getDateAnniversaire() { return dateAnniversaire; }

    public int getAge() {
        if (dateAnniversaire == null || dateAnniversaire.isEmpty()) return 0;

        try {
            // 1. Transformer la String en Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
            Date birthDate = sdf.parse(dateAnniversaire);

            // 2. Créer les calendriers pour comparer
            Calendar dob = Calendar.getInstance();
            dob.setTime(birthDate);
            Calendar today = Calendar.getInstance();

            // 3. Calculer la différence d'années
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            // 4. Ajuster si l'anniversaire n'est pas encore passé cette année
            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        } catch (Exception e) {
            return 0;
        }
    }
}
