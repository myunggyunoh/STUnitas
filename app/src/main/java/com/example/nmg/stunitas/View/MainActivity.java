package com.example.nmg.stunitas.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.example.nmg.stunitas.Data.documents;
import com.example.nmg.stunitas.MainMVP;
import com.example.nmg.stunitas.Presenter.MainPresenter;
import com.example.nmg.stunitas.R;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainMVP.View{

    private final String TAG = this.getClass().getSimpleName();


    private MainPresenter presenter;

    private EditText mSearchTextBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        presenter.createModel();


        findViewById();
        textWatcher();

    }

    private void findViewById() {
        mSearchTextBar = findViewById(R.id.searchTextBar);
    }

    long memberTime;

    private void textWatcher() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged : " + charSequence);

                presenter.loadData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mSearchTextBar.addTextChangedListener(tw);
    }

    @Override
    public void completed(List<documents> list) {
        //여기서 아답터 붙이고..
    }


}
