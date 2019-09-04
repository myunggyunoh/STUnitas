package com.example.nmg.stunitas.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.nmg.stunitas.MainMVP;
import com.example.nmg.stunitas.Model.MainModel;

public class MainPresenter implements MainMVP.Presenter {

    private MainMVP.View view;
    private MainModel model;

    private final String TAG = "MainPresenter";

    public MainPresenter(MainMVP.View view){
        this.view = view;
    }

    @Override
    public void loadData(String text) {
        if(model == null && text == null){
            Log.d(TAG,"model is null");
            return;
        }

        view.completed(model.getList(text));

    }

    @Override
    public void createModel() {
        model = new MainModel();
    }
}
