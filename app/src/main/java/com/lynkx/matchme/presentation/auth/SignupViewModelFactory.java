package com.lynkx.matchme.presentation.auth;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lynkx.matchme.data.repository.AuthRepositoryImpl;
import com.lynkx.matchme.domain.usecase.auth.RegisterUseCase;

public class SignupViewModelFactory implements ViewModelProvider.Factory {

    private final Context appContext;

    public SignupViewModelFactory(Context appContext) {
        this.appContext = appContext.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // manual wiring
        AuthRepositoryImpl repo = new AuthRepositoryImpl();
        RegisterUseCase useCase = new RegisterUseCase(repo);
        return (T) new SignupViewModel(useCase);
    }
}