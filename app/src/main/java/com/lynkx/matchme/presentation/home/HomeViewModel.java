package com.lynkx.matchme.presentation.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lynkx.matchme.domain.model.Match;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    // Le LiveData permet à l'Activity d'être prévenue dès que la liste change
    private MutableLiveData<List<Match>> matches = new MutableLiveData<>();

    public LiveData<List<Match>> getMatches() {
        return matches;
    }

    // Cette méthode servira plus tard à charger les vraies données du PHP
    public void loadMatchsDirectement() {
        List<Match> testList = new ArrayList<>();
        // On ajoute un faux profil juste pour vérifier que l'affichage fonctionne
        /*testList.add(new Match("1", "Test Pseudo", "", "2003-02-08"));
        matches.setValue(testList);*/
    }
}