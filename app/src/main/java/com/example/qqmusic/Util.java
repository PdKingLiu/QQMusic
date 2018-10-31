package com.example.qqmusic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    public static List<LocalMusic> getLocalMusic(Context context) {


        List<LocalMusic> list = new ArrayList<>();


        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media
                        .EXTERNAL_CONTENT_URI, null,
                null, null, MediaStore.Audio.AudioColumns.IS_MUSIC);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                LocalMusic localMusic = new LocalMusic();
                localMusic.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio
                        .Media.DISPLAY_NAME));
                localMusic.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore
                        .Audio.Media.ARTIST));
                localMusic.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio
                        .Media.DATA));
                localMusic.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio
                        .Media.DURATION));
                localMusic.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio
                        .Media.SIZE));
                localMusic.album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio
                        .Media.ALBUM));
                list.add(localMusic);
                list.size();
            }
            cursor.close();
        }

        return list;
    }

}