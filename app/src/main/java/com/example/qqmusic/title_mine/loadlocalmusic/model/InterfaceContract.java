package com.example.qqmusic.title_mine.loadlocalmusic.model;

import android.content.Context;

import com.example.qqmusic.data.LocalMusic;
import com.example.qqmusic.data.LocalPlayHistory;

import java.util.List;

public interface InterfaceContract {

    interface LoadLocalMusicCallBack{

        void loadSucceed(List<LocalMusic> localMusics);

    }

    void getLocalMusic(Context context, LoadLocalMusicCallBack loadLocalMusicCallBack);

    void deleteAllLocalPlayHistory();

    void deleteAllPlayHistory();

    void saveLocalPlayHistory(LocalPlayHistory localPlayHistory);


}
