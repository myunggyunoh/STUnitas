package com.example.nmg.stunitas.Main.Presenter;

import android.support.annotation.NonNull;

import com.example.nmg.stunitas.Data.SearchData;
import com.example.nmg.stunitas.Data.documents;
import com.example.nmg.stunitas.Main.MainMVP;
import com.example.nmg.stunitas.RetroFit.ApiInterface;
import com.example.nmg.stunitas.RetroFit.RetrofitClient;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainPresenter implements MainMVP.Presenter {

    private MainMVP.View view;

    private final String TAG = "MainPresenter";

    private final String DAUM_URL = "https://dapi.kakao.com";

    private List<documents> list;
    private List<documents> addList;

    private final int PAGE = 1;
    private final int COUNT = 10;

    private int page;
    private int count;

    private final long START_DELAY = 1000;
    private long realTime = 0;
    private TimerTask mTask;
    private Timer mTimer;

    public MainPresenter(){
    }

    public void setMainView(MainMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadData(final String text) {
        timerStart(text);
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
                    view.errorToast("검색어가 없습니다.");
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                view.errorToast("알 수 없는 에러가 발생하였습니다.");
            }
        });

    }

    private void searchTimer(final String text) {
        mTask = new TimerTask() {
            @Override
            public void run() {
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
                            view.errorToast("검색어가 없습니다.");
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchData> call, Throwable t) {
                        view.errorToast("알 수 없는 에러가 발생하였습니다.");
                    }
                });

            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 1000);
    }

    private void timerStart(String text) {
        long tempTime = System.currentTimeMillis();
        long subtractTime = tempTime - realTime;

        if (0 <= subtractTime && START_DELAY >= subtractTime) {
            realTime = tempTime;
            mTask.cancel();
            searchTimer(text);
        } else {
            realTime = tempTime;
            searchTimer(text);
        }
    }

}
