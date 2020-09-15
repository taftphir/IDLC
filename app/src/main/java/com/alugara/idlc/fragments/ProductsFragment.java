package com.alugara.idlc.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.ProductAdapter;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Paket;
import com.alugara.idlc.models.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment {

    RecyclerView rvProductList;
    ArrayList<String> dummyData = new ArrayList<>();
    MainViewModel mainViewModel;
    ProgressBar progressBar;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_products, container, false);
        progressBar = view.findViewById(R.id.paket_progress_bar);
        rvProductList = view.findViewById(R.id.rv_product_list);
        Utils utils = new Utils();
        utils.setVisible(progressBar);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setListPaket(getContext(),progressBar);
        mainViewModel.getListPaket().observe(this, getPaket);


        return view;
    }

    private Observer<ArrayList<Paket>> getPaket = new Observer<ArrayList<Paket>>() {
        @Override
        public void onChanged(ArrayList<Paket> pakets) {

            ProductAdapter adapter = new ProductAdapter(pakets, getContext());
            rvProductList.setAdapter(adapter);
            rvProductList.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.notifyDataSetChanged();
        }
    };

}
