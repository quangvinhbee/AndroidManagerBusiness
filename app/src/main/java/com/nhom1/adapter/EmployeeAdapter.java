package com.nhom1.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.managerbusiness.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nhom1.activities.manager_employee;
import com.nhom1.models.Employee;
import com.squareup.picasso.Picasso;

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

        TextView fullName = (TextView) convertView.findViewById(R.id.FullName);
        TextView totalWorkDays = (TextView) convertView.findViewById(R.id.totalWorkDays);
        ImageView avatarImg = (ImageView) convertView.findViewById(R.id.imageAvatarEmployee);
        ImageView btn_editEmployee = (ImageView) convertView.findViewById(R.id.imgEditEmployee);
        ImageView btn_deleteEmployee = (ImageView) convertView.findViewById(R.id.imgDeleteEmployee);
        TextView tvCreateAt = (TextView) convertView.findViewById(R.id.tvCreateAt);
        btn_editEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((manager_employee) context).EditSelectedItemListView(position);
            }
        });
        btn_deleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View child = parent.getChildAt(position);
                ((manager_employee) context).DeleteSelectedItemListView(position,child);
            }
        });


        Employee dp = data.get(position);
        fullName.setText(dp.getName());
        tvCreateAt.setText("T???o ng??y: " + dp.getCreateAt());
        Picasso.get()
                .load(dp.getAvatar())
                .resize(100,100)
                .into(avatarImg);


        totalWorkDays.setText("Ng??y ??i l??m: " + (dp.getWorkdays()));

        return convertView;
    }
}
