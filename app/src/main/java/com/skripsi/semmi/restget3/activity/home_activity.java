package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.skripsi.semmi.restget3.MainActivity;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 15/10/2015.
 */
public class home_activity extends AppCompatActivity {
    public static final String  username="username";
    private TextView mUserLogin;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mUserLogin= (TextView) findViewById(R.id.userLogin);
        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(username)){
                mUserLogin.setText(getIntent().getExtras().getString(username));
            }
        }
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
            case R.id.logout:
                logoutUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences=getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("usernameSession");
        editor.apply();
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
