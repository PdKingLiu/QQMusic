package com.example.qqmusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class MainActivity extends BaseActivity {

    RelativeLayout search_music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
        searchViewListener();
    }

    private void searchViewListener() {
        search_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("call",new OnIconCallbackListener() {
                    @Override
                    public void changelayout(String url) {
                        Glide.with(getApplicationContext()).load(url).into(circleImageView);
                    }
                });
                startActivity(intent);
            }
        });
    }

    


    private void initView() {
    }

    private void init() {
        search_music = findViewById(R.id.title_search);
    }

}
