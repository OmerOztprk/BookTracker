package com.omeroztoprak.booktracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    private Button btnSignup;
    private TextView tvLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.et_username_signup);
        etPassword = findViewById(R.id.et_password_signup);
        etConfirmPassword = findViewById(R.id.et_confirm_password_signup);
        btnSignup = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.tv_login);

        dbHelper = new DatabaseHelper(this);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Şifreler uyuşmuyor", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isUserAdded = dbHelper.addUser(username, password);
                    if (isUserAdded) {
                        Toast.makeText(SignupActivity.this, "Hesap başarıyla oluşturuldu!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Hesap oluşturulurken bir hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}