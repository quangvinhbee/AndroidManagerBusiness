package com.nhom1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.managerbusiness.R;
import com.nhom1.activities.manager_department;
import com.nhom1.activities.manager_employee;
import com.nhom1.models.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends ArrayAdapter<Department> {

    List<Department> data;
    Context context;
    int resource;

    public DepartmentAdapter(@NonNull Context context, int resource, List data) {
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
                .inflate(R.layout.department, null);

        TextView nameDepartment = (TextView)convertView.findViewById(R.id.nameDepartment);
        TextView countEmployeeinDepartment = (TextView)convertView.findViewById(R.id.countEmployeeinDepartment);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imgAvatarDepartmentAdd);
        Department dp = data.get(position);
        nameDepartment.setText(dp.getName());
        imageView.setImageResource(Integer.parseInt(dp.getAvatar()));
        countEmployeeinDepartment.setText("Tổng số nhân viên: " + dp.getCount_employee());
        ImageView btnEdit = (ImageView)convertView.findViewById(R.id.button_editDepartment);
        ImageView btnDelete = (ImageView)convertView.findViewById(R.id.button_deleteDepartment);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View child = parent.getChildAt(position);
                ((manager_department)context).DeleteSelectedItemListView(position,child);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((manager_department)context).EditSelectedItemListView(position);
            }
        });
        return convertView;
    }
}
