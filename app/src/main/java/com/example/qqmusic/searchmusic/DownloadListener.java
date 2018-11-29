package com.example.qqmusic.searchmusic;

public interface DownloadListener {

    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCandeled();
}
