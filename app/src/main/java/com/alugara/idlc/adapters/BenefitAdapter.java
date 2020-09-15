package com.alugara.idlc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alugara.idlc.R;

import java.util.ArrayList;

public class BenefitAdapter extends RecyclerView.Adapter<BenefitAdapter.CategoryViewHolder> {

    private ArrayList<String> dummyData;
    private Context context;

    public BenefitAdapter(ArrayList<String> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public BenefitAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_benefit_product, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BenefitAdapter.CategoryViewHolder holder, final int i) {
        holder.namaItemTv.setText(dummyData.get(i));
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.benefit_title_tv);
        }
    }
}
