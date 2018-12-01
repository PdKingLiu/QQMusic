package com.example.qqmusic.title_mine.loadlocalmusic.presenter;

import android.content.Context;
import android.util.Log;

import com.example.qqmusic.data.LocalMusic;
import com.example.qqmusic.data.LocalPlayHistory;

import java.util.List;

public class LocalMusicPresenter implements InterfaceContract.Presenter {

    com.example.qqmusic.title_mine.loadlocalmusic.model.InterfaceContract interfaceContract;

    InterfaceContract.View view;

    public LocalMusicPresenter(com.example.qqmusic.title_mine.loadlocalmusic.model.InterfaceContract interfaceContract,
                               InterfaceContract.View view) {
        this.interfaceContract = interfaceContract;
        this.view = view;
    }

    @Override
    public void getLocalMusic(Context context) {
        if (view.isActive()) {
            view.showLoading();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        interfaceContract.getLocalMusic(context, new com.example.qqmusic.title_mine.loadlocalmusic.model.InterfaceContract.LoadLocalMusicCallBack() {
            @Override
            public void loadSucceed(List<LocalMusic> localMusics) {
                if (view.isActive()) {
                    view.localMusicLoadSucceed(localMusics);
                    view.hideLoading();
                }
            }
        });
    }

    @Override
    public void deleteAllLocalPlayHistory() {
        interfaceContract.deleteAllLocalPlayHistory();
    }

    @Override
    public void deleteAllPlayHistory() {
        interfaceContract.deleteAllPlayHistory();
    }

    @Override
    public void saveLocalPlayHistory(LocalPlayHistory localPlayHistory) {
        interfaceContract.saveLocalPlayHistory(localPlayHistory);
    }

    @Override
    public void start() {

    }

}
