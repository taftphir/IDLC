package com.alugara.idlc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.ThreadAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeForumFragment extends Fragment {

    RecyclerView rvHotThread;
    ThreadAdapter adapter;
    ArrayList<String> dummyData = new ArrayList<>();

    public HomeForumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dummyData.add("Menjadi bucin apa hukumnya?");
        dummyData.add("Hukum mengambil buff dan feed");
        dummyData.add("Undang-undang mengenai tank lari saat war");
        dummyData.add("Hukuman apakah yang pantas bagi seorang feeder?");

        View view = inflater.inflate(R.layout.fragment_home_forum, container, false);
        rvHotThread = view.findViewById(R.id.rv_hot_thread);
        adapter = new ThreadAdapter(dummyData,getContext());
        rvHotThread.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHotThread.setAdapter(adapter);
        return view;
    }

}
