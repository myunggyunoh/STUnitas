package com.example.nmg.stunitas.View;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.nmg.stunitas.Adapter.RecyclerAdapter;
import com.example.nmg.stunitas.Data.documents;
import com.example.nmg.stunitas.Loading.LoadingPopup;
import com.example.nmg.stunitas.MainMVP;
import com.example.nmg.stunitas.Presenter.MainPresenter;
import com.example.nmg.stunitas.R;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainMVP.View{

    private final String TAG = this.getClass().getSimpleName();


    private MainPresenter presenter;

    private EditText mSearchTextBar;

    private Context mContext;
    private Activity mActivity;
    private RecyclerView mRecyclerView;

    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
        presenter.createModel();

        mContext = this;
        mActivity = this;

        init();
        textWatcher();
        addImage();
    }


    private void init() {
        mSearchTextBar = findViewById(R.id.searchTextBar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    private void textWatcher() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged : " + charSequence);
                if (charSequence.toString().equals("나비")) {
                    presenter.loadData(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mSearchTextBar.addTextChangedListener(tw);
    }

    private void addImage() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
                if (lastVisibleItemPosition == itemTotalCount) {
                    presenter.loadAddData(mSearchTextBar.getText().toString());
                    Log.d(TAG, "addImage() - loadAddData");
                }
            }
        });
    }

    @Override
    public void search(List<documents> list) {
        mAdapter = new RecyclerAdapter(mContext);
        mAdapter.setData(list);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void addList(List<documents> list) {
        if (mAdapter != null) {
            mAdapter.setData(list);
            mAdapter.notifyDataSetChanged();
        }
    }


}
