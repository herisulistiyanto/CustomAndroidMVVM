package com.android.indie.school.cleancodemvvm;

import android.app.Application;

import com.android.indie.school.cleancodemvvm.deps.component.AppComponent;
import com.android.indie.school.cleancodemvvm.deps.component.DaggerAppComponent;
import com.android.indie.school.cleancodemvvm.deps.module.AppModule;
import com.android.indie.school.cleancodemvvm.deps.module.NetworkModule;

/**
 * Created by herisulistiyanto on 12/6/16.
 */

public class BaseApp extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setAppComponent(DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule(BuildConfig.BASEURL))
                .build());
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public void setAppComponent(AppComponent mAppComponent) {
        this.mAppComponent = mAppComponent;
    }
}
