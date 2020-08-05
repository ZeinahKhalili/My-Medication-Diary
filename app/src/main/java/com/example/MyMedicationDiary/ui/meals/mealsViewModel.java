package com.example.MyMedicationDiary.ui.meals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class mealsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public mealsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("وجباتك وفقاً لمواعيد أدويتك");
    }

    public LiveData<String> getText() {
        return mText;
    }
}