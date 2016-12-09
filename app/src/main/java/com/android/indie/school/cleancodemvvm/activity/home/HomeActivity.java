package com.android.indie.school.cleancodemvvm.activity.home;

import android.app.ActionBar;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.android.indie.school.cleancodemvvm.BaseApp;
import com.android.indie.school.cleancodemvvm.R;
import com.android.indie.school.cleancodemvvm.adapter.RecyclerViewAdapter;
import com.android.indie.school.cleancodemvvm.base.BaseActivity;
import com.android.indie.school.cleancodemvvm.databinding.ActivityMainBinding;
import com.android.indie.school.cleancodemvvm.models.CityListData;
import com.android.indie.school.cleancodemvvm.models.CityListResponse;
import com.android.indie.school.cleancodemvvm.networking.Service;

import javax.inject.Inject;

public class HomeActivity extends BaseActivity<ActivityMainBinding, HomeViewModel, HomePresenter>
        implements HomeView {

    @Inject
    public Service service;

    @Override
    protected void initInjection() {
        ((BaseApp) getApplication()).getAppComponent().inject(this);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    protected void initViewModel() {
        viewModel = new HomeViewModel();
        binding.setViewModel(viewModel);
    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter(service);
        presenter.setView(this);
        presenter.setViewModel(binding.getViewModel());
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        init();
        fetchData();
    }

    private void init() {
        HomeHandler handler = new HomeHandler();
        handler.setPresenter(presenter);
        binding.setHandler(handler);
        binding.list.setLayoutManager(new LinearLayoutManager(this));

        initActionBar();
    }

    private void initActionBar() {
        ActionBar mActionBar = getActionBar();

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.layout_toolbar, null);

        if (mActionBar != null) {
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setDisplayShowTitleEnabled(false);

            mActionBar.setCustomView(customView);
            mActionBar.setDisplayShowCustomEnabled(true);
        }

        binding.includedToolbar.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.reloadCityList();
            }
        });
    }

    @Override
    public void showWait() {
        presenter.showProgressWait();
    }

    @Override
    public void removeWait() {
        presenter.removeProgressWait();
    }

    @Override
    public void fetchData() {
        presenter.getCityList();
    }

    @Override
    public void onSuccessFetchData(final CityListResponse response) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, response, new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CityListData curData = response.getData().get(position);
                String desc = curData.getDescription();
                String name = curData.getName();
                Snackbar.make(binding.list, "City : "+ name + ", Numbers of Hotels : " + desc, Snackbar.LENGTH_LONG).show();
            }
        });
        binding.list.setAdapter(adapter);
    }

    @Override
    public void onErrorFetchData(String error) {
        Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
