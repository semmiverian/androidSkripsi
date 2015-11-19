package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllUser;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 19/11/2015.
 */
public interface UserProfileFromAroundMe  {
    @Multipart
    @POST("/userFromAroundMe.php")
    void fetchUser( @Part("username") String username , Callback<AllUser> callback);
}
