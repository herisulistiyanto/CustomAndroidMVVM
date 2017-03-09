package com.android.indie.school.cleancodemvvm.activity.home;

import com.android.indie.school.cleancodemvvm.models.CityListResponse;
import com.android.indie.school.cleancodemvvm.networking.NetworkError;
import com.android.indie.school.cleancodemvvm.networking.Service;
import com.android.indie.school.cleancodemvvm.util.ResponseGenerator;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/**
 * Created by herisulistiyanto on 3/8/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    @Mock
    HomeViewModel homeViewModel;

    @Mock
    ItemViewModel itemViewModel;

    @Mock
    Service service;

    @Mock
    HomeView view;

    private CompositeSubscription subscriptions;
    private HomePresenter presenter;
    private CityListResponse cityListResponse;
    private NetworkError networkError;

    private static final Subscription SUBSCRIPTION = new Subscription() {
        @Override
        public void unsubscribe() {

        }

        @Override
        public boolean isUnsubscribed() {
            return false;
        }
    };

    @Before
    public void setUp() throws Exception {
        subscriptions = new CompositeSubscription();
        presenter = new HomePresenter(service);
        presenter.setView(view);
        presenter.setViewModel(homeViewModel);

        Gson gson = new Gson();
        String jsonString = new ResponseGenerator("response_get_city_list.json").readAll();
        cityListResponse = gson.fromJson(jsonString, CityListResponse.class);

        networkError = new NetworkError(new Throwable(NetworkError.DEFAULT_ERROR_MESSAGE));
    }

    @Test
    public void testShouldSetInProgressTrueWhenShowProgressWait() throws Exception {
        presenter.showProgressWait();
        verify(homeViewModel).setInProgress(true);
    }

    @Test
    public void testShouldSetInProgressFalseWhenRemoveProgressWait() throws Exception {
        presenter.removeProgressWait();
        verify(homeViewModel).setInProgress(false);
    }

    @Test
    public void testShouldGetCityList() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Service.GetCityListCallback) invocation.getArguments()[0]).onSuccess(cityListResponse);
                return SUBSCRIPTION;
            }
        }).when(service).getCityList(Matchers.<Service.GetCityListCallback>any());
        presenter.getCityList();
        verify(homeViewModel).setInProgress(true);
        verify(homeViewModel).setInProgress(false);
        verify(view).onSuccessFetchData(cityListResponse);
    }

    @Test
    public void testShouldGiveErrorGetCityList() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Service.GetCityListCallback) invocation.getArguments()[0]).onError(networkError);
                return SUBSCRIPTION;
            }
        }).when(service).getCityList(Matchers.<Service.GetCityListCallback>any());
        presenter.getCityList();
        verify(homeViewModel).setInProgress(true);
        verify(homeViewModel).setInProgress(false);
        verify(view).onErrorFetchData(NetworkError.DEFAULT_ERROR_MESSAGE);
    }

    @Test
    public void testShouldCallFetchData() throws Exception {
        presenter.reloadCityList();
        verify(view).fetchData();
    }

    @Test
    public void testShouldClearSubscriptionsOnStop() throws Exception {
        presenter.onStop();
        assertThat(subscriptions.hasSubscriptions(), is(false));
    }

}