package com.alugara.idlc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.models.Artikel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class InfografisAdapter extends RecyclerView.Adapter<InfografisAdapter.ArtikelViewHolder> {

    private ArrayList<Artikel> dummyData;
    private Context context;

    public InfografisAdapter(ArrayList<Artikel> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public InfografisAdapter.ArtikelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_infografis, viewGroup, false);
        return new ArtikelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfografisAdapter.ArtikelViewHolder holder, final int i) {
        holder.namaItemTv.setText(dummyData.get(i).getJudul());
        Glide.with(context)
                .load(dummyData.get(i).getFoto())
                .into(holder.itemImageIv);
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class ArtikelViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv;
        ImageView itemImageIv;
        public ArtikelViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.infografis_title_tv);
            itemImageIv = itemView.findViewById(R.id.infografis_poster_iv);
        }
    }
}
