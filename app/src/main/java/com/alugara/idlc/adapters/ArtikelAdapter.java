package com.alugara.idlc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.DetailArtikelActivity;
import com.alugara.idlc.models.Artikel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArtikelAdapter extends RecyclerView.Adapter<ArtikelAdapter.ArtikelViewHolder> {

    private ArrayList<Artikel> dummyData;
    private Context context;

    public ArtikelAdapter(ArrayList<Artikel> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public ArtikelAdapter.ArtikelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_artikel, viewGroup, false);
        return new ArtikelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtikelAdapter.ArtikelViewHolder holder, final int i) {
        holder.namaItemTv.setText(dummyData.get(i).getJudul());
        Glide.with(context)
                .load(dummyData.get(i).getFoto())
                .into(holder.itemImageIv);
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailArtikelActivity.class);
                intent.putExtra("artikel", dummyData.get(i));
                context.startActivity(intent);

            }
        });
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
            namaItemTv = itemView.findViewById(R.id.artikel_title_tv);
            itemImageIv = itemView.findViewById(R.id.artikel_poster_iv);
            cardItem = itemView.findViewById(R.id.artikel_card);
        }
    }
}
