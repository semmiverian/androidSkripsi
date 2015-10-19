package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.Login;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
/**
 * Created by semmi on 15/10/2015.
 */
public interface LoginApiInterface {
    @Multipart
    @POST("/login.php")
    void cekLogin(@Part("username") String username,@Part("password") String password, Callback<Login> callback);
}
