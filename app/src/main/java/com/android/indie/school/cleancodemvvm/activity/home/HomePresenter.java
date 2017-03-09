package com.android.indie.school.cleancodemvvm.activity.home;

import com.android.indie.school.cleancodemvvm.base.BasePresenter;
import com.android.indie.school.cleancodemvvm.models.CityListResponse;
import com.android.indie.school.cleancodemvvm.networking.NetworkError;
import com.android.indie.school.cleancodemvvm.networking.Service;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by herisulistiyanto on 12/9/16.
 */

public class HomePresenter extends BasePresenter<HomeView, HomeViewModel> {

    private Service service;
    private CompositeSubscription subscriptions;

    public HomePresenter(Service service) {
        this.service = service;
        subscriptions = new CompositeSubscription();
    }

    public void showProgressWait() {
        viewModel.setInProgress(true);
    }

    public void removeProgressWait() {
        viewModel.setInProgress(false);
    }

    public void getCityList() {
        showProgressWait();
        final Subscription subscription = service.getCityList(new Service.GetCityListCallback() {
            @Override
            public void onSuccess(CityListResponse cityListResponse) {
                removeProgressWait();
                view.onSuccessFetchData(cityListResponse);
            }

            @Override
            public void onError(NetworkError networkError) {
                removeProgressWait();
                view.onErrorFetchData(networkError.getAppErrorMessage());
            }
        });
        subscriptions.add(subscription);
    }

    public void reloadCityList() {

        if (subscriptions.hasSubscriptions()) {
            subscriptions.clear();
        }

        view.fetchData();
    }

    public void onStop() {
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }
    }

}
