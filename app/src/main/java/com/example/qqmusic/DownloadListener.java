package com.example.qqmusic;

public interface DownloadListener {

    public void onProgress(int progress);

    public void onSuccess();

    public void onFailed();

    public void onPaused();

    public void onCandeled();
}
