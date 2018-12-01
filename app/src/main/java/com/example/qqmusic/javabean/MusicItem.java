package com.example.qqmusic.javabean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MusicItem implements Serializable {

    @SerializedName("code")
    public int code;
    @SerializedName("data")
    public DataBean data;
    @SerializedName("message")
    public String message;
    @SerializedName("notice")
    public String notice;
    @SerializedName("subcode")
    public int subcode;
    @SerializedName("time")
    public int time;
    @SerializedName("tips")
    public String tips;

    public static class DataBean {
        @SerializedName("keyword")
        public String keyword;
        @SerializedName("priority")
        public int priority;
        @SerializedName("semantic")
        public SemanticBean semantic;
        @SerializedName("song")
        public SongBean song;
        @SerializedName("tab")
        public int tab;
        @SerializedName("totaltime")
        public int totaltime;
        @SerializedName("zhida")
        public ZhidaBean zhida;
        @SerializedName("qc")
        public List<?> qc;
        @SerializedName("taglist")
        public List<?> taglist;

        public static class SemanticBean {
            @SerializedName("curnum")
            public int curnum;
            @SerializedName("curpage")
            public int curpage;
            @SerializedName("totalnum")
            public int totalnum;
            @SerializedName("list")
            public List<?> list;
        }

        public static class SongBean {
            @SerializedName("curnum")
            public int curnum;
            @SerializedName("curpage")
            public int curpage;
            @SerializedName("totalnum")
            public int totalnum;
            @SerializedName("list")
            public List<ListBean> list;

            public static class ListBean {
                @SerializedName("action")
                public ActionBean action;
                @SerializedName("album")
                public AlbumBean album;
                @SerializedName("chinesesinger")
                public int chinesesinger;
                @SerializedName("desc")
                public String desc;
                @SerializedName("desc_hilight")
                public String descHilight;
                @SerializedName("docid")
                public String docid;
                @SerializedName("file")
                public FileBean file;
                @SerializedName("fnote")
                public int fnote;
                @SerializedName("genre")
                public int genre;
                @SerializedName("id")
                public int id;
                @SerializedName("index_album")
                public int indexAlbum;
                @SerializedName("index_cd")
                public int indexCd;
                @SerializedName("interval")
                public int interval;
                @SerializedName("isonly")
                public int isonly;
                @SerializedName("ksong")
                public KsongBean ksong;
                @SerializedName("language")
                public int language;
                @SerializedName("lyric")
                public String lyric;
                @SerializedName("lyric_hilight")
                public String lyricHilight;
                @SerializedName("mid")
                public String mid;
                @SerializedName("mv")
                public MvBean mv;
                @SerializedName("name")
                public String name;
                @SerializedName("newStatus")
                public int newStatus;
                @SerializedName("nt")
                public long nt;
                @SerializedName("pay")
                public PayBean pay;
                @SerializedName("pure")
                public int pure;
                @SerializedName("status")
                public int status;
                @SerializedName("subtitle")
                public String subtitle;
                @SerializedName("t")
                public int t;
                @SerializedName("tag")
                public int tag;
                @SerializedName("time_public")
                public String timePublic;
                @SerializedName("title")
                public String title;
                @SerializedName("title_hilight")
                public String titleHilight;
                @SerializedName("type")
                public int type;
                @SerializedName("url")
                public String url;
                @SerializedName("ver")
                public int ver;
                @SerializedName("volume")
                public VolumeBean volume;
                @SerializedName("grp")
                public List<?> grp;
                @SerializedName("singer")
                public List<SingerBean> singer;

                public static class ActionBean {
                    @SerializedName("alert")
                    public int alert;
                    @SerializedName("icons")
                    public int icons;
                    @SerializedName("msg")
                    public int msg;
                    @SerializedName("switch")
                    public int switchX;
                }

                public static class AlbumBean {
                    @SerializedName("id")
                    public int id;
                    @SerializedName("mid")
                    public String mid;
                    @SerializedName("name")
                    public String name;
                    @SerializedName("subtitle")
                    public String subtitle;
                    @SerializedName("title")
                    public String title;
                    @SerializedName("title_hilight")
                    public String titleHilight;
                }

                public static class FileBean {
                    @SerializedName("media_mid")
                    public String mediaMid;
                    @SerializedName("size_128")
                    public int size128;
                    @SerializedName("size_320")
                    public int size320;
                    @SerializedName("size_aac")
                    public int sizeAac;
                    @SerializedName("size_ape")
                    public int sizeApe;
                    @SerializedName("size_dts")
                    public int sizeDts;
                    @SerializedName("size_flac")
                    public int sizeFlac;
                    @SerializedName("size_ogg")
                    public int sizeOgg;
                    @SerializedName("size_try")
                    public int sizeTry;
                    @SerializedName("strMediaMid")
                    public String strMediaMid;
                    @SerializedName("try_begin")
                    public int tryBegin;
                    @SerializedName("try_end")
                    public int tryEnd;
                }

                public static class KsongBean {
                    @SerializedName("id")
                    public int id;
                    @SerializedName("mid")
                    public String mid;
                }

                public static class MvBean {
                    @SerializedName("id")
                    public int id;
                    @SerializedName("vid")
                    public String vid;
                }

                public static class PayBean {
                    @SerializedName("pay_down")
                    public int payDown;
                    @SerializedName("pay_month")
                    public int payMonth;
                    @SerializedName("pay_play")
                    public int payPlay;
                    @SerializedName("pay_status")
                    public int payStatus;
                    @SerializedName("price_album")
                    public int priceAlbum;
                    @SerializedName("price_track")
                    public int priceTrack;
                    @SerializedName("time_free")
                    public int timeFree;
                }

                public static class VolumeBean {
                    @SerializedName("gain")
                    public double gain;
                    @SerializedName("lra")
                    public double lra;
                    @SerializedName("peak")
                    public double peak;
                }

                public static class SingerBean {
                    @SerializedName("id")
                    public int id;
                    @SerializedName("mid")
                    public String mid;
                    @SerializedName("name")
                    public String name;
                    @SerializedName("title")
                    public String title;
                    @SerializedName("title_hilight")
                    public String titleHilight;
                    @SerializedName("type")
                    public int type;
                    @SerializedName("uin")
                    public int uin;
                }
            }
        }

        public static class ZhidaBean {
            @SerializedName("chinesesinger")
            public int chinesesinger;
            @SerializedName("type")
            public int type;
        }
    }
}