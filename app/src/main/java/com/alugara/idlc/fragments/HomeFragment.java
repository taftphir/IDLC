package com.alugara.idlc.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.activities.LaunchActivity;
import com.alugara.idlc.activities.MainMenuActivity;
import com.alugara.idlc.activities.SearchFilesActivity;
import com.alugara.idlc.activities.VerificationActivity;
import com.alugara.idlc.adapters.MenuAdapter;
import com.alugara.idlc.models.Kategori;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Utils;
import com.alugara.idlc.preferences.Preferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.alugara.idlc.BuildConfig.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView rvMenuList;
    MenuAdapter adapter;
    MainViewModel mainViewModel;
    TextView logoutTv, noContentTv;
    EditText searchEt;
    ProgressBar progressBar;
    Utils utils = new Utils();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvMenuList = view.findViewById(R.id.rv_menu);
        logoutTv = view.findViewById(R.id.logoutTv);
        searchEt = view.findViewById(R.id.et_search);
        progressBar = view.findViewById(R.id.progress_bar_main_menu);
        noContentTv = view.findViewById(R.id.home_no_content_txt);

        searchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchFilesActivity.class));
            }
        });

        logoutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogoutMethod(Preferences.getKeyId(getContext()));
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
            if (kategoris.size() > 0) {
                adapter = new MenuAdapter(kategoris, getContext());
                rvMenuList.setAdapter(adapter);
                rvMenuList.setLayoutManager(new GridLayoutManager(getContext(), 3));
                adapter.notifyDataSetChanged();
            }else {
                utils.setVisible(noContentTv);
            }

        }
    };

    private void onLogoutMethod(final String id){
        String url = BASE_URL+"auth/logout";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        try {
                            JSONObject usersObj = new JSONObject(response);
                            JSONArray users = usersObj.getJSONArray("users");
                            if (users.length() > 0){
                                JSONObject user = users.getJSONObject(0);
                                Preferences.setKeyLogin(getContext(), false);
                                Preferences.setKeyEmail(getContext(), "");
                                Preferences.setKeyId(getContext(), "");
                                startActivity(new Intent(getContext(), LaunchActivity.class));
                                getActivity().finish();

                            }else{
                                Toast.makeText(getContext(), "id not valid", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getContext(), "Failed to update, check your internet", Toast.LENGTH_SHORT).show();
                        Log.d("Error.Response", error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("id", id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(postRequest);
    }

}
