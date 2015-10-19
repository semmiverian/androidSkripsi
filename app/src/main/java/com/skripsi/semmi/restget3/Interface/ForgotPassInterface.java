package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.ForgotPass;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 19/10/2015.
 */
public interface ForgotPassInterface {
    @Multipart
    @POST("/forgotpass.php")
    void postForgotPassword(@Part("username") String username,@Part("email") String email, Callback<ForgotPass> callback);
}
