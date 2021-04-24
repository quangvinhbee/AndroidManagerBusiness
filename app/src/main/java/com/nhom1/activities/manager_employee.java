package com.nhom1.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.nhom1.adapter.EmployeeAdapter;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.DAOimplement.EmployeeQuery;
import com.nhom1.database.DAOimplement.TimeKeepingQuery;
import com.nhom1.database.QueryResponse;
import com.nhom1.helper.Helper;
import com.nhom1.models.Employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class manager_employee extends AppCompatActivity {

    ListView lvEmployee;
    LinearLayout btn_addEmployee;
    List<Employee> data = new ArrayList<>();
    EmployeeAdapter adapter;
    AlertDialog.Builder builder;
    String ID_Department;
    DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
    DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
    DAO.TimeKeepingQuery timeKeepingQuery = new TimeKeepingQuery();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_employee);
        Intent intent = getIntent();
        Employee employeeAdd = (Employee) intent.getSerializableExtra("key_EmployeeAdd");
        Employee employeeEdit = (Employee) intent.getSerializableExtra("key_EmployeeEdit");
        ID_Department = intent.getStringExtra("key_ID_Department");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Quản Lí Nhân Viên");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));

        if(ID_Department!=null){
            getSupportActionBar().setTitle(departmentQuery.readDepartment(ID_Department));
        }

        Init();
        setControl();
        setEvent();
    }

    void Init() {
        employeeQuery.readAllEmployee(ID_Department, new QueryResponse<List<Employee>>() {
            @Override
            public void onSuccess(List<Employee> listEmployee) {
                data = listEmployee;
            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    public void EditSelectedItemListView(int position) {
        Intent intent = new Intent(manager_employee.this, EditEmployee.class);
        intent.putExtra("key_Employee", (Serializable) data.get(position));
        startActivity(intent);
    }

    public void DeleteSelectedItemListView(int position) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa vĩnh viễn nhân viên mã " + data.get(position).get_id() + " ?")
                .setCancelable(false)
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
        alert.setTitle("Xác nhận xóa nhân viên");
        alert.show();

    }

    void setControl() {
        lvEmployee = findViewById(R.id.lvEmployee);
        btn_addEmployee = findViewById(R.id.btn_addEmployee);
    }


    void setEvent() {
        adapter = new EmployeeAdapter(this, R.layout.activity_manager_department, data);
        showDialogEmployee();
        btn_addEmployee.setOnClickListener(new View.OnClickListener() { // add employee
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(manager_employee.this, add_employee.class);
                startActivity(intent);
            }
        });
    }

    private void showDialogEmployee() {
        lvEmployee.setAdapter(adapter); // set list view
        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() { // show dialog
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = data.get(position);
                final Boolean[] check = {false};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(manager_employee.this);
                mBuilder.setTitle("Thông tin nhân viên " + employee.getName());
                View mView = getLayoutInflater().inflate(R.layout.dialog_employee, null);
                TextView tvfullname = (TextView) mView.findViewById(R.id.tvFullnameEmployee);
                TextView tvDpEmployee = (TextView) mView.findViewById(R.id.tvDpEmployee);
                TextView tvTotalWorkdays = (TextView) mView.findViewById(R.id.tvTotalWorkdays);
                TextView tvSalary = (TextView) mView.findViewById(R.id.tvSalaryEmployee);
                ImageView imgAvt = mView.findViewById(R.id.imgAvatarEmployee);
                Button btn_close = mView.findViewById(R.id.button_closeDialogEmployee);
                Button btn_timekeeping = mView.findViewById(R.id.button_TimeKeeping);

                if (employee != null) {
                    timeKeepingQuery.checkTimeKeepingForEmployee(employee.get_id(), new QueryResponse<Boolean>() {
                        @Override
                        public void onSuccess(Boolean data) {
                            check[0] = data;
                        }

                        @Override
                        public void onFailure(String message) {

                        }
                    });

                    if(check[0]){
                        btn_timekeeping.setEnabled(!check[0]);
                        btn_timekeeping.setText("Đã chấm công");
                    }

                    if (employee.getAvatar() != null) {
                        imgAvt.setImageURI(Uri.parse(employee.getAvatar()));
//                        imgAvt.setImageResource(Integer.parseInt(employee.getAvatar()));
                    }
                    if (employee.getName() != null) {
                        tvfullname.setText(String.valueOf(employee.getName()));
                    }
                    if (employee.get_idDepartment() != null) {
                        tvDpEmployee.setText(String.valueOf(employee.getName_department()));
                    }
                    if (employee.getWorkdays() != 0) {
                        tvTotalWorkdays.setText(String.valueOf("Đã đi làm " + employee.getWorkdays() + " ngày"));
                    }
                    if (employee.getSalary() != 0) {
                        String salary = Helper.getCurrentFromNumber(employee.getSalary());
                        tvSalary.setText(salary+"/ tháng");
                    }
                }

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                btn_timekeeping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timeKeepingQuery.addTimeKeeping(employee.get_id(), new QueryResponse<Boolean>() {
                            @Override
                            public void onSuccess(Boolean data) {
                                if(data){
                                    btn_timekeeping.setText("Đã chấm công");
                                    btn_timekeeping.setEnabled(!true);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                Toast.makeText(manager_employee.this,"Failed timekeeping!",Toast.LENGTH_LONG);
                            }
                        });
                    }
                });
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
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