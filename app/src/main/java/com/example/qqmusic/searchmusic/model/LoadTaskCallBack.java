package com.example.qqmusic.searchmusic.model;

public interface LoadTaskCallBack<T> {

    void onSuccess(T t);

    void onStart();

    void onFailed();

    void onFinish();

}
