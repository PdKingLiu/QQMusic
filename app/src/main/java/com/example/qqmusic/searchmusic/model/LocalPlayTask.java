package com.example.qqmusic.searchmusic.model;

public interface LocalPlayTask<T> {

    void getLocalPlay(LoadLocalPlayCallBack<T> loadTaskCallBack);

}