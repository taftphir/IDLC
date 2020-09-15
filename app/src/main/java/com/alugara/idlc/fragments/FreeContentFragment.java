package com.alugara.idlc.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.AuthActivity;
import com.alugara.idlc.adapters.MenuAdapter;
import com.alugara.idlc.models.Kategori;
import com.alugara.idlc.models.MainViewModel;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreeContentFragment extends Fragment {

    private TextView authTv;
    private RecyclerView rvFreeContent;
    private ProgressBar progressBar;
    MenuAdapter adapter;
    MainViewModel mainViewModel;
    CarouselView carouselView;

    int[] sampleImages = {R.drawable.satu, R.drawable.dua, R.drawable.tiga, R.drawable.empat};

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    public FreeContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_free_content, container, false);
        authTv = view.findViewById(R.id.loginTv);
        rvFreeContent = view.findViewById(R.id.rv_menu_free);
        progressBar = view.findViewById(R.id.progress_bar_free);
        carouselView = view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "Gambar ke-"+(position+1)+" dipilih", Toast.LENGTH_SHORT).show();
            }
        });
        authTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AuthActivity.class));
            }
        });

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setDataKategori(getContext(), progressBar);
        mainViewModel.getlistKategori().observe(this, getKategori);

        return view;
    }

    private Observer<ArrayList<Kategori>> getKategori = new Observer<ArrayList<Kategori>>() {
        @Override
        public void onChanged( ArrayList<Kategori> kategoris) {
            adapter = new MenuAdapter(kategoris, getContext());
            rvFreeContent.setAdapter(adapter);
            rvFreeContent.setLayoutManager(new GridLayoutManager(getContext(), 3));
            adapter.notifyDataSetChanged();

        }
    };


}
