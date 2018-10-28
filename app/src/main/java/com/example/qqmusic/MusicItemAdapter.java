package com.example.qqmusic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MusicItemAdapter extends RecyclerView.Adapter<MusicItemAdapter.ViewHolder>
        implements View.OnClickListener {

    public MusicItemAdapter(List<MusicItem.DataBean.SongBean.ListBean> list) {
        this.list = list;
    }

    private List<MusicItem.DataBean.SongBean.ListBean> list;

    private OnItemClickListenter mOnItemClickListenter = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListenter != null) {
            mOnItemClickListenter.onItemClick(v, (int) v.getTag());
        }
    }

    public static interface OnItemClickListenter {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListenter(OnItemClickListenter listenter) {
        this.mOnItemClickListenter = listenter;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .music_item_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
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
