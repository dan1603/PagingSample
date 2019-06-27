package com.kalashnyk.denys.pagingsample.presentation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.kalashnyk.denys.pagingsample.R;
import com.kalashnyk.denys.pagingsample.presentation.fragments.UsersBugFragment;
import com.kalashnyk.denys.pagingsample.presentation.fragments.UsersListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

//            UsersListFragment usersListFragment = new UsersListFragment();
//            usersListFragment.setArguments(getIntent().getExtras());

            UsersBugFragment fragment = new UsersBugFragment();
            fragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();

            getSupportFragmentManager().addOnBackStackChangedListener(() -> shouldDisplayHomeUp());
        }
    }

    public void shouldDisplayHomeUp(){
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }
}

