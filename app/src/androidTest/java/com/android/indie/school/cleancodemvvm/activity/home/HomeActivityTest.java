package com.android.indie.school.cleancodemvvm.activity.home;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.indie.school.cleancodemvvm.R;
import com.android.indie.school.cleancodemvvm.testutils.AssetUtils;
import com.android.indie.school.cleancodemvvm.testutils.CustomEspressoUtils;
import com.android.indie.school.cleancodemvvm.testutils.CustomMatcher;
import com.android.indie.school.cleancodemvvm.testutils.MockWebServerRule;
import com.android.indie.school.cleancodemvvm.testutils.PermissionRule;
import com.android.indie.school.cleancodemvvm.testutils.TestEnvironmentRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

/**
 * Created by herisulistiyanto on 3/9/17.
 */
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public TestEnvironmentRule testEnvironmentRule = new TestEnvironmentRule();

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    @Rule
    public PermissionRule permissionRule = new PermissionRule();

    @Rule
    public IntentsTestRule<HomeActivity> intentsTestRule = new IntentsTestRule<HomeActivity>(HomeActivity.class, true, false);

    @Mock
    public HomeViewModel viewModel;

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
        mockWebServerRule.mock(CustomMatcher.pathStartsWith("/v1/city"), 200,
                AssetUtils.readAsset("response_get_city_list.json"), null);
    }

    @Test
    public void testShouldShowProgressDuringFetchData() throws Exception {
        mockWebServerRule.mock(CustomMatcher.pathStartsWith("/v1/city"), 200,
                AssetUtils.readAsset("response_get_city_list.json"), null);
        launchActivity();
        CustomEspressoUtils.waitForViewToShow(R.id.list);
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        onView(withId(R.id.progress)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testShouldSelectCityImage() throws Exception {
        mockWebServerRule.mock(CustomMatcher.pathStartsWith("v1/city"), 200,
                AssetUtils.readAsset("response_get_city_list.json"), null);
        launchActivity();
        CustomEspressoUtils.waitForViewToShow(R.id.list);
        onView(withId(R.id.list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, ViewActions.scrollTo()));
        onView(withId(R.id.list)).check(matches(isDisplayed())).perform(ViewActions.click());
    }

    @Test
    public void testShouldDisplayListAfterReload() throws Exception {
        mockWebServerRule.mock(CustomMatcher.pathStartsWith("v1/city"), 200,
                AssetUtils.readAsset("response_get_city_list.json"), null);
        launchActivity();
        CustomEspressoUtils.waitForViewToShow(R.id.list);
        onView(withId(R.id.list)).check(matches(isDisplayed()));
        onView(withId(R.id.btnRefresh)).check(matches(isDisplayed()))
                .perform(ViewActions.click());
        CustomEspressoUtils.waitForViewToShow(R.id.list);
        onView(withId(R.id.list)).check(matches(isDisplayed()));
    }

    private void launchActivity() {
        Intent intent = new Intent(context, HomeActivity.class);
        intentsTestRule.launchActivity(intent);
    }

}