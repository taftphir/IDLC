package com.alugara.idlc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.models.Artikel;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.ArtikelViewHolder> {

    private ArrayList<Artikel> dummyData;
    private Context context;

    public KamusAdapter(ArrayList<Artikel> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public KamusAdapter.ArtikelViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_kamus, viewGroup, false);
        return new ArtikelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KamusAdapter.ArtikelViewHolder holder, final int i) {
        holder.namaItemTv.setText(dummyData.get(i).getJudul());
        holder.itemDetailTv.setText(dummyData.get(i).getIsi());
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class ArtikelViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv, itemDetailTv;
        public ArtikelViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.kamus_title_tv);
            itemDetailTv = itemView.findViewById(R.id.kamus_detail_tv);
        }
    }
}
