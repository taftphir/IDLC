package com.alugara.idlc.adapters;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.fragments.ThreadDetailFragment;

import java.util.ArrayList;

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ThreadViewHolder> {

    private ArrayList<String> dummyData;
    private Context context;

    public ThreadAdapter(ArrayList<String> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public ThreadAdapter.ThreadViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_thread, viewGroup, false);
        return new ThreadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThreadAdapter.ThreadViewHolder holder, final int i) {
        holder.judulItemTv.setText(dummyData.get(i));
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, dummyData.get(i)+" clicked", Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) context;

                activity.getSupportActionBar().setTitle(dummyData.get(i));

                ThreadDetailFragment detailFragment = new ThreadDetailFragment();
                FragmentTransaction fragment = activity.getSupportFragmentManager().beginTransaction();
                fragment.replace(R.id.frame_main_forum_container, detailFragment);
                fragment.addToBackStack(null);
                fragment.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class ThreadViewHolder extends RecyclerView.ViewHolder {
        TextView judulItemTv;
        CardView cardItem;
        public ThreadViewHolder(View itemView) {
            super(itemView);
            judulItemTv = itemView.findViewById(R.id.thread_title_tv);
            cardItem = itemView.findViewById(R.id.card_thread);
        }
    }
}
