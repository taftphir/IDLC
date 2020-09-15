package com.alugara.idlc.activities;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.BuildConfig;
import com.alugara.idlc.R;
import com.alugara.idlc.models.Seminar;
import com.alugara.idlc.models.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DetailSeminarActivity extends AppCompatActivity {

    private Seminar seminar;
    private TextView hariTv, tanggalTv, tempatTv, biayaTv, noImageTv;
    private ImageView posterIv;
    private Button downloadBtn;
    private Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_seminar);
        Toolbar toolbar = findViewById(R.id.toolbar_seminar);
        setSupportActionBar(toolbar);
        seminar = getIntent().getParcelableExtra("seminar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String mark = getResources().getString(R.string.mark_);
        if (seminar != null)
            getSupportActionBar().setTitle(seminar.getNama());

        hariTv = findViewById(R.id.seminar_day_tv);
        tanggalTv = findViewById(R.id.seminar_date_tv);
        tempatTv = findViewById(R.id.seminar_place_tv);
        biayaTv = findViewById(R.id.seminar_cost_tv);
        posterIv = findViewById(R.id.poster_seminar_iv);
        downloadBtn = findViewById(R.id.download_file_btn);
        noImageTv = findViewById(R.id.no_image_load);

        Glide.with(this).load(BuildConfig.BASE_IMAGE_URL+seminar.getPoster())
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
                }).into(posterIv);

        hariTv.setText(mark+seminar.getHari());
        tanggalTv.setText(mark+seminar.getTanggal());
        tempatTv.setText(mark+seminar.getTempat());
        biayaTv.setText(mark+seminar.getBiaya());

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailSeminarActivity.this, "Download file...", Toast.LENGTH_SHORT).show();
            }
        });
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
