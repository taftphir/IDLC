package com.alugara.idlc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alugara.idlc.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ThreadViewHolder> {

    private ArrayList<String> dummyData;
    private Context context;

    public CommentAdapter(ArrayList<String> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    @Override
    public CommentAdapter.ThreadViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_comment, viewGroup, false);
        return new ThreadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ThreadViewHolder holder, final int i) {
        holder.usernameTv.setText(dummyData.get(i));
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class ThreadViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTv;
        public ThreadViewHolder(View itemView) {
            super(itemView);
            usernameTv = itemView.findViewById(R.id.commented_user_tv);
        }
    }
}
