package com.example.qqmusic.title_mine.loadlocalmusic.presenter;

import android.content.Context;

import com.example.qqmusic.BasePresenter;
import com.example.qqmusic.BaseView;
import com.example.qqmusic.data.LocalMusic;
import com.example.qqmusic.data.LocalPlayHistory;

import java.util.List;

public interface InterfaceContract {

    interface Presenter extends BasePresenter{

        void getLocalMusic(Context context);

        void deleteAllLocalPlayHistory();

        void deleteAllPlayHistory();

        void saveLocalPlayHistory(LocalPlayHistory localPlayHistory);

    }

    interface View extends BaseView<Presenter> {

        void showLoading();

        boolean isActive();

        void hideLoading();

        void localMusicLoadSucceed(List<LocalMusic> localMusics);

    }


}
