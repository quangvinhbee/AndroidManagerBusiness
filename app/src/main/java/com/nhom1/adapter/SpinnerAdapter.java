package com.nhom1.adapter;

import android.content.Context;
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
import com.nhom1.models.Department;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<String> {
    ArrayList<String> data;
    Context context;
    int resource;

    public SpinnerAdapter(@NonNull Context context, int resource,  ArrayList data) {
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_image, parent, false
            );
        }
        ImageView imgView = (ImageView) convertView.findViewById(R.id.spinnerImg);
        TextView tV = (TextView)convertView.findViewById(R.id.spinnerTextview);
        String currentItem =data.get(position);

        if (currentItem != null) {
            tV.setText(currentItem);
            imgView.setImageResource(Integer.parseInt(currentItem));
        }

        return convertView;

    }
}
