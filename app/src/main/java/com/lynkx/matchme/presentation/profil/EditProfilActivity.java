package com.lynkx.matchme.presentation.profil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lynkx.matchme.R;
import com.lynkx.matchme.data.local.SessionManager;
import com.lynkx.matchme.domain.model.AuthResult;

public class EditProfilActivity extends AppCompatActivity {

    private EditText etPseudo, etBirthday, etDesc;
    private RadioGroup rgGender;
    private Button btnContinue;

    private EditProfilViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Eğer layout adın farklıysa burayı değiştir
        setContentView(R.layout.activity_profile_form);

        bindViews();
        setupViewModel();
        bindActions();
    }

    private void bindViews() {
        etPseudo = findViewById(R.id.etPseudo);
        etBirthday = findViewById(R.id.etBirthday);
        etDesc = findViewById(R.id.etDesc);
        rgGender = findViewById(R.id.rgGender);
        btnContinue = findViewById(R.id.btnContinue);
    }

    private void setupViewModel() {
        vm = new ViewModelProvider(this, new EditProfilViewModelFactory())
                .get(EditProfilViewModel.class);

        vm.getLoading().observe(this, loading -> {
            boolean isLoading = loading != null && loading;
            btnContinue.setEnabled(!isLoading);
        });

        vm.getResult().observe(this, result -> {
            if (result == null) return;

            if (result.success) {
                Toast.makeText(this, "Profil mis à jour ✅", Toast.LENGTH_LONG).show();

                // TODO: prochaine étape -> questionnaire paysage (définition du profil)
                // startActivity(new Intent(this, DefinitionActivity.class));

            } else {
                Toast.makeText(this, result.message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void bindActions() {
        btnContinue.setOnClickListener(v -> submit());
    }

    private void submit() {
        String pseudo = etPseudo.getText().toString().trim();
        String birthday = etBirthday.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();

        if (pseudo.isEmpty() || pseudo.length() > 10) {
            Toast.makeText(this, "Pseudo requis (max 10)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!birthday.matches("\\d{2}/\\d{2}/\\d{4}")) {
            Toast.makeText(this, "Date invalide (jj/mm/aaaa)", Toast.LENGTH_SHORT).show();
            return;
        }

        int checked = rgGender.getCheckedRadioButtonId();
        if (checked == -1) {
            Toast.makeText(this, "Genre requis", Toast.LENGTH_SHORT).show();
            return;
        }

        String gender = (checked == R.id.rbFemme) ? "F" : (checked == R.id.rbHomme) ? "H" : "X";

        if (desc.length() > 200) desc = desc.substring(0, 200);

        String idUser = new SessionManager(this).getIdUser();
        if (idUser == null || idUser.trim().isEmpty()) {
            Toast.makeText(this, "Session manquante (id_user)", Toast.LENGTH_LONG).show();
            return;
        }

        // Defaults (sonra UI’ye koyarsınız)
        String country = "FR";
        String city = "PARIS";
        String phone = "";
        String loisirs = "";

        vm.submit(idUser, pseudo, gender, desc, country, city, phone, loisirs);
    }
}