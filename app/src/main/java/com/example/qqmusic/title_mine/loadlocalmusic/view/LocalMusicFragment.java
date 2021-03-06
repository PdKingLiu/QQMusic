package com.example.qqmusic.title_mine.loadlocalmusic.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.qqmusic.MainActivity;
import com.example.qqmusic.R;
import com.example.qqmusic.title_mine.loadlocalmusic.presenter.InterfaceContract;
import com.example.qqmusic.data.LocalMusic;
import com.example.qqmusic.data.LocalPlayHistory;
import com.example.qqmusic.data.PlayHistory;
import com.example.qqmusic.title_mine.loadlocalmusic.adapter.LocalMusicAdapter;
import com.example.qqmusic.title_mine.view.MineFragment;

import org.litepal.LitePal;

import java.util.List;


public class LocalMusicFragment extends Fragment implements InterfaceContract.View {


    private Dialog mDialog;
    private ImageView music_icon;
    private MainActivity mainActivity;
    private ImageView exit_icon;
    List<LocalMusic> musicList;
    PopupWindow popupWindow;
    RecyclerView localMusicRecyclerView;
    private LocalMusicAdapter localMusicRecyclerViewAdapter;
    private LinearLayout scan_local_music;
    private InterfaceContract.Presenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        init(view);
        music_iconListener();
        exit_iconListener();
        initLocalMusicRecyclerViewOnCreate();
        return view;
    }


    private void music_iconListener() {
        music_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.popwindow, null,
                        false);
                scan_local_music = view.findViewById(R.id.pop_two);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams
                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                final WindowManager.LayoutParams wl = getActivity().getWindow().getAttributes();
                popupWindow.setTouchable(true);
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        wl.alpha = 1f;
                        getActivity().getWindow().setAttributes(wl);
                    }
                });
                popupWindow.showAsDropDown(v);
                wl.alpha = 0.5f;
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(wl);
                scan_local_music.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        wl.alpha = 1f;
                        getActivity().getWindow().setAttributes(wl);
                        mPresenter.getLocalMusic(getContext());
                        initLocalMusicRecyclerView();
                    }
                });
            }
        });
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
        mainActivity.setBottomNameAndVersion(localMusic.getSong(), localMusic.getSinger());
        mPresenter.deleteAllLocalPlayHistory();
        mPresenter.deleteAllPlayHistory();
        LocalPlayHistory localPlayHistory = new LocalPlayHistory();
        localPlayHistory.setAlbum(localMusic.getAlbum());
        localPlayHistory.setDuration(localMusic.getDuration());
        localPlayHistory.setPath(localMusic.getPath());
        localPlayHistory.setSize(localMusic.getSize());
        localPlayHistory.setSong(localMusic.getSong());
        localPlayHistory.setSinger(localMusic.getSinger());
        mPresenter.saveLocalPlayHistory(localPlayHistory);
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
        mDialog = new ProgressDialog(getActivity());
        music_icon = view.findViewById(R.id.music_icon);
        localMusicRecyclerView = view.findViewById(R.id.local_recyclerView);
        exit_icon = view.findViewById(R.id.exit_search);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void showLoading() {
        if (mDialog != null && !mDialog.isShowing()) {
            getActivity().runOnUiThread(() -> {

            });
            getActivity().runOnUiThread(() -> mDialog.show());
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.hide();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void localMusicLoadSucceed(List<LocalMusic> localMusics) {
        musicList = localMusics;
        ((MainActivity) getActivity()).localMusicList = localMusics;
        if (musicList != null) {
            ((MainActivity) getActivity()).mineFragment.setIconNum(1, musicList.size());
        }
    }

    @Override
    public void setPresenter(InterfaceContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
