package com.alugara.idlc.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.adapters.MenuAdapter;
import com.alugara.idlc.models.Files;
import com.alugara.idlc.models.Kategori;
import com.alugara.idlc.models.MainViewModel;
import com.alugara.idlc.models.Utils;
import com.alugara.idlc.preferences.Preferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.alugara.idlc.BuildConfig.BASE_URL;

public class VerificationActivity extends AppCompatActivity {

    private Button resendBtn;
    private EditText emailEd;
    private TextView validEmailTv;
    private Utils utils = new Utils();
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        Toolbar toolbar = findViewById(R.id.toolbar);
        resendBtn = findViewById(R.id.btn_resend);
        emailEd = findViewById(R.id.verif_emailTv);
        validEmailTv = findViewById(R.id.tv_val_verif_email);
        id = Preferences.getKeyId(VerificationActivity.this);

        emailEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean validEmail = utils.isEmailValid(s.toString());
                if (s.toString().isEmpty()) {
                    validEmailTv.setText(getResources().getString(R.string.must_not_blank));
                    utils.setVisible(validEmailTv);
                } else if (!validEmail) {
                    validEmailTv.setText(getResources().getString(R.string.not_valid_email));
                    utils.setVisible(validEmailTv);
                } else if (validEmail) {
                    utils.setGone(validEmailTv);
                }
            }
        });
        emailEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    utils.setGone(validEmailTv);
                }
            }
        });
        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VerificationActivity.this, getResources().getString(R.string.verif_link_sent_txt)+Preferences.getKeyEmail(VerificationActivity.this), Toast.LENGTH_SHORT).show();

            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.verif_activity_name_txt));
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
