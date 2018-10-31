package com.example.qqmusic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qqmusic.data.PlayHistory;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout search_music;
    public LinearLayout linearLayout;
    public FrameLayout frameLayout;
    private CircleImageView bottom_circleimageview;
    private TextView bottom_musicname;
    private TextView bottom_musicversion;
    private ImageView botton_status;
    private ImageView drawlayout_pop;
    private MediaPlayer mediaPlayer;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;
    private List<Fragment> adapterList;
    private int mainPagerStatus = 0;
    private TextView mineText;
    private TextView musicRoomText;
    private TextView findText;
    private DrawerLayout mDrawerLayout;
    private List<PlayHistory> mPlayHistoryList;
    private ImageView localMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.getDatabase();
        init();

        statusListener();
        searchListener();
        setMainPagerAdapter();
        popMenuListener();
        initBottomPlay();
    }




    private void initBottomPlay() {
        mPlayHistoryList = LitePal.findAll(PlayHistory.class);
        if (mPlayHistoryList.size() != 0) {
            PlayHistory playHistory = mPlayHistoryList.get(0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(playHistory.getmusicBitmap(), 0,
                    playHistory.getmusicBitmap().length);
            bottom_circleimageview.setImageBitmap(bitmap);
            bottom_musicname.setText(playHistory.getMusicName());
            bottom_musicversion.setText(playHistory.getSingerName());
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    //      弹出侧滑菜单
    private void popMenuListener() {
        drawlayout_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    //      创建主界面的adapter
    private void setMainPagerAdapter() {
        final MineFragment mineFragment = new MineFragment();
        MusicRoomFragment musicRoomFragment = new MusicRoomFragment();
        FindFragment findFragment = new FindFragment();
        adapterList.add(mineFragment);
        adapterList.add(musicRoomFragment);
        adapterList.add(findFragment);
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return adapterList.get(i);
            }

            @Override
            public int getCount() {
                return adapterList.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        changeTitleStatusBefore();
                        mainPagerStatus = 0;
                        mineText.setTextSize(22);
                        mineText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                        break;
                    case 1:
                        changeTitleStatusBefore();
                        mainPagerStatus = 1;
                        musicRoomText.setTextSize(22);
                        musicRoomText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        break;
                    case 2:
                        changeTitleStatusBefore();
                        mainPagerStatus = 2;
                        findText.setTextSize(22);
                        findText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    //      更改之前的标题状态
    private void changeTitleStatusBefore() {
        switch (mainPagerStatus) {
            case 0:
                mineText.setTextSize(20);
                mineText.setTypeface(Typeface.DEFAULT);
                break;
            case 1:
                musicRoomText.setTextSize(20);
                musicRoomText.setTypeface(Typeface.DEFAULT);
                break;
            case 2:
                findText.setTextSize(20);
                findText.setTypeface(Typeface.DEFAULT);
                break;
            default:
                break;
        }
    }

    private void searchListener() {
        search_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_apart_framelayout, new SearchFragment());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                frameLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });
    }

    //      更改歌曲名字和专辑名字
    public void setBottomNameAndVersion(final String name, final String version) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bottom_musicname.setText(name);
                bottom_musicversion.setText(version);
            }
        });
    }

    //      更改专辑图片
    public void setMusicVersionIcon(final String Url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(bottom_circleimageview.getContext()).load(Url).into
                        (bottom_circleimageview);
            }
        });
    }

    //      播放歌曲
    public void setMusic(final String Url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        setBottomPauseIcon();
                    }
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(Url);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    setBottomStartIcon();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void statusListener() {
        botton_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        setBottomPauseIcon();
                    } else {
                        mediaPlayer.start();
                        setBottomStartIcon();
                    }
                } else if (mPlayHistoryList.size() != 0) {
                    setMusic(mPlayHistoryList.get(0).getMusicUrl());
                }
            }
        });
    }

    //      更改歌曲播放状态
    public void setBottomStartIcon() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                botton_status.setImageResource(R.drawable.music_start);
            }
        });
    }

    //      更改歌曲播放状态
    public void setBottomPauseIcon() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                botton_status.setImageResource(R.drawable.music_pause);
            }
        });
    }

    private void init() {

        localMusic = findViewById(R.id.local_music);
        drawlayout_pop = findViewById(R.id.menu);
        mDrawerLayout = findViewById(R.id.menu_pop);
        bottom_circleimageview = findViewById(R.id.music_icon);
        bottom_musicname = findViewById(R.id.music_name);
        bottom_musicversion = findViewById(R.id.music_version);
        botton_status = findViewById(R.id.music_status);

        search_music = findViewById(R.id.title_search);
        frameLayout = findViewById(R.id.layout_apart_framelayout);
        linearLayout = findViewById(R.id.layout_apart_linearlayout);
        viewPager = findViewById(R.id.main_pager);
        adapterList = new ArrayList<>();

        mineText = findViewById(R.id.me);
        mineText.setTextSize(22);
        mineText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        musicRoomText = findViewById(R.id.music_room);
        findText = findViewById(R.id.find);

    }

}
