package com.example.qqmusic.search.model;

public interface NetSearchTask<T> {


    interface LoadNetSearchCallBack<T> {

        void onNetSearchSuccess(T t);

        void onStart();

        void onFailed();

        void onFinish();

    }


    void execute(T data, LoadNetSearchCallBack<T> callBack);


}