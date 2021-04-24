package com.nhom1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.managerbusiness.R;
import com.nhom1.adapter.SpinnerAdapter;
import com.nhom1.models.Department;
import com.nhom1.models.Employee;

import java.io.Serializable;
import java.util.ArrayList;

public class EditDepartment extends AppCompatActivity {

    Department department = new Department();
    Button btnEdit;
    Spinner spinnerAvt;
    EditText edtName;
    ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_department);

        Intent intent = getIntent();
        Department temp = (Department) intent.getSerializableExtra("key_Department");
        if(temp!=null){
            department = temp;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Sửa Phòng Ban");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        setControl();
        setEvent();
    }

    void setControl() {
        btnEdit =findViewById(R.id.button_editEdit_department);
        spinnerAvt = findViewById(R.id.spinnerEditAvatarDepartment);
        edtName = findViewById(R.id.editEditNameDepartment);
    }

    void setEvent() {

        data.add(String.valueOf(R.drawable.management));
        data.add(String.valueOf(R.drawable.accounting));
        data.add(String.valueOf(R.drawable.engineer));
        data.add(String.valueOf(R.drawable.marketing));
        data.add(String.valueOf(R.drawable.sales));
        SpinnerAdapter dataAdapter = new SpinnerAdapter(this, R.layout.spinner_image, data);
        spinnerAvt.setAdapter(dataAdapter);
        spinnerAvt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department.setAvatar(data.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                department.setAvatar(data.get(0));
            }
        });
        edtName.setText(String.valueOf(department.getName()));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                department.setCount_employee(10);
                department.setName(edtName.getText().toString());
                department.set_id("pPPPPP");

                Intent intent = new Intent(EditDepartment.this, manager_department.class);
                intent.putExtra("key_DepartmentEdit", (Serializable) department);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, manager_department.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}