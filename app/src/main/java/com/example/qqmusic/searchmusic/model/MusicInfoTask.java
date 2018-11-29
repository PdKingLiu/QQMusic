package com.example.qqmusic.searchmusic.model;

import com.example.qqmusic.MusicItem;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MusicInfoTask implements NetTask<String> {


    private static MusicInfoTask INSTANCE = null;

    //查询音乐的接口
    public static String API_MUSIC_LIST_FRONT =
            "http://59.37.96.220/soso/fcgi-bin/client_search_cp?format=json&t=0&inCharset=GB2312" +
                    "&outCharset=utf-8&qqmusic_ver=1302&catZhida=0&p=";

    public static String API_MUSIC_LIST_MIDDLE = "&n=10&w=";

    public static String API_MUSIC_LIST_REAR = "&flag_qc=0&remoteplace=sizer.newclient" +
            ".song&new_json=1&lossless=0&aggr=1&cr=1&sem=0&force_zonghe=0";

    //获取单例
    public static MusicInfoTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MusicInfoTask();
        }
        return INSTANCE;
    }

    @Override
    public void execute(String data, final LoadTaskCallBack callBack) {
        callBack.onStart();
        String[] s = data.split("@");
        final Gson json = new Gson();
        String url = API_MUSIC_LIST_FRONT + s[0] + API_MUSIC_LIST_MIDDLE + s[1] + API_MUSIC_LIST_REAR;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultJson = response.body().string();
                MusicItem musicItem = json.fromJson(resultJson, MusicItem.class);
                callBack.onSuccess(musicItem);
                callBack.onFinish();
            }
        });

    }


}
