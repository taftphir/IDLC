package com.alugara.idlc.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.fragments.DetailDocsFragment;
import com.alugara.idlc.models.Files;

import java.util.ArrayList;

public class RelatedItemsAdapter extends RecyclerView.Adapter<RelatedItemsAdapter.CategoryViewHolder> {

    private ArrayList<Files> dummyData;
    private Context context;

    public RelatedItemsAdapter(ArrayList<Files> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public RelatedItemsAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_card_related, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelatedItemsAdapter.CategoryViewHolder holder, final int i) {
        holder.titleTv.setText(dummyData.get(i).getName());
        holder.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) context;

                activity.getSupportActionBar().setTitle(dummyData.get(i).getName());
                Files file = dummyData.get(i);

                Bundle bundle = new Bundle();
                bundle.putParcelable("selected_files", file);
                DetailDocsFragment detailFragment = new DetailDocsFragment();
                detailFragment.setArguments(bundle);
                FragmentTransaction fragment = activity.getSupportFragmentManager().beginTransaction();
                fragment.replace(R.id.category_frame_container, detailFragment);
                fragment.addToBackStack(null);
                fragment.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        Button btnOpen;
        TextView titleTv;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            btnOpen = itemView.findViewById(R.id.btn_open_docs);
            titleTv = itemView.findViewById(R.id.title_item_tv);
        }
    }
}
