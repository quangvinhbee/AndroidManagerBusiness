package com.nhom1.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.managerbusiness.R;
import com.nhom1.activities.EditEmployee;
import com.nhom1.activities.add_department;
import com.nhom1.activities.manager_department;
import com.nhom1.activities.manager_employee;
import com.nhom1.helper.BitmapHelper;
import com.nhom1.models.Department;
import com.nhom1.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    List<Employee> data;
    Context context;
    int resource;

    public EmployeeAdapter(@NonNull Context context, int resource, List data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context)
                .inflate(R.layout.employee, null);

        TextView fullName = (TextView)convertView.findViewById(R.id.FullName);
        TextView totalWorkDays = (TextView)convertView.findViewById(R.id.totalWorkDays);
        ImageView avatarImg = (ImageView)convertView.findViewById(R.id.imageAvatarEmployee) ;
        ImageView btn_editEmployee = (ImageView)convertView.findViewById(R.id.imgEditEmployee);
        ImageView btn_deleteEmployee =(ImageView)convertView.findViewById(R.id.imgDeleteEmployee);
        TextView tvCreateAt = (TextView) convertView.findViewById(R.id.tvCreateAt);
        btn_editEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((manager_employee)context).EditSelectedItemListView(position);
            }
        });
        btn_deleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((manager_employee)context).DeleteSelectedItemListView(position);
            }
        });

        Employee dp = data.get(position);
        fullName.setText(dp.getName());
        tvCreateAt.setText("Tạo ngày: "+dp.getCreateAt());
        avatarImg.setImageURI(Uri.parse(dp.getAvatar()));
        avatarImg.setImageBitmap(BitmapHelper.getBitmapFromURL(dp.getAvatar()));
        Log.e("DEBUG_MSG image emp",dp.getAvatar());
        totalWorkDays.setText("Ngày đi làm: "+(dp.getWorkdays()));

        return convertView;
    }
}
