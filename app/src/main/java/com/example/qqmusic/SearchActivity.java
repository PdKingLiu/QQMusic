package com.example.qqmusic;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity implements OnClickListener {

    private ImageView exit_search;
    private ImageView speak_search;
    private EditText search_edit;
    private String input_string;
    private RecyclerView music_list;
    private MusicItemAdapter musicItemAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        initView();
        inputListener();

    }


    //监听输入
    private void inputListener() {
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    input_string = search_edit.getText().toString();
                    Log.d("Lpp", input_string);
                    Util.sendRequest(input_string, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("Lpp", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("Lpp", "onResponse: ");
                            String result = response.body().string();
                            Log.d("Lpp", result);
                            MusicItem musicItem = Util.requestDataOfMusicItem(result);
                            Log.d("Lpp", "here");
                            changeListView(musicItem);
                            Log.d("Lpp", "three");
                        }
                    });
                }
                return false;
            }
        });
    }


    //加载歌曲菜单
    private void changeListView(final MusicItem musicItem) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                musicItemAdapter = new MusicItemAdapter(musicItem.data.song.list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
                music_list.setLayoutManager(layoutManager);
                music_list.setAdapter(musicItemAdapter);
            }
        });
    }

    private void init() {
        exit_search = findViewById(R.id.exit_search);
        search_edit = findViewById(R.id.search_input);
        speak_search = findViewById(R.id.search_speak);
        music_list = findViewById(R.id.recyclerview);
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit_search:
                finish();
                break;
            case R.id.search_speak:
                Toast.makeText(this, "暂未实现", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
