package edu.neu.madcourse.pettin.ui.playdate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaydateViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PlaydateViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is playdate fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}