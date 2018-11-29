package com.example.qqmusic.searchmusic.model;

import java.util.List;

public interface LoadSearchHistoryCallBack<T> {

    void onSearchHistorySuccess(List<T> list);

}
