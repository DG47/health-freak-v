package com.evineon.healthfreakv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    //HomeFragment homeFragment;

    Toolbar toolbar;
    RelativeLayout fragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = findViewById(R.id.fragment_container);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        //homeFragment = new HomeFragment();
        //transaction.add(R.id.fragment_container, homeFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.cart:
                Intent i = new Intent(MainActivity.this, CartActivity.class);
                startActivity(i);
                break;
            case R.id.profile:
                Intent accountActivity = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(accountActivity);
                break;
        }
        return true;
    }*/

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            getSupportFragmentManager().popBackStack();
            super.onBackPressed();
        }
    }
}

