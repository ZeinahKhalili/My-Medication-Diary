package com.example.MyMedicationDiary.ui.doc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DocViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("مواعيد الطبيب");
    }

    public LiveData<String> getText() {
        return mText;
    }
}