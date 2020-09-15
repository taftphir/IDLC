package com.alugara.idlc.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.ArtikelAdapter;
import com.alugara.idlc.adapters.InfografisAdapter;
import com.alugara.idlc.adapters.KamusAdapter;
import com.alugara.idlc.adapters.SeminarAdapter;
import com.alugara.idlc.adapters.VideoAdapter;
import com.alugara.idlc.models.Artikel;
import com.alugara.idlc.models.Kategori;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Seminar;
import com.alugara.idlc.models.Utils;
import com.alugara.idlc.models.Video;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class ArticleActivity extends AppCompatActivity {

    private RecyclerView rvArtikel;
    private TextView noArtikelTv;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;
    private Utils utils = new Utils();
    private Kategori kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        rvArtikel = findViewById(R.id.rv_artikel);
        noArtikelTv = findViewById(R.id.tv_artikel_no_content);
        progressBar = findViewById(R.id.progress_bar_artikel);

        kategori = getIntent().getParcelableExtra("category");

        if (kategori != null){
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(kategori.getName());

            mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

            switch (kategori.getName()){
                case "ARTIKEL":
                    mainViewModel.setListArtikel(this, kategori.getId());
                    mainViewModel.getListArtikel().observe(this, getArtikel);
                    break;
                case "INFOGRAFIS":
                    mainViewModel.setListArtikel(this, kategori.getId());
                    mainViewModel.getListArtikel().observe(this, getInfografis);
                    break;
                case "KAMUS HUKUM":
                case "TIPS HUKUM PRAKTIS":
                    mainViewModel.setListArtikel(this, kategori.getId());
                    mainViewModel.getListArtikel().observe(this, getKamus);
                    break;
                case "VIDEO":
                    mainViewModel.setListVideos(this);
                    mainViewModel.getListVideos().observe(this, getVideo);
                    break;
                default:
                    utils.setVisible(noArtikelTv);
                    utils.setGone(progressBar);
                    break;

            }
        }else{
            utils.setVisible(noArtikelTv);
            utils.setGone(progressBar);
        }


    }

    private Observer<ArrayList<Artikel>> getArtikel = new Observer<ArrayList<Artikel>>() {
        @Override
        public void onChanged( ArrayList<Artikel> artikels) {
            if (artikels.size() > 0) {
                ArtikelAdapter adapter = new ArtikelAdapter(artikels, ArticleActivity.this);
                rvArtikel.setAdapter(adapter);
                rvArtikel.setLayoutManager(new LinearLayoutManager(ArticleActivity.this));
                adapter.notifyDataSetChanged();
                utils.setGone(progressBar);
            }else {
                utils.setVisible(noArtikelTv);
                utils.setGone(progressBar);
            }

        }
    };

    private Observer<ArrayList<Artikel>> getInfografis = new Observer<ArrayList<Artikel>>() {
        @Override
        public void onChanged( ArrayList<Artikel> artikels) {
            if (artikels.size() > 0) {
                InfografisAdapter adapter = new InfografisAdapter(artikels, ArticleActivity.this);
                rvArtikel.setAdapter(adapter);
                rvArtikel.setLayoutManager(new LinearLayoutManager(ArticleActivity.this));
                adapter.notifyDataSetChanged();
                utils.setGone(progressBar);
            }else {
                utils.setVisible(noArtikelTv);
                utils.setGone(progressBar);
            }

        }
    };

    private Observer<ArrayList<Artikel>> getKamus = new Observer<ArrayList<Artikel>>() {
        @Override
        public void onChanged( ArrayList<Artikel> artikels) {
            if (artikels.size() > 0) {
                KamusAdapter adapter = new KamusAdapter(artikels, ArticleActivity.this);
                rvArtikel.setAdapter(adapter);
                rvArtikel.setLayoutManager(new LinearLayoutManager(ArticleActivity.this));
                adapter.notifyDataSetChanged();
                utils.setGone(progressBar);
            }else {
                utils.setVisible(noArtikelTv);
                utils.setGone(progressBar);
            }

        }
    };

    private Observer<ArrayList<Video>> getVideo = new Observer<ArrayList<Video>>() {
        @Override
        public void onChanged( ArrayList<Video> videos) {
            if (videos.size() > 0) {
                Log.d("size_video", " : "+videos.size());
                VideoAdapter adapter = new VideoAdapter(videos, ArticleActivity.this);
                rvArtikel.setAdapter(adapter);
                rvArtikel.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                adapter.notifyDataSetChanged();
                utils.setGone(progressBar);
            }else {
                utils.setVisible(noArtikelTv);
                utils.setGone(progressBar);
            }

        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
