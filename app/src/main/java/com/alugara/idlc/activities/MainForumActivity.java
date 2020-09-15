package com.alugara.idlc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.fragments.HomeForumFragment;
public class MainForumActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigation;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
            getSupportActionBar().setTitle("IDLC FORUM");
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_forum);
        Toolbar toolbar = findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("IDLC FORUM");

        loadFragment(new HomeForumFragment());

        bottomNavigation = findViewById(R.id.bottom_nav_forum_menu);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_main_forum_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.forum_home_menu:
                fragment = new HomeForumFragment();
                break;

            case R.id.forum_write_menu:
                Toast.makeText(this, "To write thread fragment", Toast.LENGTH_SHORT).show();
                break;

            case R.id.forum_notifikasi_menu:
                Toast.makeText(this, "To notification fragment", Toast.LENGTH_SHORT).show();
                break;

            case R.id.forum_account_menu:
                Toast.makeText(this, "To account fragment", Toast.LENGTH_SHORT).show();
                break;
        }

        return loadFragment(fragment);
    }
}
