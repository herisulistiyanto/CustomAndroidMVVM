package com.android.indie.school.cleancodemvvm.base;

/**
 * Created by herisulistiyanto on 12/9/16.
 */

public class BasePresenter<V, VM> {

    protected V view;
    protected VM viewModel;

    public void setView(V view) {
        this.view = view;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }
}
