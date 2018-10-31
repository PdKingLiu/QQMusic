package com.example.qqmusic;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.qqmusic.data.LocalMusic;

import org.litepal.LitePal;

import java.util.List;


public class LocalMusicFragment extends Fragment {

    private MainActivity mainActivity;
    private ImageView exit_icon;
    private Button query;
    List<LocalMusic> musicList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_local_music, container, false);

        init(view);
        queryListener();
        exit_iconListener();
        return view;
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
                    Log.d("Lpp", musicList.get(0).toString() + "\n" + musicList.get(1));

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    if (mainActivity.localMusicList != null && mainActivity.localMusicList.size()
                            > 0) {
                        musicList = mainActivity.localMusicList;
                    } else {
                        mainActivity.localMusicList = Util.getLocalMusic(getContext());
                        musicList = mainActivity.localMusicList;
                    }
                    Log.d("Lpp", musicList.get(0).toString() + "\n" + musicList.get(1));
                } else {
                    Toast.makeText(mainActivity, "You deny the permission", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                break;
        }
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
        query = view.findViewById(R.id.query);
        exit_icon = view.findViewById(R.id.exit_search);
        mainActivity = (MainActivity) getActivity();
    }


}
