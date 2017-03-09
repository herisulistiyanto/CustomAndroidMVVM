package com.android.indie.school.cleancodemvvm.testutils;

import android.util.Log;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import okhttp3.mockwebserver.RecordedRequest;

/**
 * Created by herisulistiyanto on 3/9/17.
 */

public class CustomMatcher {

    private static final String TAG = "CustomMatcher";

    public static Matcher<RecordedRequest> pathStartsWith(final String path) {
        return new TypeSafeMatcher<RecordedRequest>() {
            @Override
            protected boolean matchesSafely(RecordedRequest item) {
                String incomingRequest = item.getPath().trim();
                boolean match = incomingRequest.startsWith(path);
                if (!match) {
                    Log.d(TAG, String.format("Incoming request %s does not start with path %s", incomingRequest, path));
                }
                return match;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("getPath should return");
            }
        };
    }

}
