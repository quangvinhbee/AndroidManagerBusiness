package com.nhom1.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.managerbusiness.R;
import com.nhom1.database.DbHelper;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    private LinearLayout btn_department,btn_employee,btn_timekeeping,btn_statistical,btn_nhom1,btn_exit;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        dbHelper = new DbHelper();
        setControl();
        setEvent();
    }
    void setControl(){
        btn_department = findViewById(R.id.mn_department);
        btn_employee = findViewById(R.id.mn_employee);
        btn_statistical = findViewById(R.id.statistical);
        btn_nhom1 = findViewById(R.id.nhom1);
        btn_exit = findViewById(R.id.exit);
    }
    void setEvent() {
        btn_department.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,manager_department.class));
            }
        });
        btn_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,manager_employee.class));
            }
        });
        btn_statistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ranking.class));
            }
        });
    }

}