package com.example.qqmusic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qqmusic.data.LocalMusic;

import java.util.List;

public class LocalMusicAdapter extends RecyclerView.Adapter<LocalMusicAdapter.ViewHolder> implements View.OnClickListener {

    private List<LocalMusic> localMusicList;

    public LocalMusicAdapter(List<LocalMusic> localMusicList) {
        this.localMusicList = localMusicList;
    }

    public OnItemClickListenter mOnItemClickListenter;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .local_music_item, viewGroup, false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void setOnItemClickListenter(OnItemClickListenter listenter) {
        this.mOnItemClickListenter = listenter;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        LocalMusic localMusic = localMusicList.get(i);
        String name = localMusic.getSong().split(" -")[1].split("\\[")[0];
        viewHolder.musicName.setText(name);
        viewHolder.musicSingerAndalbum.setText(localMusic.getSinger() + " · " + localMusic
                .getAlbum());
    }

    @Override
    public int getItemCount() {
        return localMusicList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListenter != null) {
            mOnItemClickListenter.onItemClick(v, (int) v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView musicName;

        TextView musicSingerAndalbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            musicName = itemView.findViewById(R.id.music_name);
            musicSingerAndalbum = itemView.findViewById(R.id.music_version);
        }
    }

    public static interface OnItemClickListenter {
        void onItemClick(View view, int position);
    }
}
