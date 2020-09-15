package com.alugara.idlc.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.alugara.idlc.R;
import com.alugara.idlc.fragments.AdminListFragment;
import com.alugara.idlc.fragments.HomeFragment;
import com.alugara.idlc.fragments.ProductsFragment;
import com.alugara.idlc.services.ChatService;

public class MainMenuActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        loadFragment(new HomeFragment());
        bottomNavigation = findViewById(R.id.bottom_nav_menu);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        Intent intent = new Intent(this, ChatService.class);
        intent.putExtra("foo", "extra");
        startService(intent);

    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.home_menu:
                fragment = new HomeFragment();
                break;

            case R.id.product_menu:
                fragment = new ProductsFragment();
                break;

            case R.id.forum_menu:
                //startActivity(new Intent(MainMenuActivity.this, MainForumActivity.class));
                Toast.makeText(this, "This feature is under Maintenance", Toast.LENGTH_SHORT).show();
                break;

            case R.id.chat_menu:
                fragment = new AdminListFragment();
                break;

            case R.id.account_menu:
                Toast.makeText(this, "This feature is under Maintenance", Toast.LENGTH_SHORT).show();
                break;
        }

        return loadFragment(fragment);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.double_back_txt), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
