package com.nhom1.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nhom1.database.DbHelper;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    private LinearLayout btn_department, btn_employee, btn_timekeeping, btn_statistical, btn_nhom1, btn_exit;
    private ImageView imgAvt;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper();
        if (user != null) {
            if (user.getEmail() == "admin@gmail.com") {
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

    void setControl() {
        btn_department = findViewById(R.id.mn_department);
        btn_employee = findViewById(R.id.mn_employee);
        btn_statistical = findViewById(R.id.statistical);
        btn_nhom1 = findViewById(R.id.nhom1);
        btn_exit = findViewById(R.id.exit);
        imgAvt = findViewById(R.id.imageAvatarMainActivity);
    }

    void setEvent() {

//        Picasso.get()
//                .load(user.getPhotoUrl())
//                .into(imgAvt);

        if (!isAdmin) {
            btn_employee.setVisibility(View.GONE);
            btn_department.setVisibility(View.GONE);
        }

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
    }

}