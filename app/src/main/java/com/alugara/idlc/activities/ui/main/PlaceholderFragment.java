package com.alugara.idlc.activities.ui.main;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.MenuAdapter;
import com.alugara.idlc.adapters.SeminarAdapter;
import com.alugara.idlc.models.Kategori;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Seminar;
import com.alugara.idlc.models.Utils;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private MainViewModel pageViewModel;
    private SeminarAdapter adapter;
    private RecyclerView rvSeminar;
    private TextView noContentTv;
    private ProgressBar progressBar;
    Utils utils = new Utils();

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        int index = 1;
        String active = "";
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            if (index == 1){
                pageViewModel.setListSeminarOn(getContext());
                pageViewModel.getListSeminarOn().observe(this, getSeminar);
            }else{
                pageViewModel.setListSeminarOff(getContext());
                pageViewModel.getListSeminarOff().observe(this, getSeminar);
            }
        }

    }

    private Observer<ArrayList<Seminar>> getSeminar = new Observer<ArrayList<Seminar>>() {
        @Override
        public void onChanged( ArrayList<Seminar> seminars) {
            if (seminars.size() > 0) {
                adapter = new SeminarAdapter(seminars, getContext());
                rvSeminar.setAdapter(adapter);
                rvSeminar.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter.notifyDataSetChanged();
                utils.setGone(progressBar);
            }else {
                utils.setVisible(noContentTv);
                utils.setGone(progressBar);
            }

        }
    };

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_seminar, container, false);
        rvSeminar = root.findViewById(R.id.rv_seminar);
        progressBar = root.findViewById(R.id.progress_bar_seminar);
        noContentTv = root.findViewById(R.id.tv_seminar_no_content);
        utils.setVisible(progressBar);

        return root;
    }
}