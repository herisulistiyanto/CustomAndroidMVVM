package com.android.indie.school.cleancodemvvm.testutils;

import android.util.Log;

import org.hamcrest.Matcher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;


/**
 * Created by herisulistiyanto on 3/9/17.
 */

public class MockHTTPDispatcher extends Dispatcher {

    public static final String TAG = "MY_APP_TEST";

    private static final int NOT_FOUND_RESPONSE = 404;
    private Map<Matcher<RecordedRequest>, MockResponse> requestResponseMap = new HashMap();

    public MockHTTPDispatcher() {
    }

    public MockHTTPDispatcher mock(Matcher<RecordedRequest> matcher, MockResponse mockResponse) {
        this.requestResponseMap.put(matcher, mockResponse);
        return this;
    }

    @Override
    public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
        String recordedRequestPath = recordedRequest.getPath();
        Iterator iterator = this.requestResponseMap.keySet().iterator();

        Matcher mockRequest;
        do {
            if (!iterator.hasNext()) {
                return (new MockResponse()).setResponseCode(NOT_FOUND_RESPONSE);
            }

            mockRequest = (Matcher)iterator.next();
        } while (!mockRequest.matches(recordedRequest));

        MockResponse response = (MockResponse)this.requestResponseMap.get(mockRequest);
        Log.d(TAG, "dispatch return value from path : " + recordedRequestPath);
        return response;
    }

    public void clear() {
        this.requestResponseMap.clear();
    }
}
