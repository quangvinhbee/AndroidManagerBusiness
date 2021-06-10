package com.nhom1.authentication;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nhom1.database.QueryResponse;

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
                });
    }

    private static void updateUI(FirebaseUser u) {
        user = u;
        Log.d(TAG,"User "+  user.getUid());
    }
}
