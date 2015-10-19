package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.skripsi.semmi.restget3.Fragment.AroundMeFragment;
import com.skripsi.semmi.restget3.Fragment.ForgotPassFragment;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 19/10/2015.
 */
public class AroundMeActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_me);
        displayInitialFragment();
    }
    private void displayInitialFragment() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container,AroundMeFragment.getInstance()).commit();
    }
}
