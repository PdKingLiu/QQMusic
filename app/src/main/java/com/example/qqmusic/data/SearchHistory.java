package com.example.qqmusic.data;

import org.litepal.crud.LitePalSupport;

public class SearchHistory extends LitePalSupport {

    public SearchHistory() {
    }

    public SearchHistory(String musicName) {
        this.musicName = musicName;
    }

    private String musicName;

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicName() {
        return musicName;
    }
}
