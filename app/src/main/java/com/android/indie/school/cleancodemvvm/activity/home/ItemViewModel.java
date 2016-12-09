package com.android.indie.school.cleancodemvvm.activity.home;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.android.indie.school.cleancodemvvm.models.CityListData;

/**
 * Created by herisulistiyanto on 12/6/16.
 */

public class ItemViewModel extends BaseObservable {

    private CityListData data;

    public ItemViewModel(CityListData data) {
        this.data = data;
        notifyProperties();
    }

    private void notifyProperties() {
        notifyPropertyChanged(BR.cityName);
        notifyPropertyChanged(BR.description);
        notifyPropertyChanged(BR.background);
    }

    @Bindable
    public String getCityName() {
        return data.getName();
    }

    @Bindable
    public String getDescription() {
        return data.getDescription();
    }

    @Bindable
    public String getBackground() {
        return data.getBackground();
    }
}
