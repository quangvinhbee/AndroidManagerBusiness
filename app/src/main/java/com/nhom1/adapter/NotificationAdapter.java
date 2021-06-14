package com.nhom1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.managerbusiness.R;
import com.nhom1.models.Employee;
import com.nhom1.models.Notification;

import org.w3c.dom.Text;

import java.util.List;

public class NotificationAdapter extends ArrayAdapter<Notification> {

    List<Notification> data;

    Context context;
    int resource;

    public NotificationAdapter(@NonNull Context context, int resource, List data) {
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
                .inflate(R.layout.notification, null);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
        TextView tvTime = (TextView)convertView.findViewById(R.id.tvTime);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);

        Notification notice = data.get(position);

        tvTitle.setText(notice.getTitle());
        tvTime.setText(notice.getTime());
        tvBody.setText(notice.getBody());

        return convertView;
    }
}
