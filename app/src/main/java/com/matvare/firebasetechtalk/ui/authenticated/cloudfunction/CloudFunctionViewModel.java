package com.matvare.firebasetechtalk.ui.authenticated.cloudfunction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CloudFunctionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CloudFunctionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pantalla de test de Cloud Function");
    }

    public LiveData<String> getText() {
        return mText;
    }
}