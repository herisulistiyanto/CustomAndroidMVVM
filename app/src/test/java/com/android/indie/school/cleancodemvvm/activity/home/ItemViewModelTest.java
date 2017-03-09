package com.android.indie.school.cleancodemvvm.activity.home;

import com.android.indie.school.cleancodemvvm.models.CityListData;
import com.android.indie.school.cleancodemvvm.models.CityListResponse;
import com.android.indie.school.cleancodemvvm.util.ResponseGenerator;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;

/**
 * Created by herisulistiyanto on 3/9/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemViewModelTest {

    private CityListResponse response;
    private CityListData cityListData;

    private static final String BASE_NAME = "Yogyakarta";
    private static final String BASE_DESC = "201 Hotel";
    private static final String BASE_BACKGROUND = "http://www.tnetnoc.com/hotelphotos/591/327591/2631759-The-Cangkringan-Spa-Villas-Hotel-Yogyakarta-Guest-Room-3-RTS.jpg";

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        String jsonString = new ResponseGenerator("response_get_city_list.json").readAll();
        response = gson.fromJson(jsonString, CityListResponse.class);
        cityListData = new CityListData();
        cityListData = response.getData().get(0);
    }

    @Test
    public void testShouldGetCityNameYogyakarta() throws Exception {
        final String name = cityListData.getName();
        assertEquals(name, BASE_NAME);
    }

    @Test
    public void testShouldGetDescription201Hotel() throws Exception {
        final String desc = cityListData.getDescription();
        assertEquals(desc, BASE_DESC);
    }

    @Test
    public void testShouldGetLinkBackground() throws Exception {
        final String bg = cityListData.getBackground();
        assertEquals(bg, BASE_BACKGROUND);
    }

}