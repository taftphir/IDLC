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
import com.alugara.idlc.activities.DetailSeminarActivity;
import com.alugara.idlc.models.Seminar;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SeminarAdapter extends RecyclerView.Adapter<SeminarAdapter.SeminarViewHolder> {

    private ArrayList<Seminar> dummyData;
    private Context context;

    public SeminarAdapter(ArrayList<Seminar> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public SeminarAdapter.SeminarViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_seminar, viewGroup, false);
        return new SeminarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SeminarAdapter.SeminarViewHolder holder, final int i) {
        holder.namaItemTv.setText(dummyData.get(i).getNama());
        Glide.with(context)
                .load(dummyData.get(i).getPoster())
                .into(holder.itemImageIv);
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailSeminarActivity.class);
                intent.putExtra("seminar", dummyData.get(i));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class SeminarViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv;
        ImageView itemImageIv;
        View cardItem;
        public SeminarViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.seminar_title_tv);
            itemImageIv = itemView.findViewById(R.id.seminar_poster_iv);
            cardItem = itemView.findViewById(R.id.seminar_card);
        }
    }
}
