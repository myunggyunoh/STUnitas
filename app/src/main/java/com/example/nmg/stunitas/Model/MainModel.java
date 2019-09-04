package com.example.nmg.stunitas.Model;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nmg.stunitas.Data.SearchData;
import com.example.nmg.stunitas.Data.documents;
import com.example.nmg.stunitas.RetroFit.ApiInterface;
import com.example.nmg.stunitas.RetroFit.RetrofitClient;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainModel {

    private final String TAG = "MainModel";
    private final String DAUM_URL = "https://dapi.kakao.com";

    private List<documents> list;

    public List<documents> getList(String text) {
        Log.d(TAG, "서치 시작");

        Retrofit retrofit = RetrofitClient.getClient(DAUM_URL);
        ApiInterface retrofitExService = retrofit.create(ApiInterface.class);

        retrofitExService.getSearchImage(text, "accuracy", 1,  10).enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(@NonNull Call<SearchData> call, @NonNull Response<SearchData> response) {
                if (response.isSuccessful()){
                    SearchData data = response.body();
                    Log.d(TAG,"토탈카운터 : " + data.getMeta().getTotal_count());
                    Log.d(TAG, "이름 : " + data.getDocuments().get(0).getCollection());

                    list = data.getDocuments();

                }else{
                    Log.d(TAG, "Error");
                }

            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                Log.d(TAG, "에러");
            }
        });

        return list;
    }

}
