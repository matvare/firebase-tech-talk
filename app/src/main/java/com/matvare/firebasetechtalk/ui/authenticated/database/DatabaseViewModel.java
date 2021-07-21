package com.matvare.firebasetechtalk.ui.authenticated.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DatabaseViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DatabaseViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pantalla test database");
    }

    public LiveData<String> getText() {
        return mText;
    }
}