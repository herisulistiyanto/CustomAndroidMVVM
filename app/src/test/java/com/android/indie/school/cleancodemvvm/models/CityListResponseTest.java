package com.android.indie.school.cleancodemvvm.models;

import com.android.indie.school.cleancodemvvm.util.ResponseGenerator;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by herisulistiyanto on 3/9/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CityListResponseTest {

    private CityListResponse cityListResponse;

    private final String BASE_MSG = "Get city list.";
    private final int BASE_STATUS = 200;

    @Before
    public void setUp() throws Exception {
        cityListResponse = new CityListResponse();
        Gson gson = new Gson();
        final String jsonString = new ResponseGenerator("response_get_city_list.json").readAll();
        cityListResponse = gson.fromJson(jsonString, CityListResponse.class);
    }

    @Test
    public void testShouldGetValidData() throws Exception {
        List<CityListData> datas = cityListResponse.getData();
        assertNotNull(datas);
    }

    @Test
    public void testShouldSetData() throws Exception {
        List<CityListData> datas = cityListResponse.getData();
        assertNotNull(datas);
        cityListResponse.setData(null);
        assertNull(cityListResponse.getData());
        cityListResponse.setData(datas);
        assertEquals(cityListResponse.getData(), datas);
    }

    @Test
    public void testShouldGetMessage() throws Exception {
        final String msg = cityListResponse.getMessage();
        assertEquals(msg, BASE_MSG);
    }

    @Test
    public void testSouldSetMessage() throws Exception {
        final String newMsg = "new message";
        cityListResponse.setMessage(newMsg);
        assertEquals(newMsg, cityListResponse.getMessage());
    }

    @Test
    public void testShouldGetStatus200() throws Exception {
        final int status = cityListResponse.getStatus();
        assertEquals(status, BASE_STATUS);
    }

    @Test
    public void testShouldSetStatus404() throws Exception {
        cityListResponse.setStatus(404);
        assertEquals(cityListResponse.getStatus(), 404);
    }

}