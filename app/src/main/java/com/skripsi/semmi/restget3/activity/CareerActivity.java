package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

import com.skripsi.semmi.restget3.Fragment.CareerListFragment;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 01/11/2015.
 */
public class CareerActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mFloatingActionButton;
    public static String refresh_code="1";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career);
        mFloatingActionButton= (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);
        displayInitialFragment();
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");

        CareerListFragment fragobj = new CareerListFragment();
        fragobj.setArguments(bundle);
    }



    private void displayInitialFragment() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container, CareerListFragment.getInstance()).commit();
    }

    // bikin aksi di action bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_career,menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent intent = new Intent(this, AddNewCareerActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(this,home_activity.class);
        startActivity(backIntent);
    }


}
