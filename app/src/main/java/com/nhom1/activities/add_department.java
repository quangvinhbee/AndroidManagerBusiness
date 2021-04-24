package com.nhom1.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.nhom1.adapter.SpinnerAdapter;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.QueryResponse;
import com.nhom1.models.Department;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class add_department extends AppCompatActivity {

    Spinner spinnerAvt;
    ArrayList<String> data = new ArrayList<>();
    Department department = new Department();
    EditText edtName;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Thêm Phòng Ban");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        setControl();
        setEvent();
    }


    void setControl() {
        btnAdd =findViewById(R.id.button_add_department);
        spinnerAvt = findViewById(R.id.spinneravatarDepartment);
        edtName = findViewById(R.id.editNameDepartment);
    }

    void setEvent() {
        chooseAvatarDeparment();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = UUID.randomUUID().toString();
                department.set_id(uid);
                department.setCount_employee(0);
                department.setName(edtName.getText().toString());
                addDepartment(department);

                Intent intent = new Intent(add_department.this, manager_department.class);
                intent.putExtra("key_DepartmentAdd", (Serializable) department);
                startActivity(intent);
            }
        });
    }

    private void chooseAvatarDeparment() {
        department.setAvatar(String.valueOf(R.drawable.management));
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

            }
        });
    }

    private void addDepartment(Department department) {
        if (department.getName().length() == 0) {
            Toast.makeText(add_department.this, "Tên phòng ban không được trống", Toast.LENGTH_LONG).show();
            return;
        }
        if (department.getAvatar().length() == 0) {
            Toast.makeText(add_department.this, "Ảnh đại diện không được trống", Toast.LENGTH_LONG).show();
            return;
        }
        DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
        departmentQuery.addDepartment(department, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {
                Toast.makeText(add_department.this, "Thêm phòng ban thành công", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(add_department.this, "Thêm phòng ban thất bại", Toast.LENGTH_LONG).show();
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