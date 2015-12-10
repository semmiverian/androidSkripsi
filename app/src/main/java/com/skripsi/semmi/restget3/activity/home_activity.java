package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Fragment.HomeFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileCareerFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileProductListFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileSettingFragment;
import com.skripsi.semmi.restget3.MainActivity;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 15/10/2015.
 */
public class home_activity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    public static final String  username="username";
    public static final String  status="status";
    private TextView mUserLogin;
    private TextView mStatusLogin;
    private Button mAroundMe;
    private Button mCareer;
    private Button mDummy;
    private Button allUser;
    private Button product;
    private Button userProfileBeta;
    private ImageView userImage;
    private String usernameExtra;
    private String statusExtra;
    private String imageUserLink;
    private SharedPreferences sharedPreferences;
    private TextView navigationName;
    private TextView navigationStatus;
    private NavigationView navigationView;
    public final static int addCareerCode =112;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayFirstFragment();




        // Content responding code
//        mUserLogin= (TextView) findViewById(R.id.userLogin);
//        mStatusLogin= (TextView) findViewById(R.id.statusLogin);
        // validasi dan ngambil data dari aktivitas login
//        if(getIntent()!= null && getIntent().getExtras()!=null){
//            if(getIntent().getExtras().containsKey(username)){
//                mUserLogin.setText(getIntent().getExtras().getString(username));
//                usernameExtra=mUserLogin.getText().toString();
//                mStatusLogin.setText(getIntent().getExtras().getString(status));
//            }
//        }

        // find ID code
//        mAroundMe= (Button) findViewById(R.id.aroundme);
//        mCareer= (Button) findViewById(R.id.careerButton);
//        mDummy= (Button) findViewById(R.id.dummybutton);
//        allUser= (Button) findViewById(R.id.allUserButton);
//        product = (Button) findViewById(R.id.trading);
//        userProfileBeta = (Button) findViewById(R.id.userProfileBeta);



        // on click listener
//        mAroundMe.setOnClickListener(this);
//        mCareer.setOnClickListener(this);
//        mDummy.setOnClickListener(this);
//        allUser.setOnClickListener(this);
//        product.setOnClickListener(this);
//        userProfileBeta.setOnClickListener(this);




        // Drawer layout responding code
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerMainLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        // define data from navigation header
        View headerLayout = navigationView.getHeaderView(0);
        userImage = (ImageView) headerLayout.findViewById(R.id.userImageNavigation);
        navigationName = (TextView) headerLayout.findViewById(R.id.namaUser);
        navigationStatus = (TextView) headerLayout.findViewById(R.id.statusUser);
        loadUserData();

        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey("code")){
                Log.d("kode", "dari add new Career");
                openCareerFragment();
                navigationView.getMenu().getItem(2).setChecked(true);
                // TODO find way to change the Title at Toolbar

            }
        }

        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey("code2")){
                Log.d("kode", "dari add new Career");
                openUserSettingFragment();
                navigationView.getMenu().getItem(1).setChecked(true);
                // TODO find way to change the Title at Toolbar

            }
        }

        // set Intent Services to get location if the gps is on
//        Intent serviceIntent = new Intent(this, UpdateDataLocationServices.class);
//        startService(serviceIntent);
    }

    private void openUserSettingFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileSettingFragment.getInstance()).commit();
    }

    private void openCareerFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileCareerFragment.getInstance()).commit();

    }

    private void displayFirstFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment.getInstance()).commit();

    }


    // Seting header content dynamically here
    private void loadUserData() {
        sharedPreferences = this.getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        imageUserLink=sharedPreferences.getString("imageSession", "image");
        String nama = sharedPreferences.getString("usernameSession", "username");
        String status = sharedPreferences.getString("statusSession", "status");
        navigationName.setText(nama);
        navigationStatus.setText(status);
        Picasso.with(this)
                .load(imageUserLink)
                .into(userImage);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id=item.getItemId();
//        switch (id){
//            case R.id.action_logout:
//                logoutConfirmationDialog();
//                break;
//            case R.id.action_profile:
//                showUserProfile();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void logoutConfirmationDialog() {
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
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerMainLayout);
                        drawer.openDrawer(GravityCompat.START);
                    }
                })
                .show();


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
        editor.remove("idSession");
        editor.apply();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
        // fungsi ketika user ketik back maka akan ke home bukan balik ke tampilan login
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(a);

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.aroundme:
//                Intent intentAroundMe=new Intent(this,AroundMeActivity.class);
//                intentAroundMe.putExtra(AroundMeActivity.username, usernameExtra);
//                startActivity(intentAroundMe);
//                break;
//            case R.id.careerButton:
//                Intent intentCareer=new Intent(this,CareerActivity.class);
////                intentAroundMe.putExtra(AroundMeActivity.username, usernameExtra);
//                startActivity(intentCareer);
//                break;
//            case R.id.dummybutton:
//                progressBar();
//                break;
//            case R.id.allUserButton:
//                Intent intentUser = new Intent(this, AllUserActivity.class);
//                startActivity(intentUser);
//                break;
//            case R.id.trading:
//                Intent intentTrading = new Intent(this , AllProductActivity.class);
//                startActivity(intentTrading);
//                break;
//            case R.id.userProfileBeta:
//                Intent userProfileBetaIntent = new Intent(this, UserProfileNewActivity.class);
//                startActivity(userProfileBetaIntent);
//                break;
//        }


    }



    // fungsi ketika drawer di click
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
         int id = item.getItemId();
        if(id == R.id.nav_home){
            displayFirstFragment();
        }else if (id == R.id.nav_user_setting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileSettingFragment.getInstance()).commit();
        } else if (id == R.id.nav_career) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileCareerFragment.getInstance()).commit();
        } else if (id == R.id.nav_product) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileProductListFragment.getInstance()).commit();
        }else if(id == R.id.nav_logout){
            logoutConfirmationDialog();
        }
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerMainLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
