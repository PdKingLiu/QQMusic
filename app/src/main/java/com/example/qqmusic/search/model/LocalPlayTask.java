package com.example.qqmusic.search.model;

public interface LocalPlayTask<T> {

      interface LoadLocalPlayCallBack<T> {

        void onLocalPlaySuccess(T t);

    }


    void getLocalPlay(LoadLocalPlayCallBack<T> loadTaskCallBack);

}