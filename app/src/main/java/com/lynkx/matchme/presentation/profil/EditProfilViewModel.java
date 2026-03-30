package com.lynkx.matchme.presentation.profil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lynkx.matchme.domain.model.AuthResult;
import com.lynkx.matchme.domain.usecase.profil.UpdateProfilUseCase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditProfilViewModel extends ViewModel {

    private final UpdateProfilUseCase useCase;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<AuthResult> result = new MutableLiveData<>();

    public EditProfilViewModel(UpdateProfilUseCase useCase) {
        this.useCase = useCase;
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<AuthResult> getResult() { return result; }

    public void submit(String idUser, String pseudo, String gender, String texte,
                       String country, String city, String phone, String loisirs) {
        loading.postValue(true);
        executor.execute(() -> {
            AuthResult r = useCase.execute(idUser, pseudo, gender, texte, country, city, phone, loisirs);
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