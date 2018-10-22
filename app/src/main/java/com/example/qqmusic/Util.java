package com.example.qqmusic;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Util {


    //请求网络的
    public static void sendRequest(String name, final okhttp3.Callback callback) {
        final String URL = Final_music.api_music_list_front + name + Final_music
                .api_music_list_rear;
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

    public static MusicItem requestDataOfMusicItem(String musicJosn) {
        Gson gson = new Gson();
        MusicItem musicBack = gson.fromJson(musicJosn,
                MusicItem.class);
        return musicBack;
    }

}