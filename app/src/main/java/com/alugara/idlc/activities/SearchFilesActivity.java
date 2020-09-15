package com.alugara.idlc.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.FileAdapter;
import com.alugara.idlc.models.Files;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Utils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class SearchFilesActivity extends AppCompatActivity {

    private MaterialSearchView searchView;
    private RecyclerView rvSearchFiles;
    private MainViewModel mainViewModel;
    private ProgressBar progressBar;
    private TextView noContentTv;
    private Utils utils = new Utils();

    //TODO : Optimize the search files algorithm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_files);
        searchView = findViewById(R.id.search_view);
        rvSearchFiles = findViewById(R.id.rv_search_files);
        progressBar = findViewById(R.id.progress_bar_search);
        noContentTv = findViewById(R.id.search_no_content_tv);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_search));
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String replacedText = "";
                if (query.contains(" ")) {
                    replacedText = query.replace(" ", "%20");
                }else {
                    replacedText = query;
                }

                utils.setVisible(progressBar);

                mainViewModel.setListSearchFiles(getApplicationContext(), replacedText, progressBar);
                mainViewModel.getListSearchFiles().observe(SearchFilesActivity.this, getSearchFiles);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(SearchFilesActivity.this, MainMenuActivity.class));
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.showSearch(true);
        return true;
    }

    private Observer<ArrayList<Files>> getSearchFiles = new Observer<ArrayList<Files>>() {
        @Override
        public void onChanged(ArrayList<Files> files) {
            if (files.size() > 0) {
                utils.setInvisible(noContentTv);
                FileAdapter fileAdapter = new FileAdapter(files, SearchFilesActivity.this);
                rvSearchFiles.setAdapter(fileAdapter);
                rvSearchFiles.setLayoutManager(new LinearLayoutManager(SearchFilesActivity.this));
                fileAdapter.notifyDataSetChanged();
            }else{
                utils.setVisible(noContentTv);
                utils.setInvisible(progressBar);
            }
        }
    };
}
