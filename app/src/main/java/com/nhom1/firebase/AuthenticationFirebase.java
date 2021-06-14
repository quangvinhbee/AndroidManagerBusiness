package com.nhom1.firebase;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.nhom1.database.QueryResponse;
import com.nhom1.models.Employee;
import com.nhom1.untils.MyApp;

public class AuthenticationFirebase {
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "EmailPassword";
    private static FirebaseUser user;

    public static void signIn(String email, String password, QueryResponse<Boolean> response) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            user = mAuth.getCurrentUser();
                            Log.w(TAG, "signInWithEmail:success", task.getException());
                            response.onSuccess(true);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithEmail:failure", task.getException());
                            updateUI(null);
                            response.onFailure("signInWithEmail:failure");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public static void createUserWithEmailAndPassword(String email, String password, Employee employee) {
        FirebaseAuth mAuth1 = FirebaseAuth.getInstance();
        mAuth1.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth1.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(employee.getName())
                                    .setPhotoUri(employee.getAvatarUri())
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                            mAuth1.signOut();
                            signIn("admin@gmail.com", "123456", new QueryResponse<Boolean>() {
                                @Override
                                public void onSuccess(Boolean data) {

                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MyApp.getAppContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private static void updateUI(FirebaseUser u) {
        user = u;
        Log.d(TAG, "User " + user.getUid());
    }
}
