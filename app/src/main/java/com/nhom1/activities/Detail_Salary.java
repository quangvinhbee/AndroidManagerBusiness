package com.nhom1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.managerbusiness.R;
import com.nhom1.database.DAO;
import com.nhom1.database.DAOimplement.DepartmentQuery;
import com.nhom1.database.DAOimplement.EmployeeQuery;
import com.nhom1.database.DAOimplement.TimeKeepingQuery;
import com.nhom1.helper.Helper;
import com.nhom1.models.Department;
import com.nhom1.models.Employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Detail_Salary extends AppCompatActivity {

    Employee employee;
    ArrayList<String> data;

    TextView tvMonth,tvTotalSalary,tvDetuction,tvTax,tvSalary;
    int position;

    DAO.EmployeeQuery employeeQuery = new EmployeeQuery();
    DAO.DepartmentQuery departmentQuery = new DepartmentQuery();
    DAO.TimeKeepingQuery timeKeepingQuery = new TimeKeepingQuery();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__salary);

        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("key_Employee");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Chi tiết lương");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        setControl();
        setEvent();
    }

    private void setEvent() {
        int totalSalary = 0, taxSalary =0, month = 0, deductionSalary = 0, salary = 0;
        if(employee!=null){
            totalSalary = employee.getWorkdays()*(employee.getSalary()/22);

            deductionSalary = employee.getLateWork()*100000;

            taxSalary = totalSalary*5/100;

            salary = totalSalary - taxSalary - deductionSalary;

            Calendar c = Calendar.getInstance();

            month = c.get(Calendar.MONTH);

            tvSalary.setText(Helper.getCurrentFromNumber(salary));
            tvTotalSalary.setText(Helper.getCurrentFromNumber(totalSalary));
            tvDetuction.setText(Helper.getCurrentFromNumber(deductionSalary));
            tvTax.setText(Helper.getCurrentFromNumber(taxSalary));
            tvMonth.setText(" tháng "+String.valueOf(month)+" ");
        }
    }
    private void setControl() {
        tvMonth = findViewById(R.id.salary_month);
        tvSalary = findViewById(R.id.salary);
        tvTotalSalary = findViewById(R.id.total_salary);
        tvDetuction = findViewById(R.id.deduction_salary);
        tvTax = findViewById(R.id.tax_salary);
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