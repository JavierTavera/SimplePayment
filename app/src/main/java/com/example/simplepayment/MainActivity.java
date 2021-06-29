package com.example.simplepayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
        bottomNavigationView.setSelectedItemId(R.id.fragmentHome);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Switch is going to be deprecated in these cases !! That is why I'm using if else
        int idOfItem = item.getItemId();

        if (idOfItem == R.id.fragmentHome){
            FragmentHome fragmentHome1 = new FragmentHome();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragmentHome1).commit();
            return true;
        } else if(idOfItem == R.id.fragmentDetails){
            FragmentDetails fragmentDetails1 = new FragmentDetails();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragmentDetails1).commit();
            return true;
        } else if (idOfItem == R.id.fragmentProfile){
            FragmentProfile fragmentProfile1 = new FragmentProfile();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragmentProfile1).commit();
            return true;
        }

        return false;
    }



}