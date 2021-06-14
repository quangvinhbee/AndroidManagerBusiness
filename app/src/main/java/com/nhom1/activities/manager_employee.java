package com.nhom1.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
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
import com.nhom1.adapter.EmployeeAdapter;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.DAOimplement.EmployeeQuery;
import com.nhom1.database.DAOimplement.TimeKeepingQuery;
import com.nhom1.database.QueryResponse;
import com.nhom1.helper.Helper;
import com.nhom1.models.Employee;
import com.nhom1.untils.MyApp;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class manager_employee extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 1;

    private static final int CAMERA_REQUEST_CODE = 1;

    ListView lvEmployee;
    LinearLayout btn_addEmployee;
    List<Employee> data = new ArrayList<>();
    EmployeeAdapter adapter;
    AlertDialog.Builder builder;
    EditText searchEmployee;
    String ID_Department;
    Employee employee;

    final Boolean[] checkin = {false};
    final Boolean[] checkout = {false};
    int totalWorkdays = 0;

    Button btn_timekeeping_StartAt, btn_timekeeping_EndAt;
    DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
    DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
    DAO.TimeKeepingQuery timeKeepingQuery = new TimeKeepingQuery();

    private Uri imgCapture = null;


    Animation ainm_left_to_right;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

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
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        Init();
        setControl();
        setEvent();
    }

    void Init() {
        employeeQuery.readAllEmployee(ID_Department, new QueryResponse<List<Employee>>() {
            @Override
            public void onSuccess(List<Employee> listEmployee) {
                data = listEmployee;
                for (Employee emp : listEmployee) {
                    Log.e("LogE", emp.getAvatar());
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

    public void DeleteSelectedItemListView(int position, View view) {
        //set animation
        ainm_left_to_right = AnimationUtils.loadAnimation(manager_employee.this, R.anim.left_to_right);
        ainm_left_to_right.setStartOffset(200);
        ainm_left_to_right.setAnimationListener(new Animation.AnimationListener() { //xử lí animation
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e("DEBUG_ADDEMP", "DEBUG_ADDEMP");
                employeeQuery.deleteEmployee(data.get(position), new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        Toast.makeText(MyApp.context, "Đã xóa thông tin nhân viên!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
                data.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa vĩnh viễn nhân viên tên " + data.get(position).getName() + " ?")
                .setCancelable(false)
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        view.startAnimation(ainm_left_to_right);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Xác nhận xóa nhân viên");
        alert.show();

    }

    void setControl() {
        lvEmployee = findViewById(R.id.lvEmployee);
    }


    void setEvent() {
        adapter = new EmployeeAdapter(this, R.layout.activity_manager_employee, data);
        lvEmployee.setAdapter(adapter); // set list view
        showDialogEmployee();
    }

    private void SearchEmploy(String key) {
        List<Employee> listE = new ArrayList<Employee>();
        for (Employee emp : data) {
            if (emp.getName().indexOf(key) > -1) {
                listE.add(emp);
            }
        }
        adapter = new EmployeeAdapter(this, R.layout.activity_manager_department, listE);
        showDialogEmployee();
    }

    private void showDialogEmployee() {

        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() { // show dialog
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                employee = data.get(position);
                final Boolean[] check = {false};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(manager_employee.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_employee, null);
                TextView tvfullname = (TextView) mView.findViewById(R.id.tvFullnameEmployee);
                TextView tvDpEmployee = (TextView) mView.findViewById(R.id.tvDpEmployee);
                TextView tvTotalWorkdays = (TextView) mView.findViewById(R.id.tvTotalWorkdays);

                LinearLayout btn_detail_salary = (LinearLayout) mView.findViewById(R.id.btn_view_detail_salary);
                TextView tvSalary = (TextView) mView.findViewById(R.id.tvSalaryEmployee);
                ImageView imgAvt = mView.findViewById(R.id.imgAvatarEmployee);
                ImageView btn_close = mView.findViewById(R.id.button_closeDialogEmployee);
                btn_timekeeping_StartAt = mView.findViewById(R.id.button_TimeKeeping_StartAt);
                btn_timekeeping_EndAt = mView.findViewById(R.id.button_TimeKeeping_EndAt);
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


                    if (checkin[0]) {
                        btn_timekeeping_StartAt.setEnabled(false);
                    }
                    if (checkout[0]) {
                        btn_timekeeping_EndAt.setEnabled(false);
                    }


                    if (employee.getAvatar() != null) {
                        Picasso.get()
                                .load(employee.getAvatar())
                                .into(imgAvt);
                    } else {
                        Picasso.get()
                                .load("https://i.imgur.com/kSbCAMT.png")
                                .into(imgAvt);
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
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, CAMERA_REQUEST_CODE);
                        }
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
                btn_detail_salary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(manager_employee.this, Detail_Salary.class);
                        intent.putExtra("key_Employee", (Serializable) data.get(position));
                        startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("DEBUG_ADDEMP", item.toString());
        Intent intent;
        switch (item.getItemId()) {
            case R.id.search_icon:
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Nhập tên nhân viên!");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        SearchEmploy(newText);
                        return true;
                    }
                });
                break;
            case R.id.addEmployee:
                intent = new Intent(manager_employee.this, add_employee.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                // app icon in action bar clicked; go home
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String uid = UUID.randomUUID().toString();
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK ) {
            Bundle extras = data.getExtras();
            Uri uri = data.getData();
            if(uri!=null){
                uploadPicture(uid,uri);
            }

//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imgCapture = getImageUri(getApplicationContext(), imageBitmap);
//
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
                }, uid);
        }
    }

    private void uploadPicture(String uid,Uri uri) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Đang tải ảnh lên ...");
        pd.show();
        StorageReference riversRef = storageReference.child("images/" + uid);
        riversRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                pd.dismiss();
                                Toast.makeText(manager_employee.this, "Đã tải ảnh lên", Toast.LENGTH_LONG).show();
                                String imgURL = uri.toString();
                                Log.e("Firebase message", "Thêm firebase thành công " + imgURL);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(manager_employee.this, "Tải ảnh lên thất bại", Toast.LENGTH_LONG).show();
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
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}