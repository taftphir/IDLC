package com.alugara.idlc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.ChatActivity;
import com.alugara.idlc.models.Admin;
import com.alugara.idlc.models.Utils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

    private ArrayList<Admin> dummyData;
    private Context context;
    private Utils utils = new Utils();

    public AdminAdapter(ArrayList<Admin> dummyData, Context context) {
        this.dummyData = dummyData;
        this.context = context;
    }

    public void clearList() {
        dummyData.clear();
    }

    @Override
    public AdminAdapter.AdminViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_admin, viewGroup, false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdminAdapter.AdminViewHolder holder, final int i) {
        holder.namaItemTv.setText(dummyData.get(i).getName());
        if (dummyData.get(i).getOnline().equals("1")) {
            utils.setVisible(holder.onlineIv);
        } else {
            utils.setGone(holder.onlineIv);
        }
        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Admin admin = dummyData.get(i);

                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("selected_admin", admin);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dummyData.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView namaItemTv;
        CircleImageView onlineIv;
        View cardItem;

        public AdminViewHolder(View itemView) {
            super(itemView);
            namaItemTv = itemView.findViewById(R.id.admin_name_tv);
            cardItem = itemView.findViewById(R.id.layout_item_admin);
            onlineIv = itemView.findViewById(R.id.admin_online_iv);
        }
    }
}
