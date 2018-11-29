package com.example.qqmusic.searchmusic.presenter;

import android.content.Context;

import com.example.qqmusic.MusicItem;
import com.example.qqmusic.BaseView;
import com.example.qqmusic.data.PlayHistory;
import com.example.qqmusic.searchmusic.javabean.SearchHistory;

import java.util.List;

public interface SearchMusicInterfaceContent {


    //存放业务
    interface Presenter {

        void getMusicList(String musicName, int page);

        void saveLastPlay(Context context, final String icon_url, final String music_url, final
        String
                title, final String name);

        void deleteAllPlayHistory();

        void deleteAllSearchHistory();

        void getAllSearchHistory();


    }


    interface View extends BaseView<Presenter> {

        void setLocalPlay(PlayHistory play);

        void setLocalHistoryList(List<SearchHistory> localHistoryList);

        void setMusicList(MusicItem musicList);

        void showLoading();

        void hideLoading();

        void showError();

        boolean isActive();

    }

}
