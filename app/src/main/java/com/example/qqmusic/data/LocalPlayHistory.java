package com.example.qqmusic.data;

import org.litepal.crud.LitePalSupport;

public class LocalPlayHistory extends LitePalSupport {

    public String singer;
    public String song;
    public String path;
    public int duration;
    public long size;
    public String album;

    public void setAlbum(String album) {

        this.album = album;

    }

    public String getAlbum() {

        return album;
    }

    @Override
    public String toString() {
        return "LocalMusic{" +
                "singer='" + singer + '\'' +
                ", song='" + song + '\'' +
                ", path='" + path + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", album='" + album + '\'' +
                '}';
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getSinger() {
        return singer;
    }

    public String getSong() {
        return song;
    }

    public String getPath() {
        return path;
    }

    public int getDuration() {
        return duration;
    }

    public long getSize() {
        return size;
    }
}
