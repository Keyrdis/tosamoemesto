package com.example.tosamoemesto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.tosamoemesto.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.textView.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        });

        binding.button.setOnClickListener(v -> {
            String email = binding.emailEt.getText().toString();
            String pass = binding.passET.getText().toString();
            String confirmPass = binding.confirmPassEt.getText().toString();

            if (!email.isEmpty() && !pass.isEmpty() && !confirmPass.isEmpty()) {
                if (pass.equals(confirmPass)) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(this, SignInActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(this, "Пароль не совпадает", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Поля не должны быть пустыми!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

