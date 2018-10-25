package com.example.qqmusic;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends BaseActivity {

    private OnIconCallbackListener onIconCallbackListener;
    private ImageView exit_search;
    private ImageView speak_search;
    private EditText search_edit;
    private String input_string;
    private RecyclerView music_list;
    private MusicItemAdapter musicItemAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private MusicItem.DataBean.SongBean.ListBean listBean;
    private int flag = 0;
    private List<MusicItem.DataBean.SongBean.ListBean> adapterList;
    boolean flag_listener = true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Url != null) {
            onIconCallbackListener.changelayout(Url);
        }
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        initView();
        inputListener();
        reflushListener();
        onIconCallbackListener = (OnIconCallbackListener) getIntent().getSerializableExtra("call");


        exit_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        speak_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "暂未实现", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 监听recycleView 点击
    private void recyclerViewListener() {

        musicItemAdapter.setOnItemClickListenter(new MusicItemAdapter.OnItemClickListenter() {
            @Override
            public void onItemClick(View view, int position) {
                listBean = adapterList.get(position);
                Log.d("Lpp", listBean.name);
                Log.d("Lpp", music_Name + "===" + music_Version + "===" + status_image + "===" +
                        circleImageView);
                requestMusic(listBean);
            }
        });
    }

    private void reflushListener() {
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (flag == 10) {
                    Toast.makeText(SearchActivity.this, "没有更多啦！", Toast.LENGTH_SHORT).show();
                    smartRefreshLayout.finishLoadMore();
                } else {
                    Util.sendRequest(input_string, ++flag, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            MusicItem musicItem = Util.requestDataOfMusicItem(result);
                            for (MusicItem.DataBean.SongBean.ListBean listBean : musicItem.data
                                    .song.list
                                    ) {
                                adapterList.add(listBean);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    musicItemAdapter.notifyDataSetChanged();
                                    smartRefreshLayout.finishLoadMore();
                                }
                            });
                        }
                    });
                }
            }
        });
    }


    //监听输入
    private void inputListener() {
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    flag = 0;
                    input_string = search_edit.getText().toString();
                    Log.d("Lpp", input_string);
                    Util.sendRequest(input_string, ++flag, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
//                            Log.d("Lpp", "onFailure: ");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
//                            Log.d("Lpp", "onResponse: ");
                            String result = response.body().string();
//                            Log.d("Lpp", result);
                            MusicItem musicItem = Util.requestDataOfMusicItem(result);
//                            Log.d("Lpp", "here");
                            changeListView(musicItem);

//                            Log.d("Lpp", "three");
                        }
                    });

                    // 点击搜索后隐藏输入法
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService
                            (INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });
    }


    //加载歌曲菜单
    private void changeListView(final MusicItem musicItem) {
        adapterList = musicItem.data.song.list;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                musicItemAdapter = new MusicItemAdapter(adapterList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
                music_list.setLayoutManager(layoutManager);
                music_list.setAdapter(musicItemAdapter);
                recyclerViewListener();
            }
        });
    }

    private void init() {
        exit_search = findViewById(R.id.exit_search);
        search_edit = findViewById(R.id.search_input);
        speak_search = findViewById(R.id.search_speak);
        music_list = findViewById(R.id.recyclerview);
        smartRefreshLayout = findViewById(R.id.refresh);
    }

    private void initView() {
    }


}
