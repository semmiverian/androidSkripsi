package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Fragment.CareerListFragment;
import com.skripsi.semmi.restget3.R;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;

/**
 * Created by semmi on 01/11/2015.
 */
public class CareerActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mFloatingActionButton;
    public static String refresh_code="1";
    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences sharedPreferences;
    private String currentSession;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career);
        mFloatingActionButton= (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Job List");





        displayInitialFragment();
        Bundle bundle = new Bundle();
        bundle.putString("edttext", "From Activity");

        CareerListFragment fragobj = new CareerListFragment();
        fragobj.setArguments(bundle);
        sharedPreferences = this.getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        currentSession = sharedPreferences.getString("statusSession", "Session");


        if(!currentSession.equals("mahasiswa")){
            showIntro(mFloatingActionButton,"fabIntro22","You can Add new Career by touch this button");
        }
        

    }



    private void displayInitialFragment() {
//        Default Fragment when user open the App
//        getSupportFragmentManager().beginTransaction().replace(R.id.container, CareerListFragment.getInstance()).commit();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        CareerListFragment recycle = CareerListFragment.getInstance();
        ft.replace(R.id.container, recycle);
        ft.commit();
    }

    // bikin aksi di action bar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_career, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch(item.getItemId()){
           case R.id.action_search:
               Intent searchCareerIntent = new Intent(this, SearchCareerActivity.class);
               startActivity(searchCareerIntent);
               break;
       }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:

                if(currentSession.equals("mahasiswa")){
                    MaterialDialog dialog2 = new MaterialDialog.Builder(this)
                            .title("Not Allowed")
                            .content("Mahasiswa not allowed to add new Career")
                            .positiveText("Ok")
                            .positiveColorRes(R.color.teal_400)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                                    materialDialog.dismiss();
                                }
                            })
                            .show();
                    return;
                }
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


    private void showIntro(View view,String ID,String content){
        new MaterialIntroView.Builder(this)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText(content)
                .setTarget(view)
                .setUsageId(ID) //THIS SHOULD BE UNIQUE ID
                .show();
    }


}
