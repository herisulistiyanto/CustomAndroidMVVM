package com.android.indie.school.cleancodemvvm.activity.home;

import com.android.indie.school.cleancodemvvm.models.CityListResponse;

/**
 * Created by herisulistiyanto on 12/6/16.
 */

public interface HomeView {

    void showWait();

    void removeWait();

    void fetchData();

    void onSuccessFetchData(CityListResponse response);

    void onErrorFetchData(String error);

}
