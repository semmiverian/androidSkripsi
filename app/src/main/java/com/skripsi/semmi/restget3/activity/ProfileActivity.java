package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.skripsi.semmi.restget3.Fragment.LoginFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileFragment;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 21/10/2015.
 */
public class ProfileActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        displayInitialFragment();
    }

    private void displayInitialFragment() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileFragment.getInstance()).commit();
    }
}
