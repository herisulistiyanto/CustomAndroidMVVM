package com.android.indie.school.cleancodemvvm.deps.component;

import com.android.indie.school.cleancodemvvm.activity.home.HomeActivity;
import com.android.indie.school.cleancodemvvm.deps.module.AppModule;
import com.android.indie.school.cleancodemvvm.deps.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by herisulistiyanto on 12/6/16.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(HomeActivity homeActivity);
}
