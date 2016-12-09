package com.android.indie.school.cleancodemvvm.networking;

import com.android.indie.school.cleancodemvvm.models.CityListResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by herisulistiyanto on 12/6/16.
 */

public interface NetworkService {

    @GET("v1/city")
    Observable<CityListResponse> getCityList();

}
