package com.android.indie.school.cleancodemvvm.activity.home;

/**
 * Created by herisulistiyanto on 12/9/16.
 */

public class HomeHandler {

    private HomePresenter presenter;

    public HomePresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(HomePresenter presenter) {
        this.presenter = presenter;
    }

    public void handleReload() {
        presenter.reloadCityList();
    }
}
