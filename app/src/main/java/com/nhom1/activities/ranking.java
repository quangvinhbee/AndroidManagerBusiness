package com.nhom1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.managerbusiness.R;

public class ranking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Thống kê nhân viên xuất sắc");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        setControl();
        setEvent();
    }

    private void setControl() {
    }
    private void setEvent() {
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