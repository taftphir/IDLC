package com.alugara.idlc.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.CommentAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreadDetailFragment extends Fragment {

    RecyclerView rvComments;
    ArrayList<String> dummyData = new ArrayList<>();
    CommentAdapter adapter;

    public ThreadDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dummyData.add("Upin");
        dummyData.add("Ipin");
        dummyData.add("Ultraman ribut");
        dummyData.add("Ranger merah");

        View view = inflater.inflate(R.layout.fragment_thread_detail, container, false);
        rvComments = view.findViewById(R.id.rv_comments);
        adapter = new CommentAdapter(dummyData, getContext());
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
        rvComments.setAdapter(adapter);

        return view;
    }

}
