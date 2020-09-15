package com.alugara.idlc.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.CategoryActivity;
import com.alugara.idlc.adapters.BookAdapter;
import com.alugara.idlc.adapters.FileAdapter;
import com.alugara.idlc.adapters.MenuAdapter;
import com.alugara.idlc.models.Files;
import com.alugara.idlc.models.Kategori;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private RecyclerView listHukum;
    private boolean sub;
    private String parent, name;
    MainViewModel mainViewModel;
    private ProgressBar progressBar;
    private TextView noContentTv;
    private Utils utils = new Utils();

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            sub = bundle.getBoolean("sub");
            parent = bundle.getString("parent");
            name = bundle.getString("name");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        listHukum = view.findViewById(R.id.rv_category);
        progressBar = view.findViewById(R.id.progress_bar_menu);
        noContentTv = view.findViewById(R.id.menu_no_content_txt);
        utils.setVisible(progressBar);

        ((CategoryActivity) getActivity())
                .getSupportActionBar().setTitle(name);
        if (!sub) {
            mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            mainViewModel.setListFiles(getContext(), parent, progressBar);
            mainViewModel.getListFiles().observe(this, getFiles);
        } else {
            mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            mainViewModel.setDataSub(getContext(), parent, progressBar);
            mainViewModel.getListSubKategori().observe(this, getSub);

        }

        return view;
    }

    private Observer<ArrayList<Kategori>> getSub = new Observer<ArrayList<Kategori>>() {
        @Override
        public void onChanged(ArrayList<Kategori> subs) {
            if (subs.size() > 0) {
                MenuAdapter menuAdapter = new MenuAdapter(subs, getContext());
                listHukum.setAdapter(menuAdapter);
                listHukum.setLayoutManager(new GridLayoutManager(getContext(), 3));
                menuAdapter.notifyDataSetChanged();
            } else {
                utils.setVisible(noContentTv);
            }
        }
    };

    private Observer<ArrayList<Files>> getFiles = new Observer<ArrayList<Files>>() {
        @Override
        public void onChanged(ArrayList<Files> files) {
            if (files.size() > 0) {
                if (parent.equals("12")){
                    BookAdapter fileAdapter = new BookAdapter(files, getContext());
                    listHukum.setAdapter(fileAdapter);
                    listHukum.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    fileAdapter.notifyDataSetChanged();
                }else {
                    FileAdapter fileAdapter = new FileAdapter(files, getContext());
                    listHukum.setAdapter(fileAdapter);
                    listHukum.setLayoutManager(new LinearLayoutManager(getContext()));
                    fileAdapter.notifyDataSetChanged();
                }

            } else {
                utils.setVisible(noContentTv);
            }

        }
    };

}
