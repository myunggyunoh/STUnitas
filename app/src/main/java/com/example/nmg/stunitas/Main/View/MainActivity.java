package com.example.nmg.stunitas.Main.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nmg.stunitas.Main.Adapter.RecyclerAdapter;
import com.example.nmg.stunitas.Data.documents;
import com.example.nmg.stunitas.Main.MainMVP;
import com.example.nmg.stunitas.Main.Presenter.MainPresenter;
import com.example.nmg.stunitas.R;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainMVP.View{

    private final String TAG = this.getClass().getSimpleName();

    private MainPresenter mainPresenter;

    private EditText mSearchTextBar;

    private Context mContext;
    private RecyclerView mRecyclerView;

    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter();
        mainPresenter.setMainView(this);
        mContext = this;

        init();
        textWatcher();
        addSearchList();
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
                mainPresenter.loadData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mSearchTextBar.addTextChangedListener(tw);
    }

    private void addSearchList() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
                if (lastVisibleItemPosition == itemTotalCount) {
                    mainPresenter.loadAddData(mSearchTextBar.getText().toString());
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

    @Override
    public void errorToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }


}
