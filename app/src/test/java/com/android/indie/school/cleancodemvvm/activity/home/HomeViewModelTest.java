package com.android.indie.school.cleancodemvvm.activity.home;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


/**
 * Created by herisulistiyanto on 3/9/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeViewModelTest {

    private HomeViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new HomeViewModel();
    }

    @Test
    public void testShouldIsInProgressTrue() throws Exception {
        viewModel.setInProgress(true);
        assertTrue(viewModel.isInProgress());
    }

    @Test
    public void testShouldSetInProgressFalse() throws Exception {
        viewModel.setInProgress(false);
        assertFalse(viewModel.isInProgress());
    }

}