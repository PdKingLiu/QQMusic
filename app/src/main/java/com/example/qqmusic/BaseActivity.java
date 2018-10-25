package com.example.qqmusic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity {

    public  CircleImageView circleImageView;
    public  LinearLayout layout;
    public  ImageView status_image;
    public  TextView music_Name;
    public  TextView music_Version;
    public String Url;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    public void requestMusic(final MusicItem.DataBean.SongBean.ListBean listBean) {

        final String icon_url = Final_music.music_icon_front + listBean.album.mid + Final_music
                .music_icon_rear;

        final String music_url = Final_music.music_url_front + listBean.file.mediaMid + Final_music
                .music_url_rear;

        Log.d("Lpp", "icon_url:" + icon_url);
        Log.d("Lpp", "music_url:" + music_url);
        changeMusicIcon(icon_url);

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Lpp", "three");


                    }
                });
                try {
                    Log.d("Lpp", "three0");
                    mediaPlayer.setDataSource(music_url);
                    Log.d("Lpp", "three2");
                    mediaPlayer.prepare();
                    Log.d("Lpp", "three3");
                    mediaPlayer.start();
                    Log.d("Lpp", "three4");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Lpp", "here");

                }
            }
        }).start();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("Lpp", listBean.title + "===" + listBean.singer.get(0).name + "  ·  " +
                        listBean
                        .album.name);
                setMusicNameAndVersion(listBean.title, listBean.singer.get(0).name + "  ·  " +
                        listBean
                        .album.name);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_base);
        if (circleImageView == null) {
            circleImageView = findViewById(R.id.music_icon);
            status_image = findViewById(R.id.music_status);
            music_Name = findViewById(R.id.music_name);
            music_Version = findViewById(R.id.music_version);
        }
        Log.d("Lpp", music_Name + "===" + music_Version + "===" + status_image + "===" +
                circleImageView);
    }

    public void changeMusicIcon(String url) {
        Url = url;
        Log.d("Lpp", "aa" + circleImageView.getContext());
        Glide.with(circleImageView.getContext()).load(url).into(circleImageView);
        Log.d("Lpp", url);
}

    public void setStartIcon() {
        status_image.setImageResource(R.drawable.music_start);
    }

    public void setPauseIcon() {
        status_image.setImageResource(R.drawable.music_pause);
    }

    public void setMusicNameAndVersion(String name, String Version) {
        music_Name.setText(name);
        music_Version.setText(Version);
    }

    public void statusListener() {
        status_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        setPauseIcon();
                    } else {
                        mediaPlayer.start();
                        setStartIcon();
                    }
                }
            }
        });
    }

    private void initContentView(int id) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        viewGroup.addView(layout);
        LayoutInflater.from(this).inflate(id, layout, true);
    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout linearLayout = findViewById(R.id.group_layout);
        LayoutInflater.from(this).
                inflate(layoutResID, linearLayout, true);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        layout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        layout.addView(view, params);
    }
}
