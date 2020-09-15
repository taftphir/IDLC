package com.alugara.idlc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.PaymentActivity;
import com.alugara.idlc.models.Paket;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CategoryViewHolder> {

    private ArrayList<Paket> dummyData;
    private Context context;

    public ProductAdapter(ArrayList<Paket> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public ProductAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_product, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.CategoryViewHolder holder, final int i) {
        ArrayList<String> dummyBenefits = new ArrayList<>();
        String benefits = dummyData.get(i).getBenefit();
        String[] benefitArray = benefits.split(",");
        for (int index = 0; index < benefitArray.length; index++){
            dummyBenefits.add(benefitArray[index]);
        }
        BenefitAdapter adapter = new BenefitAdapter(dummyBenefits, context);
        holder.namaItemTv.setText(dummyData.get(i).getTitle());
        String formattedPrice = String.format("%,d", Long.parseLong(dummyData.get(i).getPrice()));
        holder.priceItemTv.setText(formattedPrice);
        holder.rvBenefit.setAdapter(adapter);
        holder.rvBenefit.setLayoutManager(new LinearLayoutManager(context));
        holder.subsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PaymentActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv, priceItemTv;
        RecyclerView rvBenefit;
        Button subsBtn;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.product_title);
            priceItemTv = itemView.findViewById(R.id.product_price);
            rvBenefit = itemView.findViewById(R.id.rv_product_benefit);
            subsBtn = itemView.findViewById(R.id.btn_product_subs);
        }
    }
}
