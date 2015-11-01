package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.skripsi.semmi.restget3.Fragment.CareerListFragment;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 01/11/2015.
 */
public class CareerActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career);
        displayInitialFragment();
    }
    private void displayInitialFragment() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container, CareerListFragment.getInstance()).commit();
    }

}
