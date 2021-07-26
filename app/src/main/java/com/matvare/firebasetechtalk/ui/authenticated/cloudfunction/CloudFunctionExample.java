package com.matvare.firebasetechtalk.ui.authenticated.cloudfunction;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.FirebaseFunctionsException;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class CloudFunctionExample {

    private static final String TAG = CloudFunctionExample.class.getSimpleName();

    private static Task<String> callCloudFunction(String text) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("text", text);
        data.put("push", true);

        return FirebaseFunctions.getInstance()
                .getHttpsCallable("techTalkJavaCloudFunction")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        Log.d(TAG, "callCloudFunction - then: " + task.isSuccessful());
                        String result = "";
                        if (task.getResult() != null) {
                            result = (String) task.getResult().getData();
                            Log.i(TAG, "callCloudFunction - data: " + result);
                        }
                        return result;
                    }
                });
    }

    public static void callCloudFunctionWithListener() {
        Log.d(TAG, "callCloudFunctionWithListener");
        callCloudFunction("Prueba cloud function").addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                Log.d(TAG, "callCloudFunctionWithListener - onComplete isSuccessful: " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Exception e = task.getException();
                    if (e instanceof FirebaseFunctionsException) {
                        FirebaseFunctionsException ffe = (FirebaseFunctionsException) e;
                        FirebaseFunctionsException.Code code = ffe.getCode();
                        Object details = ffe.getDetails();
                        Log.d(TAG, "callCloudFunctionWithListener - onComplete not successful. Code: "
                                + code + " details: " + details);

                    }
                }
            }
        });
    }
}
