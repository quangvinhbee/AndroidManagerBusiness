package com.nhom1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.managerbusiness.R;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.DAOimplement.EmployeeQuery;
import com.nhom1.database.DAOimplement.TimeKeepingQuery;
import com.nhom1.database.QueryResponse;
import com.nhom1.models.Employee;
import com.squareup.picasso.Picasso;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class ranking extends AppCompatActivity {

    DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
    DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
    DAO.TimeKeepingQuery timeKeepingQuery = new TimeKeepingQuery();
    ImageView imgTop1;
    TextView month;
    LinearLayout linearLayoutChart;
    PieChart pieChart;
    List<Employee> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Thống kê nhân viên xuất sắc");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        Init();
        setControl();
        setEvent();
    }

    private void setControl() {
        imgTop1 = findViewById(R.id.imgAvatarEmployeeTop1);
        month = findViewById(R.id.tvEmployeeXuatsacnhat);
        pieChart = findViewById(R.id.piechart);

    }
    private void setEvent() {
        List<Employee> listTop = new ArrayList<Employee>();
        String top1="", top2="", top3="";
        int totalSalary = 0, taxSalary =0, month1 = 0, deductionSalary = 0, salary = 0;
        int max = 0;
        for(Employee employee:data){
            totalSalary = employee.getWorkdays()*(employee.getSalary()/22);

            deductionSalary = employee.getLateWork()*100000;

            taxSalary = totalSalary*5/100;

            salary = totalSalary - taxSalary - deductionSalary;

            if(totalSalary>max && !employee.get_id().equals(top1)) {
                max = totalSalary;
                top1 = employee.get_id();
            }
        }
        for(Employee employee:data){
            totalSalary = employee.getWorkdays()*(employee.getSalary()/22);

            deductionSalary = employee.getLateWork()*100000;

            taxSalary = totalSalary*5/100;

            salary = totalSalary - taxSalary - deductionSalary;

            if(totalSalary>max && !employee.get_id().equals(top1)&& !employee.get_id().equals(top2)) {
                max = totalSalary;
                top2 = employee.get_id();
            }
        }
        for(Employee employee:data){
            totalSalary = employee.getWorkdays()*(employee.getSalary()/22);

            deductionSalary = employee.getLateWork()*100000;

            taxSalary = totalSalary*5/100;

            salary = totalSalary - taxSalary - deductionSalary;

            if(totalSalary>max && !employee.get_id().equals(top1)&& !employee.get_id().equals(top2)&& !employee.get_id().equals(top3)) {
                max = totalSalary;
                top3 = employee.get_id();
            }
        }
        for(Employee employee:data){
            if(employee.get_id().equals(top1)){
                Picasso.get()
                        .load(employee.getAvatar())
                        .into(imgTop1);
            }
        }

        Calendar c = Calendar.getInstance();

        month1 = c.get(Calendar.MONTH);

        month.setText("Nhân viên có thành tích xuất sắc nhất tháng "+month1);

//        for(Employee employee:data){
//            Random obj = new Random();
//            int rand_num = obj.nextInt(0xffffff + 1);
//            String colorCode = String.format("#%06x", rand_num);
//            pieChart.addPieSlice(
//                    new PieModel(
//                            employee.getName(),
//                            employee.getSalary(),
//                            Color.parseColor(colorCode)));
//        }


        // To animate the pie chart
        pieChart.startAnimation();

    }
    void Init() {
        employeeQuery.readAllEmployee(null, new QueryResponse<List<Employee>>() {
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