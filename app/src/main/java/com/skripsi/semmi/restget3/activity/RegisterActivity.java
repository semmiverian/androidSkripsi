package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.skripsi.semmi.restget3.Fragment.LoginFragment;
import com.skripsi.semmi.restget3.Fragment.RegisterFragment;
import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 15/10/2015.
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        displayRegisterScene();
    }
    private void displayRegisterScene() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container, RegisterFragment.getInstance()).commit();

    }
}
