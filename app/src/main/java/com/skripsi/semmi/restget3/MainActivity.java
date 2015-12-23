package com.skripsi.semmi.restget3;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Fragment.LoginFragment;
import com.skripsi.semmi.restget3.Helper.DBopenHelper;
import com.skripsi.semmi.restget3.Interface.LoginApiInterface;
import com.skripsi.semmi.restget3.Model.Login;
import com.skripsi.semmi.restget3.activity.ForgotPassActivity;
import com.skripsi.semmi.restget3.activity.RegisterActivity;
import com.skripsi.semmi.restget3.activity.home_activity;
import com.skripsi.semmi.restget3.provider.UserProvider;
import com.vstechlab.easyfonts.EasyFonts;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mRegister;
    private Button mLogin;
    private EditText mUsername;
    private EditText mPassword;
    private TextView mForgotPass;
    private  SharedPreferences sharedPreferences;
    private MaterialDialog dialog;
    private SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_login_page);
        sharedPreferences = this.getSharedPreferences("Session Check", Context.MODE_PRIVATE);

//        Log.d("preferences", nama);

//        checkDbSessionUser();

        mUsername= (EditText) findViewById(R.id.username);
        mUsername.setTypeface(EasyFonts.robotoLight(this));
        mPassword= (EditText) findViewById(R.id.password);
        mPassword.setTypeface(EasyFonts.robotoLight(this));
        mForgotPass= (TextView) findViewById(R.id.forgotGo);
        mRegister= (TextView) findViewById(R.id.registerGo);
        mRegister.setOnClickListener(this);
        mLogin= (Button) findViewById(R.id.loginGo);
        mLogin.setTypeface(EasyFonts.robotoBold(this));
        mLogin.setOnClickListener(this);
        mForgotPass.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String nama = sharedPreferences.getString("usernameSession", null);
        checkSessionUser(nama);
    }

    private void checkDbSessionUser() {
            Cursor cursor =getContentResolver().query(UserProvider.CONTENT_URI, DBopenHelper.allColumns,null,null,null,null);
            Log.d("testcursor",""+cursor.getCount());
            if(cursor.getCount()==1){
                Intent intent1 = new Intent(this, home_activity.class);
                startActivity(intent1);
                return;
            }
//            displayInitialFragment();
            Log.d("problem","Masih ada problem pas cek session dari SQLite");
    }

    private void checkSessionUser(String nama) {
        if(nama!= null){
            Intent intent1 = new Intent(this, home_activity.class);
            startActivity(intent1);

        }
//        displayInitialFragment();
        Log.d("BUG","dari main activity");
    }

    private void displayInitialFragment() {
//        Default Fragment when user open the App
        getSupportFragmentManager().beginTransaction().replace(R.id.container, LoginFragment.getInstance()).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
        // fungsi ketika user ketik back maka akan ke home bukan balik ke tampilan login
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(a);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerGo:
                Toast.makeText(this, "Register Click", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, 102);
                break;
            case R.id.loginGo:
                // buat sebuah dialog bar biar bisa ngasih tau user apa yang sedang terjadi

                dialog = new MaterialDialog.Builder(this)
                        .title("Proses")
                        .content("Connecting to server")
                        .progress(true,0)
                        .show();
                String username=mUsername.getText().toString();
                String password=mPassword.getText().toString();
                RestAdapter restAdapter=new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.api))
                        .build();
                LoginApiInterface loginApiInterface = restAdapter.create(LoginApiInterface.class);
                loginApiInterface.cekLogin(username, password, new Callback<Login>() {
                    @Override
                    public void success(Login login, Response response) {
                        // Log.d("sukses",""+login.getKode());
                        if(login.getKode().equals("4")) {
//                             dialog.dismiss();

                            dialog.setContent("Berhasil Log in");
                            // kalau sukses bakal simpen kaya season ke device

                            editor=sharedPreferences.edit();
                            editor.putString("usernameSession",login.getUsername());
                            editor.putString("statusSession", login.getStatus());
                            editor.putString("imageSession", login.getImage());
                            editor.putInt("idSession", login.getId());
                            editor.putString("jurusanSession", login.getJurusan());
                            editor.putString("angkatanSession",login.getTahunlulus());
                            editor.putString("namaSession",login.getNama());
                            editor.apply();

//                            saveCurrentUser(login.getUsername());

                            Intent intent1 = new Intent(MainActivity.this, home_activity.class);
                            startActivity(intent1);
                        }else{
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this,"Error"+login.getInfo(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("post Error","from Retrofit"+error.getMessage());
                    }
                });

                break;
            case  R.id.forgotGo:
                Intent intent3=new Intent(this, ForgotPassActivity.class);
                startActivity(intent3);
                break;
        }
    }

    private void saveCurrentUser(String username) {
        ContentValues values = new ContentValues();
        values.put(DBopenHelper.USER_NAME,username);
        Uri userUri = getContentResolver().insert(UserProvider.CONTENT_URI,values);
        Log.d("insert sukses ", userUri.getLastPathSegment());
    }
}
