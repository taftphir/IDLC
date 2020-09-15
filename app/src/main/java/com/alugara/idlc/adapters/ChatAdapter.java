package com.alugara.idlc.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.models.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> dummyData;
    private Context context;
    private String usersID, adminID;

    public void clearData(){
        dummyData.clear();
        notifyDataSetChanged();
    }

    public ChatAdapter(ArrayList<Message> dummyData, Context context, String usersID, String adminID) {
        this.dummyData = dummyData;
        this.context = context;
        this.usersID = usersID;
        this.adminID = adminID;
    }

    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_message, viewGroup, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ChatViewHolder holder, final int i) {
        holder.messageItemTv.setText(dummyData.get(i).getMessage());
        if (dummyData.get(i).getFrom().equals(usersID)
                && dummyData.get(i).getTo().equals(adminID)){

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.cardItemLayout.getLayoutParams();
            params.gravity = Gravity.RIGHT;

            holder.cardItemLayout.setLayoutParams(params);
        }else{
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.cardItemLayout.getLayoutParams();
            params.gravity = Gravity.LEFT;

            holder.cardItemLayout.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageItemTv;
        CardView cardItemLayout;
        public ChatViewHolder(View itemView) {
            super(itemView);
            messageItemTv = itemView.findViewById(R.id.tv_message);
            cardItemLayout = itemView.findViewById(R.id.card_message);
        }
    }
}
