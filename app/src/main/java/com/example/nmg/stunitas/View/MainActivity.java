package com.example.nmg.stunitas.View;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nmg.stunitas.RetroFit.ApiInterface;
import com.example.nmg.stunitas.Data.SearchData;
import com.example.nmg.stunitas.R;
import com.example.nmg.stunitas.RetroFit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private final String DAUM_URL = "https://dapi.kakao.com";

    private EditText mSearchTextBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        textWatcher();

    }

    private void findViewById() {
        mSearchTextBar = findViewById(R.id.searchTextBar);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
//                listSearch();
            }
        });
    }

    private void textWatcher() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged : " + charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mSearchTextBar.addTextChangedListener(tw);
    }



    private void search() {
        Log.d(TAG, "서치 시작");

        Retrofit retrofit = RetrofitClient.getClient(DAUM_URL);
        ApiInterface retrofitExService = retrofit.create(ApiInterface.class);

        retrofitExService.getSearchImage("나비", "accuracy", 1,  10).enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(@NonNull Call<SearchData> call, @NonNull Response<SearchData> response) {
                if (response.isSuccessful()){
                    SearchData data = response.body();
                    Log.d(TAG,"토탈카운터 : " + data.getMeta().getTotal_count());
                    Log.d(TAG, "이름 : " + data.getDocuments().get(0).getCollection());
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


}
