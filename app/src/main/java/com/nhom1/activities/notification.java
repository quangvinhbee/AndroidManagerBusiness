package com.nhom1.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.managerbusiness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nhom1.adapter.NotificationAdapter;
import com.nhom1.models.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class notification extends AppCompatActivity {

    ListView lvNotification;
    NotificationAdapter adapter;
    public List<Notification> data = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseFirestore db;

    private static final String TAG = "DocSnippets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Thông báo");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));

        getDataNotification();
        setControl();
        setEvent();
    }

    void setControl() {
        lvNotification = findViewById(R.id.lvNotification);
    }


    void setEvent() {
        Log.e(TAG,"item.getTitle()");
        for (Notification item:data){
            Log.e(TAG,item.getTitle());
        }

    }

    void getDataNotification(){
        db = FirebaseFirestore.getInstance();
        db.collection("notification")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Notification> dta = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Notification noti = document.toObject(Notification.class);
                                dta.add(noti);
                            }
                            adapter = new NotificationAdapter(notification.this, R.layout.activity_notification, dta);
                            lvNotification.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

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