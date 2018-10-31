package com.example.qqmusic;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.qqmusic.data.LocalMusic;
import com.example.qqmusic.data.LocalPlayHistory;
import com.example.qqmusic.data.PlayHistory;

import org.litepal.LitePal;

import java.util.List;


public class LocalMusicFragment extends Fragment {

    private MainActivity mainActivity;
    private ImageView exit_icon;
    private Button query;
    List<LocalMusic> musicList;
    RecyclerView localMusicRecyclerView;
    private LocalMusicAdapter localMusicRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_local_music, container, false);

        init(view);
        queryListener();
        exit_iconListener();
        initLocalMusicRecyclerViewOnCreate();
        return view;
    }

    private void initLocalMusicRecyclerViewOnCreate() {
        if (mainActivity.localMusicList.size() != 0) {
            musicList = mainActivity.localMusicList;
            initLocalMusicRecyclerView();
        }
    }

    private void initLocalMusicRecyclerView() {
        localMusicRecyclerViewAdapter = new LocalMusicAdapter(musicList);
        localMusicRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        localMusicRecyclerViewAdapter.setOnItemClickListenter(new LocalMusicAdapter
                .OnItemClickListenter() {
            @Override
            public void onItemClick(View view, int position) {
                playLocalMusic(musicList.get(position));
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                localMusicRecyclerView.setAdapter(localMusicRecyclerViewAdapter);
            }
        });

    }

    private void playLocalMusic(LocalMusic localMusic) {
        mainActivity.setMusic(localMusic.getPath());
        mainActivity.setBottomNameAndVersion(localMusic.getSong(),localMusic.getSinger());
        LitePal.deleteAll(LocalPlayHistory.class);
        LitePal.deleteAll(PlayHistory.class);
        LocalPlayHistory localPlayHistory = new LocalPlayHistory();
        localPlayHistory.setAlbum(localMusic.getAlbum());
        localPlayHistory.setDuration(localMusic.getDuration());
        localPlayHistory.setPath(localMusic.getPath());
        localPlayHistory.setSize(localMusic.getSize());
        localPlayHistory.setSong(localMusic.getSong());
        localPlayHistory.setSinger(localMusic.getSinger());
        localPlayHistory.save();
    }

    private void queryListener() {
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission
                        .READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest
                            .permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    if (mainActivity.localMusicList != null && mainActivity.localMusicList.size()
                            > 0) {
                        musicList = mainActivity.localMusicList;
                    } else {
                        mainActivity.localMusicList = Util.getLocalMusic(getContext());
                        musicList = mainActivity.localMusicList;
                    }
                    initLocalMusicRecyclerView();
                }
            }
        });
    }

    private void exit_iconListener() {
        exit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = mainActivity.findViewById(R.id
                        .layout_apart_linearlayout);
                FrameLayout frameLayout = getActivity().findViewById(R.id.layout_apart_framelayout);
                frameLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LinearLayout linearLayout = getActivity().findViewById(R.id.layout_apart_linearlayout);
        FrameLayout frameLayout = getActivity().findViewById(R.id.layout_apart_framelayout);
        frameLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }


    private void init(View view) {
        localMusicRecyclerView = view.findViewById(R.id.local_recyclerView);
        query = view.findViewById(R.id.query);
        exit_icon = view.findViewById(R.id.exit_search);
        mainActivity = (MainActivity) getActivity();
    }


}
