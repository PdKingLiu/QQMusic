package com.example.qqmusic.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qqmusic.R;
import com.example.qqmusic.javabean.MusicItem;

import java.util.List;

public class MusicItemAdapter extends RecyclerView.Adapter<MusicItemAdapter.ViewHolder>
        implements View.OnClickListener {

    public MusicItemAdapter(List<MusicItem.DataBean.SongBean.ListBean> list) {
        this.list = list;
    }

    private List<MusicItem.DataBean.SongBean.ListBean> list;

    private OnMusicItemClickListener mOnMusicItemClickListener = null;

    private OnDownloadClickListener mOnDownloadClickListener = null;


    @Override
    public void onClick(View v) {
        View vvv = (View) v.getParent();
        switch (v.getId()) {
            case R.id.group_item:
                mOnMusicItemClickListener.onMusicItemClick(v, (int) vvv.getTag());
                break;
            case R.id.music_icon:
                mOnDownloadClickListener.onDownloadItemClick(vvv, (int) vvv.getTag());
                break;
            default:
                break;
        }

    }

    public static interface OnMusicItemClickListener {
        void onMusicItemClick(View view, int position);
    }

    public static interface OnDownloadClickListener {
        void onDownloadItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnMusicItemClickListener onMusicItemClickListener,
                                       OnDownloadClickListener onDownloadClickListener) {
        this.mOnMusicItemClickListener = onMusicItemClickListener;
        this.mOnDownloadClickListener = onDownloadClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .music_item_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        LinearLayout linearLayout = view.findViewById(R.id.group_item);
        ImageView music_icon = view.findViewById(R.id.music_icon);
//        view.setOnClickListener(this);
        music_icon.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MusicItem.DataBean.SongBean.ListBean song = list.get(i);
        viewHolder.music_name.setText(song.title);
        viewHolder.itemView.setTag(i);
        String version = song.singer.get(0).name + "  ·  " + song.album.name;
        viewHolder.music_verison.setText(version);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView music_name;

        private TextView music_verison;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            music_name = itemView.findViewById(R.id.music_name);

            music_verison = itemView.findViewById(R.id.music_version);

        }
    }
}
