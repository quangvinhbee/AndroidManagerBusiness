package com.nhom1.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.DAOimplement.EmployeeQuery;
import com.nhom1.database.DAOimplement.TimeKeepingQuery;
import com.nhom1.database.QueryResponse;
import com.nhom1.models.Department;
import com.nhom1.models.Employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditEmployee extends AppCompatActivity {

    Employee employee;
    ArrayList<String> data;
    List<Department> listDepartment;
    EditText edtFullName, edtSalary;
    Button btnEdit;
    Spinner spinner;
    ImageView AvatarUpload;
    Uri imgUri;
    int position;
    DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
    DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
    DAO.TimeKeepingQuery timeKeepingQuery = new TimeKeepingQuery();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("key_Employee");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Sửa Nhân Viên");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        setControl();
        setEvent();
    }

    void setEvent() {
        chooseDepartment();
        edtFullName.setText(employee.getName());
        edtSalary.setText(String.valueOf(employee.getSalary()));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.setName(edtFullName.getText().toString());
                employee.setSalary(Integer.parseInt(edtSalary.getText().toString()));
                employeeQuery.updateEmployee(employee, new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        Toast.makeText(EditEmployee.this, "Chỉnh sửa thông tin thành công!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
                Intent intent = new Intent(EditEmployee.this, manager_employee.class);
                intent.putExtra("key_EmployeeEdit", (Serializable) employee);
                startActivity(intent);
            }
        });
    }

    void setControl() {
        AvatarUpload = findViewById(R.id.imgAddUploadAvatar);
        spinner = findViewById(R.id.spinnerListDepartment_Edit);
        edtFullName = findViewById(R.id.editEditNameEmployee);
        edtSalary = findViewById(R.id.editEditSalaryEmployee);
        btnEdit = findViewById(R.id.button_Edit_employee);
    }

    private void chooseDepartment() {
        data = new ArrayList<>();
        DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
        departmentQuery.readAllDepartment(new QueryResponse<List<Department>>() {
            @Override
            public void onSuccess(List<Department> listDp) {
                listDepartment = listDp;
            }

            @Override
            public void onFailure(String message) {

            }
        });
        if (listDepartment != null)
            for (Department item : listDepartment) {
                data.add(item.getName());
            }
        else {
            Toast.makeText(EditEmployee.this, "Chưa có phòng ban!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditEmployee.this, manager_employee.class);
            startActivity(intent);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_image, R.id.spinnerTextview, data);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (Department item : listDepartment) {
                    if (item.getName().equals(data.get(position))) {
                        employee.set_idDepartment(item.get_id());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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