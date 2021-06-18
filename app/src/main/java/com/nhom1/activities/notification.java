package com.nhom1.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nhom1.adapter.NotificationAdapter;
import com.nhom1.models.Notification;

import java.util.ArrayList;
import java.util.List;

public class notification extends AppCompatActivity {

    ListView lvNotification;
    NotificationAdapter adapter;
    List<Notification> data = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Thông báo");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        setControl();
        setEvent();
    }

    void setControl() {
        lvNotification = findViewById(R.id.lvNotification);
    }


    void setEvent() {
        Notification notice = new Notification("THÔNG BÁO MỚI", "22:00 19/2/2102",
                "Từ 0h ngày 15/6, TP.HCM tiếp tục áp dụng Chỉ thị 15 trên toàn thành phố. " +
                        "Riêng một số loại hình kinh doanh dịch vụ thiết yếu vẫn được hoạt động.");
        data.add(notice);
        data.add(notice);
        data.add(notice);
        adapter = new NotificationAdapter(this, R.layout.activity_notification, data);
        lvNotification.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getEmail().equals("admin@gmail.com"))
            getMenuInflater().inflate(R.menu.menu_notification, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("DEBUG_ADDEMP", item.toString());
        Intent intent;
        switch (item.getItemId()) {
            case R.id.addNotification:
                intent = new Intent(notification.this, addNotification.class);
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

}