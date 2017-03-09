package com.android.indie.school.cleancodemvvm.testutils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.nio.charset.Charset;

import okhttp3.mockwebserver.RecordedRequest;

/**
 * Created by herisulistiyanto on 3/9/17.
 */

public class RequestMatchers {

    public RequestMatchers() {
    }

    public static Matcher<RecordedRequest> httpMethodIs(final String httpMethod) {
        return new TypeSafeMatcher<RecordedRequest>() {
            @Override
            protected boolean matchesSafely(RecordedRequest item) {
                return httpMethod.equals(item.getMethod());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("getMethod should return");
            }
        };
    }

    public static Matcher<RecordedRequest> pathStartsWith(final String path) {
        return new TypeSafeMatcher<RecordedRequest>() {
            @Override
            protected boolean matchesSafely(RecordedRequest item) {
                return item.getPath().startsWith(path);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("getPath should return");
            }
        };
    }

    public static <T> Matcher<RecordedRequest> bodyIsEqual(final T expected, final Class<T> clazz) {
        return new TypeSafeMatcher<RecordedRequest>() {
            @Override
            protected boolean matchesSafely(RecordedRequest item) {
                try {
                    String json = item.getBody().clone().readString(Charset.forName("UTF-8"));
                    Object obj = (new Gson()).fromJson(json, clazz);
                    return obj.equals(expected);
                } catch (JsonSyntaxException jsonException) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("json should be same");
            }
        };
    }

    public static Matcher<RecordedRequest> containsHeader(final String header, final String value) {
        return new TypeSafeMatcher<RecordedRequest>() {
            @Override
            protected boolean matchesSafely(RecordedRequest item) {
                String headerValue = item.getHeaders().get(header);
                return value.equals(headerValue);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("header should contain");
            }
        };
    }

}
