package com.example.qqmusic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class LocalMusicFragment extends Fragment {

    private MainActivity mainActivity;
    private ImageView exit_icon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_local_music, container, false);

        init(view);
        exit_iconListener();
        return view;
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
        exit_icon = view.findViewById(R.id.exit_search);
        mainActivity = (MainActivity) getActivity();
    }


}
