package com.skripsi.semmi.restget3;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Fragment.LoginFragment;
import com.skripsi.semmi.restget3.Fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayInitialFragment();
    }
    private void displayInitialFragment() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container, LoginFragment.getInstance()).commit();
    }

}
