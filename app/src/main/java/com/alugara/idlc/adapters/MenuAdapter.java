package com.alugara.idlc.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.ArticleActivity;
import com.alugara.idlc.activities.CategoryActivity;
import com.alugara.idlc.activities.SeminarActivity;
import com.alugara.idlc.models.Kategori;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.CategoryViewHolder> {

    //TODO : create if - else condition in ArticleActivity for every category that related with artikel table
    //TODO : also add another case with video category

    private ArrayList<Kategori> kategoriList;
    private Context context;

    public MenuAdapter(ArrayList<Kategori> kategori, Context context) {
        this.kategoriList = kategori;
        this.context = context;
    }

    @Override
    public MenuAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_menu, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.CategoryViewHolder holder, final int i) {
        holder.namaItemTv.setText(kategoriList.get(i).getName());
        TypedArray imgs = context.getResources().obtainTypedArray(R.array.array_icon_cat);
        int index = Integer.parseInt(kategoriList.get(i).getImg());
        holder.imgMenu.setImageResource(imgs.getResourceId(index, -1));
        holder.layoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (kategoriList.get(i).getName()){
                    case "SEMINAR":
                        Intent intent = new Intent(context, SeminarActivity.class);
                        intent.putExtra("category", kategoriList.get(i));
                        context.startActivity(intent);
                        break;

                    case "ARTIKEL":
                    case "INFOGRAFIS":
                    case "TIPS HUKUM PRAKTIS":
                    case "KAMUS HUKUM":
                    case "VIDEO":
                        Intent artikelIntent = new Intent(context, ArticleActivity.class);
                        artikelIntent.putExtra("category", kategoriList.get(i));
                        context.startActivity(artikelIntent);
                        break;
                    case "BLOG":
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.irmadevita.com"));
                        context.startActivity(browserIntent);
                        break;
                    default:
                        Intent fileIntent = new Intent(context, CategoryActivity.class);
                        fileIntent.putExtra("parent", kategoriList.get(i).getId());
                        fileIntent.putExtra("name", kategoriList.get(i).getName());
                        context.startActivity(fileIntent);
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv;
        ImageView imgMenu;
        RelativeLayout layoutContainer;
        public CategoryViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.menu_name_tv);
            imgMenu = itemView.findViewById(R.id.img_menu);
            layoutContainer = itemView.findViewById(R.id.layout_container);
        }
    }
}
