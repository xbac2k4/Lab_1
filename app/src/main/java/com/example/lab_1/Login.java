package com.example.lab_1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText edt_username;
    EditText edt_password;
    Button btn_dangnhap;
    TextView tv_quenmatkhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ánh xạ
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        tv_quenmatkhau = findViewById(R.id.tv_quenmatkhau);
        btn_dangnhap = findViewById(R.id.btn_login);
        //
//        String username = getIntent().getStringExtra("username");
        edt_username.setText(getIntent().getStringExtra("username"));
        edt_password.setText(getIntent().getStringExtra("password"));
        // sự kiện click
        tv_quenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                intent.putExtra("email", edt_username.getText().toString().trim());
                startActivity(intent);
            }
        });
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLogin();
            }
        });
        findViewById(R.id.tv_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
    private void onClickLogin() {
        String email = edt_username.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        if (!email.isEmpty() || !password.isEmpty()) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Lấy thông tin tài khoản mới vừa đăng nhập
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            finishAffinity();
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else  {
                            Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        } else {
            Toast.makeText(this, "Vui lòng nhập thông tin đăng nhập", Toast.LENGTH_SHORT).show();
        }
    }
}