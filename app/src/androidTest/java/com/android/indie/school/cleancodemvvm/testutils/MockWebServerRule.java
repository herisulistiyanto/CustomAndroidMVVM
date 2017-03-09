package com.android.indie.school.cleancodemvvm.testutils;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;

/**
 * Created by herisulistiyanto on 3/9/17.
 */

public class MockWebServerRule implements TestRule {

    public static final String DOMAIN = "localhost";
    public static final int PORT = 1234;
    public static final SocketPolicy DEFAULT_SOCKET_POLICY;
    public static final int DEFAULT_STATUS_CODE = 200;
    private static final String NO_RESPONSE_STRING = "";
    private MockHTTPDispatcher mockHTTPDispatcher = new MockHTTPDispatcher();
    private MockWebServer mockWebServer = new MockWebServer();

    public MockWebServerRule() {
        this.mockWebServer.setDispatcher(this.mockHTTPDispatcher);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new MockWebServerRule.MockHTTPServerStatement(base);
    }

    public void mockResponse(String path, String httpMethod, int httpResponseCode, String response) throws Exception {
        this.mockResponseWithHeaders(path, httpMethod, httpResponseCode, response, (Headers) null);
    }

    public void mockResponseWithTimeOut(String path, String httpMethod) throws Exception {
        this.mock(CoreMatchers.allOf(RequestMatchers.pathStartsWith(path), RequestMatchers.httpMethodIs(httpMethod)), DEFAULT_STATUS_CODE, "", (Headers) null, SocketPolicy.NO_RESPONSE);
    }

    public void mockResponseWithHeaders(String path, String httpMethod, int httpResponseCode, String response, Headers headers) throws Exception {
        this.mock(CoreMatchers.allOf(RequestMatchers.pathStartsWith(path), RequestMatchers.httpMethodIs(httpMethod)), httpResponseCode, response, headers, DEFAULT_SOCKET_POLICY);
    }

    public void mock(Matcher<RecordedRequest> requestMatcher, int httpResponseCode, String response, Headers headers) throws IOException {
        this.mock(requestMatcher, httpResponseCode, response, headers, DEFAULT_SOCKET_POLICY);
    }

    public void mock(Matcher<RecordedRequest> requestMatcher, int httpResponseCode, String response, Headers headers, SocketPolicy socketPolicy) throws IOException {
        MockResponse mockResponse = (new MockResponse()).setResponseCode(httpResponseCode).setBody(response).setSocketPolicy(socketPolicy);
        if (headers != null) {
            mockResponse.setHeaders(headers);
        }

        this.mockHTTPDispatcher.mock(requestMatcher, mockResponse);
    }

    public MockHTTPDispatcher getDispatcher() {
        return this.mockHTTPDispatcher;
    }

    static {
        DEFAULT_SOCKET_POLICY = SocketPolicy.KEEP_OPEN;
    }

    private class MockHTTPServerStatement extends Statement {

        private Statement baseStatement;

        public MockHTTPServerStatement(Statement baseStatement) {
            this.baseStatement = baseStatement;
        }

        @Override
        public void evaluate() throws Throwable {
            MockWebServerRule.this.mockWebServer.start(PORT);

            try {
                this.baseStatement.evaluate();
            } finally {
                MockWebServerRule.this.mockWebServer.shutdown();
            }
        }
    }
}
