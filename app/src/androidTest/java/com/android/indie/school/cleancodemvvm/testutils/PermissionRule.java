package com.android.indie.school.cleancodemvvm.testutils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by herisulistiyanto on 3/9/17.
 */

public class PermissionRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + getTargetContext().getPackageName() + " android.permission.READ_PHONE_STATE");
//                    getInstrumentation().getUiAutomation().executeShellCommand("pm grant " + getTargetContext().getPackageName() + " android.permission.READ_CONTACTS");
//                }
                base.evaluate();
            }
        };
    }
}
