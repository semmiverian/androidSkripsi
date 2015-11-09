package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.skripsi.semmi.restget3.Fragment.AllUserFragment;
import com.skripsi.semmi.restget3.Fragment.CareerListFragment;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 09/11/2015.
 */
public class AllUserActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        displayInitialFragment();
    }
    private void displayInitialFragment() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container, AllUserFragment.getInstance()).commit();
    }
}
