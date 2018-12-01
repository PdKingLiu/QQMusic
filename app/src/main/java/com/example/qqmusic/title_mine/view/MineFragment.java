package com.example.qqmusic.title_mine.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.qqmusic.title_mine.loadlocalmusic.model.LocalMusicModel;
import com.example.qqmusic.title_mine.loadlocalmusic.presenter.LocalMusicPresenter;
import com.example.qqmusic.title_mine.loadlocalmusic.view.LocalMusicFragment;
import com.example.qqmusic.MainActivity;
import com.example.qqmusic.R;
import com.example.qqmusic.utils.FragmentUtils;

public class MineFragment extends Fragment {

    private LinearLayout localMusic;
    private MainActivity mainActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, container, false);

        init(view);
        localMusicListener();
        return view;
    }

    private void init(View view) {
        localMusic = view.findViewById(R.id.local_music);
        mainActivity = (MainActivity) getActivity();
    }

    private void localMusicListener() {
        localMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalMusicModel localMusicModel = LocalMusicModel.getINSTANCE();
                LocalMusicFragment fragment = new LocalMusicFragment();
                LocalMusicPresenter localMusicPresenter = new LocalMusicPresenter
                        (localMusicModel, fragment);
                fragment.setPresenter(localMusicPresenter);
                FragmentUtils.replaceFragment(R.id.layout_apart_framelayout, getActivity()
                        .getSupportFragmentManager(), fragment);
                mainActivity.frameLayout.setVisibility(View.VISIBLE);
                mainActivity.linearLayout.setVisibility(View.GONE);
            }
        });
    }
}
