package com.alugara.idlc.activities;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.alugara.idlc.R;
import com.alugara.idlc.models.Artikel;
import com.alugara.idlc.models.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DetailArtikelActivity extends AppCompatActivity {

    private Artikel artikel;
    private TextView titleTv, creatorTv, contentTv, noImageTv;
    private ImageView fotoIv;
    private Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);
        Toolbar toolbar = findViewById(R.id.toolbar_artikel);
        setSupportActionBar(toolbar);
        artikel = getIntent().getParcelableExtra("artikel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String mark = getResources().getString(R.string.mark_);
        if (artikel != null)
            getSupportActionBar().setTitle("Baca Artikel");

        titleTv = findViewById(R.id.title_artikel_tv);
        creatorTv = findViewById(R.id.artikel_creator_tv);
        contentTv = findViewById(R.id.artikel_isi_tv);
        fotoIv = findViewById(R.id.poster_artikel_iv);
        noImageTv = findViewById(R.id.no_image_artikel_load);

        Glide.with(this).load(artikel.getFoto())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        utils.setVisible(noImageTv);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(fotoIv);

        titleTv.setText(artikel.getJudul());
        contentTv.setText(artikel.getIsi());
        creatorTv.setText(mark+artikel.getNama());

    }

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
