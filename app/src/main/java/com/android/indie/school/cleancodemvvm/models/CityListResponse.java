package com.android.indie.school.cleancodemvvm.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityListResponse {

    @SerializedName("data")
    private List<CityListData> data = new ArrayList<CityListData>();
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;

    public List<CityListData> getData() {
        return data;
    }

    public void setData(List<CityListData> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CityListResponse{" +
                "data=" + data +
                ", message=" + message +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }

        CityListResponse response = (CityListResponse) obj;
        return status == response.getStatus() &&
                ((null != data) ? data.equals(response.getData()) : response.getData() == null &&
                        ((null != message) ? !message.equals(response.getMessage()) : null == response.getMessage()));

    }

    @Override
    public int hashCode() {
        int result = (null != data) ? data.hashCode() : 0;
        final int prime = 31;
        result = prime * result + ((null != message) ? message.hashCode() : 0);
        result = prime * result + status;
        return result;
    }
}