package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.MainActivity;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 15/10/2015.
 */
public class home_activity extends AppCompatActivity implements View.OnClickListener {
    public static final String  username="username";
    public static final String  status="status";
    private TextView mUserLogin;
    private TextView mStatusLogin;
    private Button mAroundMe;
    private Button mCareer;
    private Button mDummy;
    private String usernameExtra;
    private String statusExtra;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mUserLogin= (TextView) findViewById(R.id.userLogin);
        mStatusLogin= (TextView) findViewById(R.id.statusLogin);
        // validasi dan ngambil data dari aktivitas login
        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(username)){
                mUserLogin.setText(getIntent().getExtras().getString(username));
                usernameExtra=mUserLogin.getText().toString();
                mStatusLogin.setText(getIntent().getExtras().getString(status));
            }
        }
        mAroundMe= (Button) findViewById(R.id.aroundme);
        mCareer= (Button) findViewById(R.id.careerButton);
        mDummy= (Button) findViewById(R.id.dummybutton);
        mAroundMe.setOnClickListener(this);
        mCareer.setOnClickListener(this);
        mDummy.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.action_logout:
                new MaterialDialog.Builder(this)
                        .title("Logout")
                        .content("Apa anda yakin ingin keluar")
                        .positiveText("ya")
                        .negativeText("tidak")
//                        .icon(Drawable.createFromPath(String.valueOf(R.drawable.ic_media_play)))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                logoutUser();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                materialDialog.dismiss();
                            }
                        })
                        .show();

                break;
            case R.id.action_profile:
                showUserProfile();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showUserProfile() {
        // fungsi untuk melihat user profile
        Intent intentProfile=new Intent(this,ProfileActivity.class);
        startActivity(intentProfile);
    }

    private void logoutUser() {
        // Hilangkan session yang di simpern di mobile jadi harus login lagi kalau mau masuk ke app
        SharedPreferences sharedPreferences=getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("usernameSession");
        editor.remove("statusSession");
        editor.remove("imageSession");
        editor.apply();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // fungsi ketika user ketik back maka akan ke home bukan balik ke tampilan login
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aroundme:
                Intent intentAroundMe=new Intent(this,AroundMeActivity.class);
                intentAroundMe.putExtra(AroundMeActivity.username, usernameExtra);
                startActivity(intentAroundMe);
                break;
            case R.id.careerButton:
                Intent intentCareer=new Intent(this,CareerActivity.class);
//                intentAroundMe.putExtra(AroundMeActivity.username, usernameExtra);
                startActivity(intentCareer);
                break;
            case R.id.dummybutton:
                progressBar();
                break;
        }


    }

    private void progressBar() {
        // Create and show a non-indeterminate dialog with a max value of 150
        // If the showMinMax parameter is true, a min/max ratio will be shown to the left of the seek bar.
//        boolean showMinMax = true;
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("loading")
                .content("sabar gan")
                .progress(true,200)
                .show();
    }


}
