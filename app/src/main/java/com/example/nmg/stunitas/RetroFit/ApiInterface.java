package com.example.nmg.stunitas.RetroFit;

import com.example.nmg.stunitas.Data.SearchData;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.GET;
import retrofit2.http.Headers;
public interface ApiInterface {

    String key = "2822827ed7c739b422b2dc1be630f566";

    @GET("/v2/search/image.json?")
    @Headers("Authorization: KakaoAK " + key)
    Call<SearchData> getSearchImage(
            @Query("query") String method,
            @Query("sort") String sort,
            @Query("page") int page,
            @Query("size") int count);

}