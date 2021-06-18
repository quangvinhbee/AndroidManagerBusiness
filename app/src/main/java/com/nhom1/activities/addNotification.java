package com.nhom1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.managerbusiness.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class addNotification extends AppCompatActivity {

    FirebaseFirestore db;
    EditText edtTitle,edtBody;
    Button btnAdd;
    private static final String TAG = "DocSnippets";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // set action bar
        getSupportActionBar().setTitle("Thêm Thông Báo");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xff78CFFD));
        db = FirebaseFirestore.getInstance();

        setControl();
        setEvent();
    }

    private void setControl() {
        edtTitle = findViewById(R.id.editTitleNotification);
        edtBody = findViewById(R.id.editBodyNotification);
        btnAdd = findViewById(R.id.button_add_notification);
    }
    private void setEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> notification = new HashMap<>();
                notification.put("title",edtTitle.getText().toString());
                notification.put("body",edtBody.getText().toString());
                Log.d(TAG, "Body:" + edtBody.getText().toString());

                db.collection("notification")
                        .add(notification)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });
    }
}