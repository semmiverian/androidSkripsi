package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.SplashScreen;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by semmi on 17/11/2015.
 */
public interface SplashScreenInterface {

    @GET("/testConnection.php")
    void tesConnection(Callback<SplashScreen> callback);
}
