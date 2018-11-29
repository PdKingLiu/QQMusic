package com.example.qqmusic.searchmusic.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.qqmusic.MusicItem;
import com.example.qqmusic.data.PlayHistory;
import com.example.qqmusic.searchmusic.javabean.SearchHistory;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModelTask implements
        NetSearchTask<String>,
        SaveLocalTask,
        DeleteLocalTask,
        LocalSearchHistoryTask<SearchHistory>,
        LocalPlayTask<PlayHistory>{


    private static ModelTask INSTANCE = null;

    //查询音乐的接口
    public static String API_MUSIC_LIST_FRONT =
            "http://59.37.96.220/soso/fcgi-bin/client_search_cp?format=json&t=0&inCharset=GB2312" +
                    "&outCharset=utf-8&qqmusic_ver=1302&catZhida=0&p=";

    public static String API_MUSIC_LIST_MIDDLE = "&n=10&w=";

    public static String API_MUSIC_LIST_REAR = "&flag_qc=0&remoteplace=sizer.newclient" +
            ".song&new_json=1&lossless=0&aggr=1&cr=1&sem=0&force_zonghe=0";

    //获取单例
    public static ModelTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModelTask();
        }
        return INSTANCE;
    }

    @Override
    public void execute(String data, final LoadNetSearchCallBack callBack) {
        callBack.onStart();
        String[] s = data.split("@");
        final Gson json = new Gson();
        String url = API_MUSIC_LIST_FRONT + s[0] + API_MUSIC_LIST_MIDDLE + s[1] +
                API_MUSIC_LIST_REAR;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultJson = response.body().string();
                MusicItem musicItem = json.fromJson(resultJson, MusicItem.class);
                callBack.onNetSearchSuccess(musicItem);
                callBack.onFinish();
            }
        });

    }



    @Override
    public void saveLocalMusic(Context context, final String icon_url, final String music_url,
                               final String title,
                               final String name) {
        Glide.with(context).load(icon_url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super
                    Drawable> transition) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] bytes = bos.toByteArray();
                PlayHistory playHistory = new PlayHistory(bytes, icon_url, music_url,
                        title, name);
                playHistory.save();
            }
        });

    }

    @Override
    public void deleteAllPlayHistory() {
        LitePal.deleteAll(PlayHistory.class);
    }

    @Override
    public void deleteAllSearchHistory() {
        LitePal.deleteAll(SearchHistory.class);
    }



    @Override
    public void getLocalPlay(LoadLocalPlayCallBack<PlayHistory> loadTaskCallBack) {
        loadTaskCallBack.onLocalPlaySuccess(LitePal.findAll(PlayHistory.class).get(0));
    }


    @Override
    public void getSearchHistory(LoadSearchHistoryCallBack<SearchHistory> loadSearchHistoryCallBack) {
        loadSearchHistoryCallBack.onSearchHistorySuccess(LitePal.findAll(SearchHistory.class));
    }
}
