package com.example.nmg.stunitas.Main.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nmg.stunitas.Data.documents;
import com.example.nmg.stunitas.Main.MainRecyclerHolder.ImageHolder;
import com.example.nmg.stunitas.Main.MainRecyclerHolder.MainHolder;
import com.example.nmg.stunitas.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<MainHolder>  {

    private List<documents> mDocumens;
    private Context mContext;
    private final String TAG = "STUnitas";

    public RecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<documents> list) {
        mDocumens = list;
    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        MainHolder vh = new ImageHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, int i) {
        Log.d(TAG, "onBindViewHolder : " + mDocumens.get(i).getImage_url());
        mainHolder.setMessageData(mDocumens.get(i), mContext); // 홀더에서 데이터 처리를 한다.
    }
    @Override
    public int getItemViewType(int position) {
        return 1;
    }
    @Override
    public int getItemCount() {
        return (mDocumens != null) ? mDocumens.size() : 0;
    }
}
