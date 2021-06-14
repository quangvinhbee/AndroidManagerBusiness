package com.nhom1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nhom1.database.DbHelper;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    private LinearLayout btn_department, btn_employee, btn_notification, btn_statistical, btn_resetPassword, btn_exit;
    private ImageView imgAvt;
    TextView tvHello;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    private boolean isAdmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper();

        if (user != null) {
            if (user.getEmail().equals("admin@gmail.com")) {
                isAdmin = true;
            } else {
                isAdmin = false;
            }
        } else {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }

        setControl();
        setEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
    }

    void setControl() {
        btn_department = findViewById(R.id.mn_department);
        btn_employee = findViewById(R.id.mn_employee);
        btn_statistical = findViewById(R.id.statistical);
        btn_resetPassword = findViewById(R.id.btn_resetPassword);
        btn_exit = findViewById(R.id.exit);
        btn_notification = findViewById(R.id.notification);
        imgAvt = findViewById(R.id.imageAvatarMainActivity);
        tvHello = findViewById(R.id.tvHello);
    }

    void setEvent() {

        if (user != null) {
            Picasso.get()
                    .load(user.getPhotoUrl())
                    .into(imgAvt);
            tvHello.setText("Xin chào " + user.getDisplayName());
        }


        if (!isAdmin) {
            btn_employee.setVisibility(View.GONE);
            btn_department.setVisibility(View.GONE);
        }

        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, notification.class));
            }
        });

        btn_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, manager_department.class));
            }
        });
        btn_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, manager_employee.class));
            }
        });
        btn_statistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ranking.class));
            }
        });

        btn_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(user.getEmail());
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }

}