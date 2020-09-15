package com.alugara.idlc.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.alugara.idlc.R;
import com.alugara.idlc.fragments.DetailDocsFragment;
import com.alugara.idlc.fragments.MenuFragment;
import com.alugara.idlc.models.Files;
import com.alugara.idlc.models.Kategori;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.alugara.idlc.BuildConfig.BASE_URL;

public class CategoryActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    private String parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getParcelableExtra("file") != null){
            Files file = getIntent().getParcelableExtra("file");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(file.getName());

            Bundle bundle = new Bundle();
            bundle.putParcelable("selected_files", file);
            DetailDocsFragment detailFragment = new DetailDocsFragment();
            detailFragment.setArguments(bundle);
            FragmentTransaction fragment = getSupportFragmentManager().beginTransaction();
            fragment.replace(R.id.category_frame_container, detailFragment);
            fragment.commit();
        }else{
            parent = getIntent().getStringExtra("parent");
            final String name = getIntent().getStringExtra("name");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(name);


            final String mainUrl = BASE_URL+"kategori/sub?parent="+parent;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    mainUrl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArray = response.getJSONArray("sub_kategori");

                                if (jsonArray.length() > 0){
                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    MenuFragment menuFragment = new MenuFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("parent", parent);
                                    bundle.putString("name",name);
                                    bundle.putBoolean("sub", true);
                                    menuFragment.setArguments(bundle);
                                    fragmentTransaction.replace(R.id.category_frame_container, menuFragment);
                                    fragmentTransaction.commit();
                                }else{
                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    MenuFragment menuFragment = new MenuFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("parent", parent);
                                    bundle.putString("name",name);
                                    bundle.putBoolean("sub", false);
                                    menuFragment.setArguments(bundle);
                                    fragmentTransaction.replace(R.id.category_frame_container, menuFragment);
                                    fragmentTransaction.commit();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonObjectRequest);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }
        else{
            super.onBackPressed();
        }
    }
}
