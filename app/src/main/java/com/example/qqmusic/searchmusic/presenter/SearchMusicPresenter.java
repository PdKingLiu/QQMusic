package com.example.qqmusic.searchmusic.presenter;

import com.example.qqmusic.MusicItem;
import com.example.qqmusic.searchmusic.model.LoadTaskCallBack;
import com.example.qqmusic.searchmusic.model.NetTask;

public class SearchMusicPresenter implements SearchMusicInterfaceContent.Presenter,
        LoadTaskCallBack<MusicItem> {

    private SearchMusicInterfaceContent.View addTaskView;

    private NetTask netTask;

    public SearchMusicPresenter(SearchMusicInterfaceContent.View addTaskView,
                                NetTask netTask) {
        this.addTaskView = addTaskView;
        this.netTask = netTask;
    }

    @Override
    public void onSuccess(MusicItem musicItem) {
        if (addTaskView.isActive()) {
            addTaskView.setMusicList(musicItem);
        }
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
}
