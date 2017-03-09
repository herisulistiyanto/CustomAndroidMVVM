package com.android.indie.school.cleancodemvvm.testutils;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import com.android.indie.school.cleancodemvvm.BaseApp;
import com.android.indie.school.cleancodemvvm.deps.component.DaggerAppComponent;
import com.android.indie.school.cleancodemvvm.deps.module.AppModule;
import com.android.indie.school.cleancodemvvm.deps.module.NetworkModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by herisulistiyanto on 3/9/17.
 */

public class TestEnvironmentRule implements TestRule {

    public static final String DOMAIN = MockWebServerRule.DOMAIN;
    public static final int PORT = MockWebServerRule.PORT;

    public static String domain() {
        return String.format("http://%s:%s", DOMAIN, PORT);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new TestEnvironmentStatement(base);
    }

    private static class TestEnvironmentStatement extends Statement {

        private Statement baseStatement;

        public TestEnvironmentStatement(Statement baseStatement) {
            this.baseStatement = baseStatement;
        }

        @Override
        public void evaluate() throws Throwable {
            BaseApp baseApp = (BaseApp) InstrumentationRegistry.getTargetContext().getApplicationContext();
            String mainServer = String.format("%s", domain());
            baseApp.setAppComponent(
                    DaggerAppComponent
                            .builder()
                            .appModule(new AppModule(InstrumentationRegistry.getTargetContext()))
                            .networkModule(new NetworkModule(mainServer))
                            .build()
            );
            baseStatement.evaluate();
        }
    }
}
