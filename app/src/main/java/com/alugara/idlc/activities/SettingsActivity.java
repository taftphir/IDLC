package com.alugara.idlc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import com.alugara.idlc.R;
import com.alugara.idlc.preferences.Preferences;
import com.alugara.idlc.services.ChatService;

public class SettingsActivity extends AppCompatActivity {

    Switch chatSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        chatSwitch = findViewById(R.id.switchChat);

        boolean chatStatus = Preferences.getKeyChat(this);

        chatSwitch.setChecked(chatStatus);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ChatService.class);
        intent.putExtra("foo", "extra");
        if (chatSwitch.isChecked())
            startService(intent);
        else
            stopService(intent);

        Preferences.setKeyChat(this, chatSwitch.isChecked());
        startActivity(new Intent(SettingsActivity.this, MainMenuActivity.class));
        finish();

    }
}
