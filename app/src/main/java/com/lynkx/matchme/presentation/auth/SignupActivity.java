package com.lynkx.matchme.presentation.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lynkx.matchme.R;
import com.lynkx.matchme.data.local.SessionManager;
import com.lynkx.matchme.presentation.profil.EditProfilActivity;

public class SignupActivity extends AppCompatActivity {

    private SignupViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ImageButton btnBack = findViewById(R.id.btn_back);
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);
        EditText etConfirmPassword = findViewById(R.id.et_password_confirm);
        CheckBox cbTerms = findViewById(R.id.cb_terms);
        Button btnSignUp = findViewById(R.id.btn_signup_submit);

        btnBack.setOnClickListener(v -> finish());

        // --- ViewModel (manual wiring via Factory) ---
        vm = new ViewModelProvider(this, new SignupViewModelFactory(getApplicationContext()))
                .get(SignupViewModel.class);

        // --- Observers ---
        vm.getLoading().observe(this, isLoading -> {
            boolean loading = isLoading != null && isLoading;
            btnSignUp.setEnabled(!loading);
        });

        vm.getResult().observe(this, result -> {
            if (result == null) return;

            if (result.success) {
                // Save session (id_user)
                new SessionManager(this).saveSession(result.idUser, vm.getLastEmail());

                Toast.makeText(this, "Inscription OK ✅", Toast.LENGTH_SHORT).show();

                // Go to profile form (EditProfilActivity in your architecture)
                startActivity(new Intent(this, EditProfilActivity.class));
                finish();
            } else {
                Toast.makeText(this, result.message, Toast.LENGTH_LONG).show();
            }
        });

        // --- Click ---
        btnSignUp.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString();
            String confirmPass = etConfirmPassword.getText().toString();

            // Validations
            if (email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass.length() < 6) {
                Toast.makeText(this, "Mot de passe trop court (min 6)", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!pass.equals(confirmPass)) {
                Toast.makeText(this, getString(R.string.error_password_match), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!cbTerms.isChecked()) {
                Toast.makeText(this, getString(R.string.error_terms), Toast.LENGTH_SHORT).show();
                return;
            }

            // API prof needs these too (temp for now)
            String birthdayTemp = "14/07/1989";
            String langue = "fr";
            String pseudoTemp = "TempUser"; // <= 10 (you will ask later on profile page)

            vm.setLastEmail(email);
            vm.register(email, pass, birthdayTemp, langue, pseudoTemp);
        });
    }
}