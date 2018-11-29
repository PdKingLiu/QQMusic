package com.example.qqmusic.searchmusic.model;

public interface LoadNetSearchCallBack<T> {

    void onNetSearchSuccess(T t);

    void onStart();

    void onFailed();

    void onFinish();

}
