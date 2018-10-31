package com.example.qqmusic;

import android.util.Log;
import android.widget.ListView;

import com.example.qqmusic.data.LocalMusic;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Util {


    //请求网络的
    public static void sendRequest(String name, int flag, final okhttp3.Callback callback) {
        final String URL = Final_music.api_music_list_front + flag + Final_music
                .api_music_list_middle + name + Final_music
                .api_music_list_rear;
        Log.d("Lpp", URL);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(URL).build();
                    client.newCall(request).enqueue(callback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //解析音乐搜索的
    public static MusicItem requestDataOfMusicItem(String musicJosn) {
        Gson gson = new Gson();
        MusicItem musicBack = gson.fromJson(musicJosn,
                MusicItem.class);
        return musicBack;
    }

    public static List<LocalMusic> getLocalMusic() {

        List<LocalMusic> list = new ArrayList<>();
        

    }

}