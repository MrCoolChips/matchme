package com.lynkx.matchme.presentation.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lynkx.matchme.domain.model.AuthResult;
import com.lynkx.matchme.domain.usecase.auth.RegisterUseCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignupViewModel extends ViewModel {

    private final RegisterUseCase registerUseCase;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<AuthResult> result = new MutableLiveData<>();

    private String lastEmail = "";

    public SignupViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<AuthResult> getResult() { return result; }

    public void setLastEmail(String email) { this.lastEmail = email; }
    public String getLastEmail() { return lastEmail; }

    public void register(String email, String pass, String birthday, String langue, String pseudo) {
        loading.postValue(true);
        executor.execute(() -> {
            AuthResult r = registerUseCase.execute(email, pass, birthday, langue, pseudo);
            result.postValue(r);
            loading.postValue(false);
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}