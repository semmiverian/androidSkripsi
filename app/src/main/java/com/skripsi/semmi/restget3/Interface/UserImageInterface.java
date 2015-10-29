package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.UserImage;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 29/10/2015.
 */
public interface UserImageInterface {
    @Multipart
    @POST("/getUserProfilePicture.php")
    void getUserImage(@Part("username") String username, Callback<UserImage> callback);
}
