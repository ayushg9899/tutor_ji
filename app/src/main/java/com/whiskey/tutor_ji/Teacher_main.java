package com.whiskey.tutor_ji;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Teacher_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);


        BottomNavigationView bottomNav = findViewById(R.id.bottomnavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                    new Teacher_dash()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.explore:
                            selectedFragment = new Teacher_dash();
                            break;
                        case R.id.courses:
                            selectedFragment = new teacher_frag2();
                            break;
                        case R.id.favorites:
                            selectedFragment = new teacher_frag3();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                            selectedFragment).commit();

                    return true;
                }
            };
}