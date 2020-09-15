package com.alugara.idlc.activities;

import android.graphics.Typeface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alugara.idlc.R;
import com.alugara.idlc.fragments.LoginFragment;
import com.alugara.idlc.fragments.RegisterFragment;

public class AuthActivity extends AppCompatActivity {

    String tipeUser;
    Button loginBtn, masukBtn;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loginBtn = findViewById(R.id.btn_login);
        masukBtn = findViewById(R.id.btn_register);
        masukBtn.setTypeface(null, Typeface.BOLD);

        tipeUser = getIntent().getStringExtra("tipe_user");
        String status = getIntent().getStringExtra("status");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (status.equals("login")){
            LoginFragment loginFragment = new LoginFragment();
            fragmentTransaction.replace(R.id.frame_auth_container, loginFragment);
            fragmentTransaction.commit();
        }else{
            RegisterFragment registerFragment = new RegisterFragment();
            Bundle bundle = new Bundle();
            bundle.putString("tipe_user", tipeUser);
            registerFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.frame_auth_container, registerFragment);
            fragmentTransaction.commit();
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setTypeface(null, Typeface.BOLD);
                masukBtn.setTypeface(null, Typeface.NORMAL);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.replace(R.id.frame_auth_container, loginFragment);
                fragmentTransaction.commit();
            }
        });

        masukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masukBtn.setTypeface(null, Typeface.BOLD);
                loginBtn.setTypeface(null, Typeface.NORMAL);
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                RegisterFragment registerFragment = new RegisterFragment();
                Bundle bundle = new Bundle();
                bundle.putString("tipe_user", tipeUser);
                registerFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_auth_container, registerFragment);
                fragmentTransaction.commit();
            }
        });
    }
}
