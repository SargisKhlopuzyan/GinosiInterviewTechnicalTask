package com.example.sargiskh.guardian.network;

import com.example.sargiskh.guardian.model.Response;
import com.google.gson.annotations.SerializedName;

public class DataResponse {
    @SerializedName("response") private Response response;

    public Response getResponse() {
        return response;
    }
}
