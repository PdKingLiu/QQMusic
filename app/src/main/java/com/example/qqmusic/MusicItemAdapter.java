package com.example.qqmusic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MusicItemAdapter extends RecyclerView.Adapter<MusicItemAdapter.ViewHolder> {

    public MusicItemAdapter(List<MusicItem.DataBean.SongBean.ListBean> list) {
        this.list = list;
    }

    private List<MusicItem.DataBean.SongBean.ListBean> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .music_item_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MusicItem.DataBean.SongBean.ListBean song = list.get(i);
        viewHolder.music_name.setText(song.title);
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
