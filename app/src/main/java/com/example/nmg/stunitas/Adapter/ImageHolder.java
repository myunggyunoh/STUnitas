package com.example.nmg.stunitas.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.nmg.stunitas.Data.documents;
import com.example.nmg.stunitas.R;

import retrofit2.http.Url;

public class ImageHolder extends MainHolder{

    private ImageView imageView;
    private ProgressBar progress;


    public ImageHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        progress = itemView.findViewById(R.id.progress);
    }

    @Override
    public void setMessageData(documents data, Context context) {
        Log.d("STUnitas","셋데이터 : " + data.getImage_url());
        String url = data.getImage_url();
//        Glide.with(context).load(url).into(imageView);


        progress.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progress.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

    }
}