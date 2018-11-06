package com.example.qqmusic;

public class Final_music {


     //专辑图片url

    static String music_icon_front = "http://y.gtimg.cn/music/photo_new/T002R300x300M000";

    static String music_icon_rear = ".jpg?max_age=2592000";

    static String MUSIC_DOWNLOAD_URL = "http://dl.stream.qqmusic.qq.com/M8000036g6g628i2gI" +
            ".mp3?vkey=9E0BAC7AE6A7617AE6E120D31EA2972EA6FE06A87D29D2F2596D40525EA8B4774A7F53B52C6AFCEF3D18E5C874200C80C9AE3ED39086ECE1&guid=5150825362&fromtag=1";

    //下载音乐的接口
    static String music_url_front = "http://ws.stream.qqmusic.qq.com/C100";

    static String music_url_rear = ".m4a?fromtag=0&guid=126548448";


    //查询音乐的接口
    static String api_music_list_front =
            "http://59.37.96.220/soso/fcgi-bin/client_search_cp?format" +
                    "=json&t=0&inCharset=GB2312&outCharset=utf-8&" +
                    "qqmusic_ver=1302&catZhida=0&p=";

    static String api_music_list_middle = "&n=10&w=";


    static String api_music_list_rear = "&flag_qc=0&remoteplace=sizer.newclient" +
            ".song&new_json=1&lossless=0&aggr=1&cr=1&sem=0&force_zonghe=0";
}
