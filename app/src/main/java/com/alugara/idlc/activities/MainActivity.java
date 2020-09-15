package com.alugara.idlc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.alugara.idlc.R;
import com.alugara.idlc.fragments.AdminListFragment;
import com.alugara.idlc.fragments.FreeContentFragment;
import com.alugara.idlc.fragments.ProductsFragment;
import com.alugara.idlc.preferences.Preferences;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigation;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu = findViewById(R.id.bottom_nav_menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new FreeContentFragment());

        bottomNavigation = findViewById(R.id.bottom_nav_menu);
        bottomNavigation.setOnNavigationItemSelectedListener(this);

        if (Preferences.getKeyLogin(this)){
            startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
            finish();
        }

    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_main_container, fragment)
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
                fragment = new FreeContentFragment();
                break;

            case R.id.product_menu:
                fragment = new ProductsFragment();
                break;

            case R.id.forum_menu:
                startActivity(new Intent(MainActivity.this, MainForumActivity.class));
                break;

            case R.id.account_menu:
                fragment = new AdminListFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
