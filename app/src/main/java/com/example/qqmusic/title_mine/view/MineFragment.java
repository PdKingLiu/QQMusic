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
import android.widget.TextView;

import com.example.qqmusic.title_mine.loadlocalmusic.model.LocalMusicModel;
import com.example.qqmusic.title_mine.loadlocalmusic.presenter.LocalMusicPresenter;
import com.example.qqmusic.title_mine.loadlocalmusic.view.LocalMusicFragment;
import com.example.qqmusic.MainActivity;
import com.example.qqmusic.R;
import com.example.qqmusic.utils.FragmentUtils;

public class MineFragment extends Fragment {

    private LinearLayout localMusic;
    private MainActivity mainActivity;
    public TextView localMusicNum_Textview;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, container, false);

        init(view);
        initMineIconNum();
        localMusicListener();
        return view;
    }

    private void initMineIconNum() {
        if (((MainActivity) getActivity()).mLocalPlayHistoryList != null && ((MainActivity)
                getActivity()).mLocalPlayHistoryList.size() > 0) {
            localMusicNum_Textview.setText("" + ((MainActivity) getActivity()).mLocalPlayHistoryList
                    .size());
        } else {
            localMusicNum_Textview.setText("0");
        }
    }

    public void setIconNum(int i, int num) {
        switch (i) {
            case 1:
                localMusicNum_Textview.setText("" + num);
                break;
            default:
                break;
        }
    }

    private void init(View view) {
        localMusicNum_Textview = view.findViewById(R.id.local_music_count);
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
