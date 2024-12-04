package com.example.tosamoemesto.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.tosamoemesto.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends BaseActivity {

    private ActivitySignInBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        binding.textView.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        binding.button.setOnClickListener(v -> {
            String email = binding.emailEt.getText().toString().trim();
            String pass = binding.passET.getText().toString().trim();

            if (!email.isEmpty() && !pass.isEmpty()) {
                // Проверка на корректный формат email
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Закрываем текущую активность
                        } else {
                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Введите корректный email!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Поля не должны быть пустыми!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Закрываем текущую активность
        }
    }
}