package com.alugara.idlc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alugara.idlc.R;
import com.alugara.idlc.preferences.Preferences;

public class LaunchActivity extends AppCompatActivity {

    Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        if (Preferences.getKeyLogin(this)){
            startActivity(new Intent(LaunchActivity.this, MainMenuActivity.class));
            finish();
        }
        loginBtn = findViewById(R.id.launch_log_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, AuthActivity.class);
                intent.putExtra("status","login");
                startActivity(intent);
            }
        });
        registerBtn = findViewById(R.id.launch_reg_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, AuthActivity.class);
                intent.putExtra("status","register");
                startActivity(intent);
            }
        });
    }
}
