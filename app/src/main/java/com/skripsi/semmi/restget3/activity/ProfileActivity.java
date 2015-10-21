package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by semmi on 21/10/2015.
 */
public class ProfileActivity extends AppCompatActivity {
    public static final String  username="User";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(username)){
                this.setTitle(getIntent().getExtras().getString(username));
            }
        }
    }
}
