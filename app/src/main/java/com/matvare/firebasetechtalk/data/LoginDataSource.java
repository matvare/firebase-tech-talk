package com.matvare.firebasetechtalk.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.matvare.firebasetechtalk.data.model.LoggedInUser;
import com.matvare.firebasetechtalk.ui.login.OnLoginResult;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final static String TAG = LoginDataSource.class.getSimpleName();

    private FirebaseAuth mAuth;

    public LoginDataSource() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(String username, String password, OnLoginResult resultListener) {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Log.w(TAG, "createUserWithEmail:user not null and created.");
                            LoggedInUser loggedInUser =
                                    new LoggedInUser(
                                            user.getUid(),
                                            user.getEmail());
                            Result.Success<LoggedInUser> result = new Result.Success<>(loggedInUser);
                            resultListener.onResult(result);
                        } else {
                            Log.w(TAG, "createUserWithEmail:user is null.");
                        }

                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            loginExistingUser(username, password, resultListener);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            resultListener.onResult(new Result.Error(new IOException("Error logging in", task.getException())));
                        }
                    }
                });
    }

    public void logout() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Log.d(TAG, "logout current user: " + user.getEmail());
            FirebaseAuth.getInstance().signOut();
        } else {
            Log.d(TAG, "User is null. Cannot log out.");
        }
    }

    private void loginExistingUser(String email, String password, OnLoginResult resultListener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Log.w(TAG, "signInWithEmail:user not null and created.");
                            LoggedInUser loggedInUser =
                                    new LoggedInUser(
                                            user.getUid(),
                                            user.getEmail());
                            Result.Success<LoggedInUser> result = new Result.Success<>(loggedInUser);
                            resultListener.onResult(result);
                        } else {
                            Log.w(TAG, "signInWithEmail:user is null.");
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        resultListener.onResult(new Result.Error(new IOException("Error logging in the existing user", task.getException())));
                    }
                });
    }
}