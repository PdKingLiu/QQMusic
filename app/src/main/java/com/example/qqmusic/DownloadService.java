package com.example.qqmusic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class DownloadService extends Service {
    public DownloadService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {

        public void startDownload(String url) {

        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
