package com.example.qqmusic.search.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
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

import com.example.qqmusic.search.DownloadService;
import com.example.qqmusic.MainActivity;
import com.example.qqmusic.R;
import com.example.qqmusic.data.PlayHistory;
import com.example.qqmusic.search.adapter.MusicItemAdapter;
import com.example.qqmusic.javabean.MusicItem;
import com.example.qqmusic.data.SearchHistory;
import com.example.qqmusic.search.presenter.SearchMusicInterfaceContent;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class SearchFragment extends Fragment implements SearchMusicInterfaceContent.View {

    private Dialog mDialog;
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
    private SearchMusicInterfaceContent.Presenter mPresenter;
    MainActivity mainActivity;
    private int searchFlag = 0;


    //专辑图片url

    public static String MUSIC_ICON_FRONT = "http://y.gtimg.cn/music/photo_new/T002R300x300M000";

    public static String MUSIC_ICON_REAR = ".jpg?max_age=2592000";

    //播放音乐的接口

    public static String MUSIC_PLAY_FRONT = "http://ws.stream.qqmusic.qq.com/C100";

    public static String MUSIC_PLAY_REAR = ".m4a?fromtag=0&guid=126548448";

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
        editIsEmptyListener();
        initHistoryAdapter();
        inputListener();
        listenerImage();
        refreshListener();
        search_historyListener();
        return view;
    }


    //历史记录监听点击
    private void search_historyListener() {
        search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                changLayout("history_GONE");
                historyList.notifyDataSetChanged();
            } else {
                changLayout("history_GONE");
            }
        }
        mPresenter.getMusicList(input_string, ++flag);

    }


    private void editIsEmptyListener() {
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
                mPresenter.deleteAllSearchHistory();
                changLayout("history_GONE");
                historyList.clear();
                historyList.notifyDataSetChanged();
            }
        });
    }

    private void initHistoryAdapter() {
        mPresenter.getAllSearchHistory();
        searchHistories = LitePal.findAll(SearchHistory.class);
        List<String> list = new ArrayList<>();
        historyList = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                list);
        if (searchHistories != null && searchHistories.size() == 0) {
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
                                changLayout("history_GONE");
                                historyList.notifyDataSetChanged();
                            } else {
                                changLayout("history_GONE");
                            }
                        }
                        mPresenter.getMusicList(input_string, ++flag);

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


    private DownloadService.DownloadBinder downloadBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    // 监听recycleView 点击
    private void recyclerViewListener() {
        musicItemAdapter.setOnItemClickListener(new MusicItemAdapter.OnMusicItemClickListener() {
            @Override
            public void onMusicItemClick(View view, int position) {
                listBean = adapterList.get(position);
                playMusic(listBean);
            }
        }, new MusicItemAdapter.OnDownloadClickListener() {
            @Override
            public void onDownloadItemClick(View view, int position) {
                ((MainActivity) getActivity()).startDownload();
            }
        });
    }


    // 监听刷新
    private void refreshListener() {
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (flag == 10) {
                    Toast.makeText(getContext(), "没有更多啦！", Toast.LENGTH_SHORT).show();
                    smartRefreshLayout.finishLoadMore();
                } else {
                    searchFlag = 1;
                    mPresenter.getMusicList(input_string, ++flag);
                }
            }
        });
    }


    public void playMusic(final MusicItem.DataBean.SongBean.ListBean listBean) {

        final String icon_url = MUSIC_ICON_FRONT
                + listBean.album.mid
                + MUSIC_ICON_REAR;

        final String music_url = MUSIC_PLAY_FRONT
                + listBean.file.mediaMid
                + MUSIC_PLAY_REAR;

        mainActivity.setMusicVersionIcon(icon_url);
        mainActivity.setBottomNameAndVersion(listBean.title, listBean.singer.get(0).name);
        mainActivity.setMusic(music_url);

        mPresenter.deleteAllPlayHistory();
        mPresenter.saveLastPlay(getContext(), icon_url, music_url, listBean.title,
                listBean.singer.get(0).name);
    }


    //监听返回
    private void listenerImage() {
        exit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        mDialog = new ProgressDialog(getContext());
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


    @Override
    public void setLocalPlay(PlayHistory play) {

    }

    @Override
    public void setLocalHistoryList(List<SearchHistory> localHistoryList) {
        searchHistories = localHistoryList;
    }

    @Override
    public void setMusicList(final MusicItem musicList) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (searchFlag == 1) {
                    adapterList.addAll(musicList.data.song.list);
                    musicItemAdapter.notifyDataSetChanged();
                    smartRefreshLayout.finishLoadMore();
                    searchFlag = 0;
                } else {
                    changLayout("success_VISIBLE");
                    adapterList = musicList.data.song.list;
                    musicItemAdapter = new MusicItemAdapter(adapterList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    music_list_recyclerview.setLayoutManager(layoutManager);
                    music_list_recyclerview.setAdapter(musicItemAdapter);
                    recyclerViewListener();
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                            .getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });

    }

    @Override
    public void showLoading() {
        if (!mDialog.isShowing()) {
            Log.d("Lpp", "Thread2 + " + Thread.currentThread().getName() + "--");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog.show();
                }
            });
        }
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()) {
            Log.d("Lpp", "Thread2 + " + Thread.currentThread().getName() + "--");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mDialog.hide();
                }
            });

        }
    }

    @Override
    public void showError() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (searchFlag == 1) {
                    smartRefreshLayout.finishLoadMore();
                    searchFlag = 0;
                } else {
                    Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void setPresenter(SearchMusicInterfaceContent.Presenter presenter) {
        mPresenter = presenter;
    }
}
