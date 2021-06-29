package com.example.simplepayment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // By using switch we can easily get
            // the selected fragment
            // by using there id.
            //Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    //selectedFragment = new AlgorithmFragment();
                    break;
                case R.id.details:
                    //selectedFragment = new CourseFragment();
                    break;
                case R.id.profile:
                    //selectedFragment = new ProfileFragment();
                    break;
            }
            // It will help to replace the
            // one fragment to other.

            return true;
        }
    };

}