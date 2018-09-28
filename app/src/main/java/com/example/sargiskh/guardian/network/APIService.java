package com.example.sargiskh.guardian.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIService {
    @GET Call<DataResponse> getDataSearchedByPage(@Url String url);
    @GET Call<DataResponse> getDataSearchedByPhrase(@Url String url);
}