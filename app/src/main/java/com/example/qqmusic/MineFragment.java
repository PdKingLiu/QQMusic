package com.example.qqmusic;

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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_apart_framelayout,
                        new LocalMusicFragment());
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                mainActivity.frameLayout.setVisibility(View.VISIBLE);
                mainActivity.linearLayout.setVisibility(View.GONE);
            }
        });
    }
}
