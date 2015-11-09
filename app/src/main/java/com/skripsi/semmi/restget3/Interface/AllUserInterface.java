package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllUser;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by semmi on 09/11/2015.
 */
public interface AllUserInterface {
    @GET("/allUser.php")
    void getAllUser( Callback<List<AllUser>> callback);
}
