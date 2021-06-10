package com.nhom1.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nhom1.authentication.AuthenticationFirebase;
import com.nhom1.database.QueryResponse;

public class Login extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnSignIn;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        if (user != null) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }else{

        }
        setControl();
        setEvent();
    }

    void setControl() {
        edtUsername = findViewById(R.id.editUsername);
        edtPassword = findViewById(R.id.editPassword);
        btnSignIn = findViewById(R.id.button_signin);
    }

    void setEvent() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationFirebase.signIn(edtUsername.getText().toString(), edtPassword.getText().toString(), new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }
        });
    }
}