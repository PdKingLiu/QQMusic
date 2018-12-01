package com.example.qqmusic.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.qqmusic.data.LocalMusic;

import java.util.ArrayList;
import java.util.List;

public class Util {




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

                if (localMusic.getSong().indexOf('-') != -1) {
                    if (localMusic.getSong().indexOf('[') != -1) {
                        localMusic.song = localMusic.getSong().split(" - ")[1].split("\\[")[0];
                    } else {
                        localMusic.song = localMusic.getSong().split(" - ")[1].split(".m")[0];
                    }
                } else {
                    if (localMusic.getSong().indexOf('[') != -1) {
                        localMusic.song = localMusic.getSong().split("\\[")[0];
                    } else {
                        localMusic.song = localMusic.getSong().split(".m")[0];
                    }
                }
                localMusic.save();
                list.add(localMusic);
            }
            cursor.close();
        }

        return list;
    }

}