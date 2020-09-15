package com.alugara.idlc.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.AdminAdapter;
import com.alugara.idlc.models.Admin;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Utils;

import java.util.ArrayList;

public class AdminListFragment extends Fragment {

    private View layoutLoad;
    private Utils utils = new Utils();
    private MainViewModel mainViewModel;
    private RecyclerView rvAdmins;
    private AdminAdapter adapter;

    public AdminListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_list, container, false);
        rvAdmins = view.findViewById(R.id.rv_admin_list);
        layoutLoad = view.findViewById(R.id.layout_load_admin);
        Toolbar toolbar = view.findViewById(R.id.toolbar_admin_list);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("LIST ADMIN");
        utils.setVisible(layoutLoad);

        if (adapter != null)
            adapter.clearList();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setListAdmin(getContext());
        mainViewModel.getListAdmin().observe(this, getAdmins);
        return view;
    }

    private Observer<ArrayList<Admin>> getAdmins = new Observer<ArrayList<Admin>>() {
        @Override
        public void onChanged(ArrayList<Admin> admins) {
            adapter = new AdminAdapter(admins, getContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rvAdmins.setLayoutManager(linearLayoutManager);
            rvAdmins.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            utils.setGone(layoutLoad);

        }
    };
}
