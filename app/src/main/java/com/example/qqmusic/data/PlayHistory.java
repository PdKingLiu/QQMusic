package com.example.qqmusic.data;

import android.graphics.drawable.Drawable;

import org.litepal.crud.LitePalSupport;

public class PlayHistory extends LitePalSupport {

    private byte[] musicBitmap;
    private String imageUrl;
    private String musicUrl;
    private String musicName;
    private String singerName;

    public PlayHistory() {
    }

    public PlayHistory(byte[] musicBitmap, String imageUrl, String musicUrl, String musicName,
                       String singerName) {
        this.musicBitmap = musicBitmap;
        this.imageUrl = imageUrl;
        this.musicUrl = musicUrl;
        this.musicName = musicName;
        this.singerName = singerName;
    }

    public void setmusicBitmap(byte[] musicBitmap) {
        this.musicBitmap = musicBitmap;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public byte[] getmusicBitmap() {
        return musicBitmap;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public String getMusicName() {
        return musicName;
    }

    public String getSingerName() {
        return singerName;
    }
}
