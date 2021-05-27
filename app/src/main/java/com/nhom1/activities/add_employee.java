package com.nhom1.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.DAOimplement.EmployeeQuery;
import com.nhom1.database.QueryResponse;
import com.nhom1.models.Department;
import com.nhom1.models.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class add_employee extends AppCompatActivity {
    ArrayList<String> data;
    List<Department> listDepartment;
    Spinner spinner;
    Button btnAdd;
    Employee employee = new Employee();
    EditText edtFullname, edtSalary;
    ImageView AvatarUpload;
    Uri imgUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    String uid = UUID.randomUUID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Thêm Nhân Viên");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        setControl();
        setEvent();
    }

    void setControl() {
        AvatarUpload = findViewById(R.id.imgAddUploadAvatar);
        spinner = findViewById(R.id.spinnerListDepartment);
        btnAdd = findViewById(R.id.button_add_employee);
        edtFullname = findViewById(R.id.editNameEmployee);
        edtSalary = findViewById(R.id.editSalaryEmployee);
    }

    void setEvent() {
        chooseDepartment();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.set_id(uid);
                employee.setWorkdays(0);
                employee.setName(edtFullname.getText().toString());
                if (employee.getAvatar() == null) {
                    employee.setAvatar("https://i.imgur.com/kSbCAMT.png");
                }
                if (edtSalary.getText().toString().length() > 0) {
                    employee.setSalary(Integer.parseInt(edtSalary.getText().toString()));
                }
                addEmployee(employee);
                Intent intent = new Intent(add_employee.this, manager_employee.class);
                startActivity(intent);
            }
        });
        AvatarUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImage();
            }
        });
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
            Toast.makeText(add_employee.this, "Chưa có phòng ban, không thể tạo nhân viên", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(add_employee.this, manager_employee.class);
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

    private void addEmployee(Employee employee) {
        if (employee.getName().length() == 0) {
            Toast.makeText(add_employee.this, "Tên nhân viên không được trống", Toast.LENGTH_LONG).show();
            return;
        }
        if (employee.get_idDepartment().length() == 0) {
            Toast.makeText(add_employee.this, "Phòng ban không được trống", Toast.LENGTH_LONG).show();
            return;
        }
        if (employee.getSalary() == 0) {
            Toast.makeText(add_employee.this, "Lương nhân viên không được trống", Toast.LENGTH_LONG).show();
            return;
        }
        if (employee.get_idDepartment().length() == 0) {
            Toast.makeText(add_employee.this, "Phòng ban nhân viên không được trống", Toast.LENGTH_LONG).show();
            return;
        }
        DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
        employeeQuery.addEmployee(employee, new QueryResponse<Boolean>() {
            @Override
            public void onSuccess(Boolean data) {

            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(add_employee.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    private void ShowImageAvatar() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();
            AvatarUpload.setImageURI(imgUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Đang tải ảnh lên ...");
        pd.show();
        StorageReference riversRef = storageReference.child("images/" + uid);
        riversRef.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                pd.dismiss();
                                Toast.makeText(add_employee.this, "Đã tải ảnh lên", Toast.LENGTH_LONG).show();
                                String imgURL = uri.toString();
                                employee.setAvatar(imgURL);
                                Log.e("Firebase message", "Thêm firebase thành công " + imgURL);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(add_employee.this, "Tải ảnh lên thất bại", Toast.LENGTH_LONG).show();
                        Log.e("Firebase message", "Thêm firebase thất bại");
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Còn " + (int) progressPercent + "% nữa hoàn thành");
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