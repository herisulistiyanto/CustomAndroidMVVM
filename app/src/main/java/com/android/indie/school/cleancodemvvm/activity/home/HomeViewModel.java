package com.android.indie.school.cleancodemvvm.activity.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by herisulistiyanto on 12/6/16.
 */

public class HomeViewModel extends BaseObservable {

    public boolean isInProgress = false;

    @Bindable
    public boolean isInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.isInProgress = inProgress;
        notifyPropertyChanged(BR.inProgress);
    }

}
