package com.example.qqmusic.searchmusic.presenter;

import android.content.Context;

import com.example.qqmusic.MusicItem;
import com.example.qqmusic.data.PlayHistory;
import com.example.qqmusic.searchmusic.javabean.SearchHistory;
import com.example.qqmusic.searchmusic.model.DeleteLocalTask;
import com.example.qqmusic.searchmusic.model.LoadLocalPlayCallBack;
import com.example.qqmusic.searchmusic.model.LoadNetSearchCallBack;
import com.example.qqmusic.searchmusic.model.LoadSearchHistoryCallBack;
import com.example.qqmusic.searchmusic.model.LocalPlayTask;
import com.example.qqmusic.searchmusic.model.LocalSearchHistoryTask;
import com.example.qqmusic.searchmusic.model.ModelTask;
import com.example.qqmusic.searchmusic.model.NetSearchTask;
import com.example.qqmusic.searchmusic.model.SaveLocalTask;

import java.util.List;

public class SearchMusicPresenter implements
        LoadNetSearchCallBack<MusicItem>,
        SearchMusicInterfaceContent.Presenter,
        LoadSearchHistoryCallBack<SearchHistory>,
        LoadLocalPlayCallBack<PlayHistory> {

    private SearchMusicInterfaceContent.View addTaskView;

    private NetSearchTask netTask;
    private LocalPlayTask localPlayTask;
    private LocalSearchHistoryTask localSearchHistoryTask;
    private SaveLocalTask saveLocalMusic;
    private DeleteLocalTask deleteLocalTask;

    public SearchMusicPresenter(SearchMusicInterfaceContent.View addTaskView,
                                ModelTask musicInfoTask) {
        this.addTaskView = addTaskView;
        this.netTask = musicInfoTask;
        this.localPlayTask = musicInfoTask;
        this.saveLocalMusic = musicInfoTask;
        this.deleteLocalTask = musicInfoTask;
        this.localSearchHistoryTask = musicInfoTask;
    }


    @Override
    public void onNetSearchSuccess(MusicItem musicItem) {
        if (addTaskView.isActive()) {
            addTaskView.setMusicList(musicItem);
        }
    }

    @Override
    public void onLocalPlaySuccess(PlayHistory playHistory) {
        addTaskView.setLocalPlay(playHistory);
    }

    @Override
    public void onSearchHistorySuccess(List<SearchHistory> list) {
        addTaskView.setLocalHistoryList(list);
    }

    @Override
    public void onStart() {
        if (addTaskView.isActive()) {
            addTaskView.showLoading();
        }
    }

    @Override
    public void onFailed() {
        if (addTaskView.isActive()) {
            addTaskView.showError();
            addTaskView.hideLoading();
        }
    }

    @Override
    public void onFinish() {
        if (addTaskView.isActive()) {
            addTaskView.hideLoading();
        }
    }

    @Override
    public void getMusicList(String musicName, int page) {
        netTask.execute(musicName + "@" + page, this);
    }

    @Override
    public void saveLastPlay(Context context, String icon_url, String music_url, String title,
                             String name) {
        saveLocalMusic.saveLocalMusic(context, icon_url, music_url, title, name);
    }

    @Override
    public void deleteAllPlayHistory() {
        deleteLocalTask.deleteAllPlayHistory();
    }

    @Override
    public void deleteAllSearchHistory() {
        deleteLocalTask.deleteAllSearchHistory();
    }

    @Override
    public void getAllSearchHistory() {
        localSearchHistoryTask.getSearchHistory(this);
    }


}
