package com.example.qqmusic;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qqmusic.data.LocalMusic;
import com.example.qqmusic.data.LocalPlayHistory;
import com.example.qqmusic.data.PlayHistory;
import com.example.qqmusic.searchmusic.DownloadService;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.qqmusic.Final_music.MUSIC_DOWNLOAD_URL;
import static org.litepal.LitePalApplication.getContext;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout search_music;
    public LinearLayout linearLayout;
    public FrameLayout frameLayout;
    private CircleImageView bottom_circleimageview;
    private TextView bottom_musicname;
    private TextView bottom_musicversion;
    private ImageView botton_status;
    private ImageView more_menu;
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
    public List<LocalMusic> localMusicList;
    public List<LocalPlayHistory> mLocalPlayHistoryList;

    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        Intent intent = new Intent(MainActivity.this, DownloadService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        LitePal.getDatabase();
        init();
        startMusicService();
        more_menuListener();
        statusListener();
        searchListener();
        setMainPagerAdapter();
        popMenuListener();
        initBottomPlay();
        initLocalBottomPlay();
    }

    private void checkPermission() {
        String[] permissions = {Manifest.permission
                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int i : grantResults) {
                        if (i != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "拒绝权限将无法使用", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
        }
    }

    private void startMusicService() {

    }

    private void more_menuListener() {
        more_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout
                        .mian_popwindow_layout, null, false);
                LinearLayout linearLayout = view.findViewById(R.id.pop_one);
                LinearLayout linearLayout2 = view.findViewById(R.id.pop_one);
                LinearLayout linearLayout3 = view.findViewById(R.id.pop_one);
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams
                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setTouchable(true);
                final WindowManager.LayoutParams wl = getWindow().getAttributes();
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        wl.alpha = 1f;
                        getWindow().setAttributes(wl);
                    }
                });
                popupWindow.showAsDropDown(v);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                wl.alpha = 0.5f;
                getWindow().setAttributes(wl);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wl.alpha = 1f;
                        getWindow().setAttributes(wl);
                        popupWindow.dismiss();
                    }
                });
                linearLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wl.alpha = 1f;
                        getWindow().setAttributes(wl);
                        popupWindow.dismiss();
                    }
                });
                linearLayout3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        wl.alpha = 1f;
                        getWindow().setAttributes(wl);
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    private void initLocalBottomPlay() {
        mLocalPlayHistoryList = LitePal.findAll(LocalPlayHistory.class);
        if (mLocalPlayHistoryList.size() != 0) {
            LocalPlayHistory localPlayHistory = mLocalPlayHistoryList.get(0);
            bottom_musicname.setText(localPlayHistory.getSong());
            bottom_musicversion.setText(localPlayHistory.getSinger());
        }
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


    public void startDownload() {
        downloadBinder.startDownloadTask(MUSIC_DOWNLOAD_URL);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
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
                fragmentTransaction.replace(R.id.layout_apart_framelayout, new SearchFragment
                        ());
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
                } else if (mLocalPlayHistoryList.size() != 0) {
                    setMusic(mLocalPlayHistoryList.get(0).path);
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
        more_menu = findViewById(R.id.more);
        localMusicList = LitePal.findAll(LocalMusic.class);
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
