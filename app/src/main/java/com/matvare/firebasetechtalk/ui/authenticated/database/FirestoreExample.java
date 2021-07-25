package com.matvare.firebasetechtalk.ui.authenticated.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.matvare.firebasetechtalk.data.LoginDataSource;
import com.matvare.firebasetechtalk.data.LoginRepository;
import com.matvare.firebasetechtalk.data.model.LoggedInUser;

import java.util.HashMap;
import java.util.Map;

public class FirestoreExample {

    private static final String TAG = FirestoreExample.class.getSimpleName();

    public static void writeInFirestore() {
        LoggedInUser loggedInUser = LoginRepository.getInstance(new LoginDataSource()).getUser();
        Log.d(TAG, "Logged in user: " + loggedInUser.getDisplayName());
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("email", loggedInUser.getDisplayName());
        user.put("id", loggedInUser.getUserId());
        user.put("timestampServer", FieldValue.serverTimestamp());

        // Add a new document with a generated ID
        FirebaseFirestore.getInstance().collection("users")
                .add(user)
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

    public static void readFromFirestore() {
        FirebaseFirestore.getInstance().collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
