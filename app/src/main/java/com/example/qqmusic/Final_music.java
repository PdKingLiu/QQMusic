package com.example.qqmusic;

public class Final_music {


    //下载音乐的接口
    static String music_url_front = "https://dl.stream.qqmusic.qq.com/M800";

    static String music_url_rear =
            ".mp3?vkey=B65CE7850FF54A091AE51E59382B937F8AFD9" +
                    "FABB6E684FD4325C292F8012DC8BEC3F5711851E900EE90" +
                    "B7F54844C50F764895B6C88FBDB5&guid=5150825362&fromtag=1";


    //查询音乐的接口
    static String api_music_list_front =
            "http://59.37.96.220/soso/fcgi-bin/client_search_cp?format" +
                    "=json&t=0&inCharset=GB2312&outCharset=utf-8&" +
                    "qqmusic_ver=1302&catZhida=0&p=";

    static String api_music_list_middle = "&n=10&w=";


    static String api_music_list_rear = "&flag_qc=0&remoteplace=sizer.newclient" +
            ".song&new_json=1&lossless=0&aggr=1&cr=1&sem=0&force_zonghe=0";
}
