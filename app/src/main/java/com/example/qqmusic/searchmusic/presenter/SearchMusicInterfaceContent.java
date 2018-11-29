package com.example.qqmusic.searchmusic.presenter;

import com.example.qqmusic.MusicItem;
import com.example.qqmusic.searchmusic.BaseView;

public interface SearchMusicInterfaceContent {


    //存放业务
    interface Presenter {
        //获取音乐
        void getMusicList(String musicName,int page);

    }


     interface View extends BaseView<Presenter> {

        void setMusicList(MusicItem  musicList);

        void showLoading();

        void hideLoading();

        void showError();

        boolean isActive();

    }

}
