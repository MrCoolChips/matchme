package com.lynkx.matchme.presentation.profil;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lynkx.matchme.data.repository.ProfilRepositoryImpl;
import com.lynkx.matchme.domain.usecase.profil.UpdateProfilUseCase;

public class EditProfilViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ProfilRepositoryImpl repo = new ProfilRepositoryImpl();
        UpdateProfilUseCase useCase = new UpdateProfilUseCase(repo);
        return (T) new EditProfilViewModel(useCase);
    }
}