package com.alugara.idlc.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.models.Video;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ArtikelViewHolder> {

    private ArrayList<Video> dummyData;
    private Context context;

    public VideoAdapter(ArrayList<Video> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public VideoAdapter.ArtikelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_video, viewGroup, false);
        return new ArtikelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ArtikelViewHolder holder, final int i) {
        holder.namaItemTv.setText(dummyData.get(i).getTitle());
        Glide.with(context)
                .load(dummyData.get(i).getThumbnail())
                .into(holder.itemImageIv);
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeURI = "com.google.android.youtube";
                if (appInstalledOrNot(youtubeURI)){
                    Intent intent = new  Intent(Intent.ACTION_VIEW);
                    intent.setPackage(youtubeURI);
                    intent.setData(Uri.parse(dummyData.get(i).getUrl()));
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(dummyData.get(i).getUrl()));
                    context.startActivity(intent);
                }




            }
        });
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class ArtikelViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv;
        ImageView itemImageIv;
        View cardItem;
        public ArtikelViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.video_title_tv);
            itemImageIv = itemView.findViewById(R.id.video_poster_iv);
            cardItem = itemView.findViewById(R.id.video_card);
        }
    }
}
