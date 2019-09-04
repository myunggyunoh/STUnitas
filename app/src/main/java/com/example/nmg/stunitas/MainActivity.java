package com.example.nmg.stunitas;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String DAUM_URL = "https://dapi.kakao.com";

    private final String TAG = this.getClass().getSimpleName();

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

        retrofitExService.getSearchImage("나비", "accuracy", 1,  10).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                if (response.isSuccessful()){
                    Data data = response.body();
                    Log.d(TAG,"토탈카운터 : " + data.getMeta().total_count);
                    Log.d(TAG, "이름 : " + data.documents.get(0).getCollection());
                }else{
                    Log.d(TAG, "Error");
                }

            }

            @Override
            public void onFailure(Call<Data > call, Throwable t) {
                Log.d(TAG, "에러");
            }
        });
    }


}
