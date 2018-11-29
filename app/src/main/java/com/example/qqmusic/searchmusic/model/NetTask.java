package com.example.qqmusic.searchmusic.model;

public interface NetTask<T> {

    void execute(T data, LoadTaskCallBack<T> callBack);

}