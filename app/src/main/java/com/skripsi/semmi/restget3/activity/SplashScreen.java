package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.SplashScreenInterface;
import com.skripsi.semmi.restget3.MainActivity;
import com.skripsi.semmi.restget3.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 17/11/2015.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        delayTesting();

    }

    // fungsi ini untuk nge delay buat cek koneksi
    // setelah 5 detik baru cek koneksi internetnya.
    private void delayTesting() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ConnectionTesting();
            }
        }, 3000);
    }

    // fungsi untuk mengecek koneksi ke internet sebelum bisa melanjutkan aplikasi
    private void ConnectionTesting() {
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
       SplashScreenInterface spi = restAdapter.create(SplashScreenInterface.class);
        spi.tesConnection(new Callback<com.skripsi.semmi.restget3.Model.SplashScreen>() {
            @Override
            public void success(com.skripsi.semmi.restget3.Model.SplashScreen splashScreen, Response response) {
                Intent intent =  new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Erro", "from Retrofit" + error.getMessage());
                new MaterialDialog.Builder(SplashScreen.this)
                        .title("No Connection")
                        .content("Anda tidak terhubung dengan internet, Silahkan coba kembali")
                        .positiveText("ya")
//                        .icon(Drawable.createFromPath(String.valueOf(R.drawable.ic_media_play)))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                ConnectionTesting();
                            }
                        })
                        .show();
            }
        });
    }
}
