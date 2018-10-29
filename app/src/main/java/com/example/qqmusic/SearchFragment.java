package com.example.qqmusic;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.qqmusic.data.PlayHistory;
import com.example.qqmusic.data.SearchHistory;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.litepal.LitePal;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SearchFragment extends Fragment {

    private ImageView exit_search;
    private ImageView speak_search;
    private EditText search_edit;
    private ListView search_history;
    private ArrayAdapter<String> historyList;
    private String input_string;
    private RecyclerView music_list_recyclerview;
    private MusicItemAdapter musicItemAdapter;
    private SmartRefreshLayout smartRefreshLayout;
    private MusicItem.DataBean.SongBean.ListBean listBean;
    private int flag = 0;
    private List<MusicItem.DataBean.SongBean.ListBean> adapterList;
    private List<SearchHistory> searchHistories = new ArrayList<>();
    private LinearLayout history_view;
    private LinearLayout search_success;
    private TextView cleanHistory;

    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        mainActivity = (MainActivity) getActivity();
        LitePal.getDatabase();
        cleanListener();
        editIsEmplyListener();
        initHistoryAdapter();
        inputListener();
        listenerImage();
        reflushListener();
        search_historyListener();
        return view;
    }

    //历史记录监听点击
    private void search_historyListener() {
        search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Lpp===", position + "");
                Log.d("Lpp===", historyList.getItem(position) + "");
                String s = historyList.getItem(position);
                input_string = s;
                search_edit.setText(input_string);
                searchMusicByItemClick(input_string);
            }
        });
    }

    //通过历史item点击搜索
    private void searchMusicByItemClick(String input_string) {
        if (historyList != null) {
            flag = 0;
            int i;
            for (i = 0; i < historyList.getCount(); i++) {
                if (historyList.getItem(i).equals(input_string)) {
                    break;
                }
            }
            if (i == historyList.getCount()) {
                SearchHistory sh = new SearchHistory(input_string);
                sh.save();
                searchHistories.add(sh);
                historyList.add(input_string);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changLayout("history_GONE");
                        historyList.notifyDataSetChanged();
                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        changLayout("history_GONE");
                    }
                });
            }
        }
        Util.sendRequest(input_string, ++flag, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws
                    IOException {

                changLayout("success_VISIBLE");
                String result = response.body().string();
                MusicItem musicItem = Util.requestDataOfMusicItem(result);
                changeListView(musicItem);
            }
        });
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                .getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }


    private void editIsEmplyListener() {
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 && historyList.getCount() != 0) {
                    changLayout("history_VISIBLE");
                    changLayout("success_GONE");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void cleanListener() {
        cleanHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(SearchHistory.class);
                changLayout("history_GONE");
                historyList.clear();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        historyList.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void initHistoryAdapter() {
        searchHistories = LitePal.findAll(SearchHistory.class);
        Log.d("Lpp++", searchHistories == null ? "null" : search_history.toString());
        List<String> list = new ArrayList<>();
        historyList = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                list);
        if (searchHistories.size() == 0) {
            search_history.setAdapter(historyList);
            changLayout("history_GONE");
        } else {
            for (SearchHistory s : searchHistories) {
                list.add(s.getMusicName());
            }
            search_history.setAdapter(historyList);
        }
    }

    //监听输入
    private void inputListener() {
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    input_string = search_edit.getText().toString();
                    if (input_string.equals("")) {
                    } else {
                        flag = 0;
                        if (historyList != null) {
                            int i;
                            for (i = 0; i < historyList.getCount(); i++) {
                                if (historyList.getItem(i).equals(input_string)) {
                                    break;
                                }
                            }
                            if (i == historyList.getCount()) {
                                SearchHistory sh = new SearchHistory(input_string);
                                sh.save();
                                searchHistories.add(sh);
                                historyList.add(input_string);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        changLayout("history_GONE");
                                        historyList.notifyDataSetChanged();
                                    }
                                });
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        changLayout("history_GONE");
                                    }
                                });
                            }
                        }
                        Util.sendRequest(input_string, ++flag, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws
                                    IOException {

                                changLayout("success_VISIBLE");
                                String result = response.body().string();
                                MusicItem musicItem = Util.requestDataOfMusicItem(result);
                                changeListView(musicItem);
                            }
                        });

                        // 点击搜索后隐藏输入法
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                                .getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                }
                return false;
            }
        });
    }

    private void changLayout(final String s) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (s) {
                    case "history_GONE":
                        history_view.setVisibility(View.GONE);
                        break;
                    case "history_VISIBLE":
                        history_view.setVisibility(View.VISIBLE);
                        break;
                    case "success_GONE":
                        search_success.setVisibility(View.GONE);
                        break;
                    case "success_VISIBLE":
                        search_success.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    //加载歌曲菜单
    private void changeListView(final MusicItem musicItem) {
        adapterList = musicItem.data.song.list;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                musicItemAdapter = new MusicItemAdapter(adapterList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                music_list_recyclerview.setLayoutManager(layoutManager);
                music_list_recyclerview.setAdapter(musicItemAdapter);
                recyclerViewListener();
            }
        });
    }


    // 监听recycleView 点击
    private void recyclerViewListener() {
        musicItemAdapter.setOnItemClickListenter(new MusicItemAdapter.OnItemClickListenter() {
            @Override
            public void onItemClick(View view, int position) {
                listBean = adapterList.get(position);
                requestMusic(listBean);
            }
        });
    }

    // 监听刷新
    private void reflushListener() {
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (flag == 10) {
                    Toast.makeText(getContext(), "没有更多啦！", Toast.LENGTH_SHORT).show();
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
                            for (MusicItem.DataBean.SongBean.ListBean listBean :
                                    musicItem.data.song.list) {
                                adapterList.add(listBean);
                            }
                            getActivity().runOnUiThread(new Runnable() {
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


    public void requestMusic(final MusicItem.DataBean.SongBean.ListBean listBean) {

        final String icon_url = Final_music.music_icon_front
                + listBean.album.mid
                + Final_music.music_icon_rear;

        final String music_url = Final_music.music_url_front
                + listBean.file.mediaMid
                + Final_music.music_url_rear;

        Log.d("Lpp", "icon_url:" + icon_url);
        Log.d("Lpp", "music_url:" + music_url);
        mainActivity.setMusicVersionIcon(icon_url);
        mainActivity.setBottomNameAndVersion(listBean.title,
                listBean.singer.get(0).name);
        mainActivity.setMusic(music_url);

        saveMusicPlayHistory(icon_url, music_url, listBean.title,
                listBean.singer.get(0).name);
    }

    //      将最近的一次播放历史存进数据库
    private void saveMusicPlayHistory(final String icon_url, final String music_url, final String
            title, final String name) {
        LitePal.deleteAll(PlayHistory.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(mainActivity).load(icon_url).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition
                            <? super Drawable> transition) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] bytes = bos.toByteArray();
                        PlayHistory playHistory = new PlayHistory(bytes, icon_url, music_url,
                                title, name);
                        playHistory.save();
                    }
                });
            }
        });
    /*    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bytes = bos.toByteArray();
        PlayHistory playHistory = new PlayHistory(bytes, icon_url, music_url, title, name);
        playHistory.save();*/
    }


    //监听返回
    private void listenerImage() {
        exit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (getActivity().getWindow().getAttributes().softInputMode == WindowManager
                        .LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
                    Log.d("hehehe", "onClick: hhhh");
                }
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                        .getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);*/
                LinearLayout linearLayout = getActivity().findViewById(R.id
                        .layout_apart_linearlayout);
                FrameLayout frameLayout = getActivity().findViewById(R.id.layout_apart_framelayout);
                frameLayout.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                getFragmentManager().popBackStack();
            }
        });
        speak_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "暂未实现", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView(View view) {
        exit_search = view.findViewById(R.id.exit_search);
        speak_search = view.findViewById(R.id.search_speak);
        search_edit = view.findViewById(R.id.search_input);
        music_list_recyclerview = view.findViewById(R.id.recyclerview);
        smartRefreshLayout = view.findViewById(R.id.refresh);

        search_history = view.findViewById(R.id.search_history);
        history_view = view.findViewById(R.id.history_view);
        search_success = view.findViewById(R.id.search_success);
        cleanHistory = view.findViewById(R.id.clean_history);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LinearLayout linearLayout = getActivity().findViewById(R.id.layout_apart_linearlayout);
        FrameLayout frameLayout = getActivity().findViewById(R.id.layout_apart_framelayout);
        frameLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
