package com.android.indie.school.cleancodemvvm.models;

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
public class CityListDataTest {

    private CityListResponse response;
    private CityListData data;

    private final String BASE_ID = "1";
    private final String BASE_NAME = "Yogyakarta";
    private final String BASE_DESC = "201 Hotel";
    private final String BASE_BACKGROUND = "http://www.tnetnoc.com/hotelphotos/591/327591/2631759-The-Cangkringan-Spa-Villas-Hotel-Yogyakarta-Guest-Room-3-RTS.jpg";

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        String jsonString = new ResponseGenerator("response_get_city_list.json").readAll();
        response = gson.fromJson(jsonString, CityListResponse.class);
        data = new CityListData();
        data = response.getData().get(0);
        //indeks 0 is
        /*
        {
          "id": "1",
          "name": "Yogyakarta",
          "description": "201 Hotel",
          "background": "http://www.tnetnoc.com/hotelphotos/591/327591/2631759-The-Cangkringan-Spa-Villas-Hotel-Yogyakarta-Guest-Room-3-RTS.jpg"
        }
        */
    }

    @Test
    public void testGetIdShouldReturn1() throws Exception {
        final String id = data.getId();
        assertEquals(id, BASE_ID);
    }

    @Test
    public void testShouldSetIdBecome2() throws Exception {
        data.setId("2");
        assertEquals(data.getId(), "2");
    }

    @Test
    public void testShouldGetNameYogyakarta() throws Exception {
        final String name = data.getName();
        assertEquals(name, BASE_NAME);
    }

    @Test
    public void testShouldSetNameBecomeNewCity() throws Exception {
        data.setName("NewCity");
        assertEquals(data.getName(), "NewCity");
    }

    @Test
    public void testShouldGetDescription201Hotel() throws Exception {
        final String desc = data.getDescription();
        assertEquals(desc, BASE_DESC);
    }

    @Test
    public void testShouldSetDescriptionBecome200Hotels() throws Exception {
        data.setDescription("200 Hotels");
        assertEquals(data.getDescription(), "200 Hotels");
    }

    @Test
    public void testShouldGetLinkBackground() throws Exception {
        final String bg = data.getBackground();
        assertEquals(bg, BASE_BACKGROUND);
    }

    @Test
    public void testShouldSetNewBackground() throws Exception {
        data.setBackground("https://media-cdn.tripadvisor.com/media/photo-s/08/dc/c3/be/the-park-lane-jakarta.jpg");
        assertEquals(data.getBackground(), "https://media-cdn.tripadvisor.com/media/photo-s/08/dc/c3/be/the-park-lane-jakarta.jpg");
    }

}