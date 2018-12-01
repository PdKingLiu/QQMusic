package com.example.qqmusic.search.model;

import java.util.List;

public interface LocalSearchHistoryTask<T>{

     interface LoadSearchHistoryCallBack<T> {

        void onSearchHistorySuccess(List<T> list);

    }

    void getSearchHistory(LoadSearchHistoryCallBack<T> loadSearchHistoryCallBack);

}
