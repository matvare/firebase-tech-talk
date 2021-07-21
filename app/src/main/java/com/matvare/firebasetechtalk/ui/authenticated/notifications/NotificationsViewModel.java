package com.matvare.firebasetechtalk.ui.authenticated.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pantalla de push notification test");
    }

    public LiveData<String> getText() {
        return mText;
    }
}