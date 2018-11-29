package com.example.qqmusic.searchmusic.model;

import android.content.Context;

public interface SaveLocalTask {

    void saveLocalMusic(Context context, final String icon_url, final String music_url, final String
            title, final String name);

}
