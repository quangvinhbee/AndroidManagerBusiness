package com.nhom1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.managerbusiness.R;
import com.nhom1.models.Employee;

import java.io.Serializable;

public class EditEmployee extends AppCompatActivity {

    Employee employee;
    EditText edtFullName, edtSalary;
    Button btnEdit;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        Intent intent = getIntent();
        employee = (Employee)intent.getSerializableExtra("key_Employee");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Sửa Nhân Viên");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        setControl();
        setEvent();
    }

    void setEvent(){
        edtFullName.setText(employee.getName());
        edtSalary.setText(String.valueOf(employee.getSalary()));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.set_idDepartment("11l1l1l");
                employee.setName(edtFullName.getText().toString());
                employee.setSalary(Integer.parseInt(edtSalary.getText().toString()));
                Intent intent = new Intent(EditEmployee.this, manager_employee.class);
                intent.putExtra("key_EmployeeEdit", (Serializable) employee);
                startActivity(intent);
            }
        });
    }

    void setControl(){
        edtFullName = findViewById(R.id.editEditNameEmployee);
        edtSalary = findViewById(R.id.editEditSalaryEmployee);
        btnEdit = findViewById(R.id.button_Edit_employee);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, manager_employee.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}