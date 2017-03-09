package com.android.indie.school.cleancodemvvm.testutils;

import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.Checks;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Iterator;
import java.util.concurrent.TimeoutException;

/**
 * Created by herisulistiyanto on 3/9/17.
 */

public class CustomEspressoUtils {

    public CustomEspressoUtils() {
    }

    public static ViewAction waitForViewToHide(int viewId) {
        return waitForViewTo(viewId, true);
    }

    public static ViewAction waitForViewToShow(int viewId) {
        return waitForViewTo(viewId, false);
    }

    public static ViewAction waitForViewTo(final int viewId, final boolean hide) {
        return new ViewAction() {
            public static final int STANDARD_WAIT_TIME = 5000;

            public Matcher<View> getConstraints() {
                return ViewMatchers.isDisplayed();
            }

            public String getDescription() {
                return "wait for a specific view with id to " + (hide?"hide":"show") + "<" + viewId + "> during " + 5000 + " millis.";
            }

            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadUntilIdle();
                long startTime = System.currentTimeMillis();
                long endTime = startTime + 5000L;
                Matcher viewMatcher = ViewMatchers.withId(viewId);

                do {
                    boolean visible = false;
                    Iterator var9 = TreeIterables.breadthFirstViewTraversal(view).iterator();

                    while(var9.hasNext()) {
                        View child = (View)var9.next();
                        if(viewMatcher.matches(child)) {
                            visible = child.getVisibility() == View.VISIBLE;
                            break;
                        }
                    }

                    if(hide != visible) {
                        return;
                    }

                    uiController.loopMainThreadForAtLeast(100L);
                } while(System.currentTimeMillis() < endTime);

                throw (new PerformException.Builder()).withActionDescription(this.getDescription()).withViewDescription(HumanReadables.describe(view)).withCause(new TimeoutException()).build();
            }
        };
    }

    public static Matcher<View> hasNumChildren(final Matcher<Integer> numChildrenMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                return item instanceof ViewGroup && numChildrenMatcher.matches(Integer.valueOf(((ViewGroup)item).getChildCount()));
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" a view with # children is ");
                numChildrenMatcher.describeTo(description);
            }
        };
    }

    public static void stubIntent(Matcher<Intent> intentMatcher, Intent intent) {
        ActivityResult result = new ActivityResult(-1, intent);
        Intents.intending(intentMatcher).respondWith(result);
    }

    public static void stubIntent(Matcher<Intent> intentMatcher) {
        stubIntent(intentMatcher, new Intent());
    }

    public static Matcher<View> withBackGroundColor(final int color) {
        Checks.checkNotNull(Integer.valueOf(color));
        return new BoundedMatcher<View, Button>(Button.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with background color: ");
            }

            @Override
            protected boolean matchesSafely(Button item) {
                return color == ((ColorDrawable)item.getBackground()).getColor();
            }
        };
    }

    public static Matcher<View> withTextColor(final int color) {
        Checks.checkNotNull(Integer.valueOf(color));
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with text color: ");
            }

            @Override
            protected boolean matchesSafely(TextView item) {
                return color == item.getCurrentTextColor();
            }
        };
    }
}
