package com.alugara.idlc.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.CategoryActivity;
import com.alugara.idlc.fragments.DetailDocsFragment;
import com.alugara.idlc.models.Files;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.CategoryViewHolder> {

    private ArrayList<Files> dummyData;
    private Context context;

    public FileAdapter(ArrayList<Files> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public FileAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_category, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileAdapter.CategoryViewHolder holder, final int i) {
        holder.namaItemTv.setText(dummyData.get(i).getName());
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context.getClass().getSimpleName().equals("SearchFilesActivity")){
                    Intent intent = new Intent(context, CategoryActivity.class);
                    intent.putExtra("file", dummyData.get(i));
                    context.startActivity(intent);
                }else{
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

            }
        });
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv;
        View cardItem;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.nama_kategori_tv);
            cardItem = itemView.findViewById(R.id.card_item);
        }
    }
}
