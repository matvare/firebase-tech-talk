package com.matvare.firebasetechtalk.service;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.matvare.firebasetechtalk.data.LoginDataSource;
import com.matvare.firebasetechtalk.data.LoginRepository;
import com.matvare.firebasetechtalk.data.model.LoggedInUser;

import org.jetbrains.annotations.NotNull;

public class FirebaseMessagingServiceExample extends FirebaseMessagingService {

    private static final String TAG = FirebaseMessagingServiceExample.class.getSimpleName();

    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(@NotNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        LoggedInUser loggedInUser = LoginRepository.getInstance(new LoginDataSource()).getUser();
        if (loggedInUser != null) {
            String userId = loggedInUser.getUserId();
            FirebaseFirestore.getInstance().collection("users")
                    .document(userId)
                    .update("token", token);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            // Hacer algo acá

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
