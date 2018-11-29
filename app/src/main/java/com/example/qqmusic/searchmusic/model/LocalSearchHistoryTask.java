package com.example.qqmusic.searchmusic.model;

public interface LocalSearchHistoryTask<T>{

    void getSearchHistory(LoadSearchHistoryCallBack<T> loadSearchHistoryCallBack);

}
