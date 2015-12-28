package com.skripsi.semmi.restget3.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Fragment.AboutUsFragment;
import com.skripsi.semmi.restget3.Fragment.HomeFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileCareerFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileProductListFragment;
import com.skripsi.semmi.restget3.Fragment.UserProfileSettingFragment;
import com.skripsi.semmi.restget3.Helper.DBopenHelper;
import com.skripsi.semmi.restget3.MainActivity;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.provider.UserProvider;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 15/10/2015.
 */
public class home_activity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    public static final String  username="username";
    public static final String  status="status";
    private ImageView userImage;
    private String imageUserLink;
    private SharedPreferences sharedPreferences;
    private TextView navigationName;
    private TextView navigationStatus;
    private NavigationView navigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayFirstFragment();

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


        // Kode ketika balik dari add new career dan product
        // kode nya biar bs ngeset fragment yang pas sama hal yang baru dilakukin
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

        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey("code3")){
                Log.d("kode", "dari delete produk");
                openProductFragment();
                navigationView.getMenu().getItem(2).setChecked(true);
                // TODO find way to change the Title at Toolbar
            }
        }
//        String nama = sharedPreferences.getString("usernameSession", "username");
        /*
            Database Testing code
         */
//        Cursor cursor = getContentResolver().query(UserProvider.CONTENT_URI,DBopenHelper.allColumns,null,null,null,null);
//        String[] datas = {DBopenHelper.USER_NAME};
//        saveCurrentUser(nama);
//        selectAllData(cursor)
        // set Intent Services to get location if the gps is on
//        Intent serviceIntent = new Intent(this, UpdateDataLocationServices.class);
//        startService(serviceIntent);
    }

    private void deleteCurrentUserSession() {
        getContentResolver().delete(UserProvider.CONTENT_URI,null,null);
    }

    // Code about Database Insert, Select Testing

    private void selectAllData(Cursor cursor) {
        int flag=0;
        if(cursor.moveToFirst()){
            String[] Users;
            String user;
            Users=new String[31];
            while(cursor.isAfterLast() == false && flag <=Users.length){
                user=cursor.getString(cursor.getColumnIndex(DBopenHelper.USER_NAME));
                Users[flag]=user;
                Log.d("cursor", Users[flag]);
                flag += 1;
                cursor.moveToNext();
            }
        }
    }

    private void saveCurrentUser(String nama) {
        ContentValues values = new ContentValues();
        values.put(DBopenHelper.USER_NAME,nama);
        Uri userUri = getContentResolver().insert(UserProvider.CONTENT_URI,values);
        Log.d("insert sukses ", userUri.getLastPathSegment());
    }


    private void openProductFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileProductListFragment.getInstance()).commit();
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
        deleteCurrentUserSession();
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
        // fungsi ketika user ketik back maka akan ke home bukan balik ke tampilan login
        finish();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
    }

    @Override
    public void onClick(View v) {

    }



    // fungsi ketika drawer di click
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
         int id = item.getItemId();
        if(id == R.id.nav_home){
            displayFirstFragment();
        }else if (id == R.id.nav_user_setting) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileSettingFragment.getInstance()).commit();
            openUserSettingFragment();
        } else if (id == R.id.nav_career) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileCareerFragment.getInstance()).commit();
            openCareerFragment();
        } else if (id == R.id.nav_product) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileProductListFragment.getInstance()).commit();
            openProductFragment();
        }else if(id == R.id.nav_logout){
            logoutConfirmationDialog();
        }else if(id == R.id.nav_about_us){
            openAboutUsFragment();
        }
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerMainLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openAboutUsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, AboutUsFragment.getInstance()).commit();
    }


}
