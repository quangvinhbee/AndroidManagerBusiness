package com.nhom1.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.nhom1.adapter.DepartmentAdapter;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.DAOimplement.EmployeeQuery;
import com.nhom1.database.DAOimplement.TimeKeepingQuery;
import com.nhom1.database.QueryResponse;
import com.nhom1.models.Department;
import com.nhom1.models.Employee;
import com.nhom1.untils.MyApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class manager_department extends AppCompatActivity {

    private ListView lvDepartment;
    private LinearLayout btn_addDepartment;
    List<Department> data = new ArrayList<>();
    AlertDialog.Builder builder;
    DepartmentAdapter adapter;
    Animation ainm_left_to_right;


    DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
    DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
    DAO.TimeKeepingQuery timeKeepingQuery = new TimeKeepingQuery();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_department);
        Intent intent = getIntent();
        Department departmentAdd = (Department)intent.getSerializableExtra("key_DepartmentAdd");
        Department departmentEdit = (Department)intent.getSerializableExtra("key_DepartmentEdit");

        if(departmentEdit!=null){
            data.add(departmentEdit);
        }
        if(departmentAdd!=null){
            data.add(departmentAdd);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Quản Lí Phòng Ban");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));

        Init();
        setControl();
        setEvent();
    }

    public void EditSelectedItemListView(int position){
        Intent intent = new Intent(manager_department.this, EditDepartment.class);
        intent.putExtra("key_Department", (Serializable) data.get(position));
        startActivity(intent);
    }
    public void DeleteSelectedItemListView(int position,View view){
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa vĩnh viễn phòng ban mã "+data.get(position).get_id() +" ?")
                .setCancelable(false)
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        departmentQuery.deleteDepartment(data.get(position), new QueryResponse<Boolean>() {
                            @Override
                            public void onSuccess(Boolean data) {
                                Toast.makeText(MyApp.context, "Đã xóa thông tin Phòng ban!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(String message) {

                            }
                        });
                        data.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Xác nhận xóa phòng ban");
        alert.show();
    }

    void setControl() {
        lvDepartment = findViewById(R.id.lvDepartment);
        btn_addDepartment = findViewById(R.id.btn_addDepartment);
    }

    void setEvent() {
        adapter = new DepartmentAdapter(this, R.layout.activity_manager_department, data);
        lvDepartment.setAdapter(adapter);
        lvDepartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(manager_department.this, manager_employee.class);
                intent.putExtra("key_ID_Department", data.get(position).get_id());
                startActivity(intent);
            }
        });

        btn_addDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manager_department.this,add_department.class));
            }
        });
    }

    void Init() {
        Department dp = new Department();
        DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
        departmentQuery.readAllDepartment(new QueryResponse<List<Department>>() {
            @Override
            public void onSuccess(List<Department> listDepartment) {
                data = listDepartment;
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}