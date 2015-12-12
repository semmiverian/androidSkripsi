package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllUser;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 12/12/2015.
 */
public interface SearchUserInterface {
    @Multipart
    @POST("/searchUser.php")
    void findUser(@Part("query") String query , Callback<List<AllUser>> callback);
}
