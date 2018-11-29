package com.example.qqmusic.searchmusic.model;

import android.content.Context;

public interface NetSearchTask<T> {

    void execute(T data, LoadNetSearchCallBack<T> callBack);


}