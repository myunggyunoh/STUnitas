package com.example.nmg.stunitas.Presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nmg.stunitas.Data.SearchData;
import com.example.nmg.stunitas.Data.documents;
import com.example.nmg.stunitas.MainMVP;
import com.example.nmg.stunitas.Model.MainModel;
import com.example.nmg.stunitas.RetroFit.ApiInterface;
import com.example.nmg.stunitas.RetroFit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainPresenter implements MainMVP.Presenter {

    private MainMVP.View view;
    private MainModel model;

    private final String TAG = "MainPresenter";

    private final String DAUM_URL = "https://dapi.kakao.com";

    private List<documents> list;
    private List<documents> addList;

    private final int PAGE = 1;
    private final int COUNT = 5;

    private int page;
    private int count;

    public MainPresenter(MainMVP.View view){
        this.view = view;
    }

    @Override
    public void loadData(final String text) {
        if(model == null && text == null){
            Log.d(TAG,"model is null");
            return;
        }

        page = PAGE;
        count = COUNT;

        Retrofit retrofit = RetrofitClient.getClient(DAUM_URL);
        ApiInterface retrofitExService = retrofit.create(ApiInterface.class);

        retrofitExService.getSearchImage(text, "accuracy", page,  count).enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(@NonNull Call<SearchData> call, @NonNull Response<SearchData> response) {
                if (response.isSuccessful()){
                    SearchData data = response.body();

                    list = data.getDocuments();
                    view.search(list);
                }else{
                    Log.d(TAG, "Error");
                }

            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                Log.d(TAG, "에러");
            }
        });

    }


    @Override
    public void loadAddData(String text) {
        page++;
        count = count + COUNT;

        Retrofit retrofit = RetrofitClient.getClient(DAUM_URL);
        ApiInterface retrofitExService = retrofit.create(ApiInterface.class);
        retrofitExService.getSearchImage(text, "accuracy", page,  count).enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(@NonNull Call<SearchData> call, @NonNull Response<SearchData> response) {
                if (response.isSuccessful()){
                    SearchData data = response.body();

                    addList = data.getDocuments();

                    list.addAll(addList);
                    view.addList(list);
                }else{
                    Log.d(TAG, "Error");
                }

            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                Log.d(TAG, "에러");
            }
        });

    }

    @Override
    public void createModel() {
        model = new MainModel();
    }

}
