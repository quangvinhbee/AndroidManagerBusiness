package com.nhom1.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhom1.adapter.EmployeeAdapter;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.DAOimplement.EmployeeQuery;
import com.nhom1.database.DAOimplement.TimeKeepingQuery;
import com.nhom1.database.QueryResponse;
import com.nhom1.helper.Helper;
import com.nhom1.models.Employee;
import com.squareup.picasso.Picasso;

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
    int totalWorkdays = 0;
    DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
    DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
    DAO.TimeKeepingQuery timeKeepingQuery = new TimeKeepingQuery();


    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_employee);
        Intent intent = getIntent();
        Employee employeeAdd = (Employee) intent.getSerializableExtra("key_EmployeeAdd");
        Employee employeeEdit = (Employee) intent.getSerializableExtra("key_EmployeeEdit");
        ID_Department = intent.getStringExtra("key_ID_Department");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Quản Lí Nhân Viên");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));

        if (ID_Department != null) {
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
                for(Employee emp:listEmployee){
                    Log.e("LogE",emp.getAvatar());
                }
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
        AlertDialog alert = builder.create();
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
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Boolean[] checkin = {false};
                final Boolean[] checkout = {false};
                Employee employee = data.get(position);
                final Boolean[] check = {false};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(manager_employee.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_employee, null);
                TextView tvfullname = (TextView) mView.findViewById(R.id.tvFullnameEmployee);
                TextView tvDpEmployee = (TextView) mView.findViewById(R.id.tvDpEmployee);
                TextView tvTotalWorkdays = (TextView) mView.findViewById(R.id.tvTotalWorkdays);


                TextView tvSalary = (TextView) mView.findViewById(R.id.tvSalaryEmployee);
                ImageView imgAvt = mView.findViewById(R.id.imgAvatarEmployee);
                ImageView btn_close = mView.findViewById(R.id.button_closeDialogEmployee);
                Button btn_timekeeping_StartAt = mView.findViewById(R.id.button_TimeKeeping_StartAt);
                Button btn_timekeeping_EndAt = mView.findViewById(R.id.button_TimeKeeping_EndAt);
                totalWorkdays = timeKeepingQuery.readDateCurrentMonthOfEmployee(employee.get_id());
                if (employee != null) { // set data
                    timeKeepingQuery.isCheckIn(employee.get_id(), new QueryResponse<Boolean>() {
                        @Override
                        public void onSuccess(Boolean data) {
                            checkin[0] = data;
                        }

                        @Override
                        public void onFailure(String message) {

                        }
                    });
                    timeKeepingQuery.isCheckOut(employee.get_id(), new QueryResponse<Boolean>() {
                        @Override
                        public void onSuccess(Boolean data) {
                            checkout[0] = data;
                        }

                        @Override
                        public void onFailure(String message) {

                        }
                    });
                    Picasso.get()
                            .load(employee.getAvatar())
                            .into(imgAvt);

                    storageRef.child("images/"+employee.get_id()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            imgAvt.setImageURI(uri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });

                    if (checkin[0]) {
                        btn_timekeeping_StartAt.setEnabled(false);
                    }
                    if (checkout[0]) {
                        btn_timekeeping_EndAt.setEnabled(false);
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
                    tvTotalWorkdays.setText(String.valueOf("Đã đi làm " + employee.getWorkdays() + " ngày"));
                    if (employee.getSalary() != 0) {
                        String salary = Helper.getCurrentFromNumber(employee.getSalary());
                        tvSalary.setText(salary + "/ tháng");
                    }
                }

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                btn_timekeeping_StartAt.setOnClickListener(new View.OnClickListener() { // check In
                    @Override
                    public void onClick(View v) {
                        if (!checkin[0])
                            timeKeepingQuery.addCheckInTimeKeeping(employee.get_id(), new QueryResponse<Boolean>() {
                                @Override
                                public void onSuccess(Boolean data) {
                                    if (data) {
                                        Init();
                                        adapter.notifyDataSetChanged();
                                        btn_timekeeping_StartAt.setEnabled(!data);
                                        Toast.makeText(manager_employee.this, "CheckIn thành công!", Toast.LENGTH_LONG);
                                    }
                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                    }
                });
                btn_timekeeping_EndAt.setOnClickListener(new View.OnClickListener() { // check out
                    @Override
                    public void onClick(View v) {
                        if (checkin[0])
                            timeKeepingQuery.addCheckOutTimeKeeping(employee.get_id(), new QueryResponse<Boolean>() {
                                @Override
                                public void onSuccess(Boolean data) {
                                    Init();
                                    adapter.notifyDataSetChanged();
                                    btn_timekeeping_EndAt.setEnabled(!data);
                                    Toast.makeText(manager_employee.this, "CheckOut thành công!", Toast.LENGTH_LONG);
                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                    }
                });
                btn_close.setOnClickListener(new View.OnClickListener() { // close dialog
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