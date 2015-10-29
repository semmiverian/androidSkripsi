package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.UploadImage;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by semmi on 29/10/2015.
 */
public interface UploadImageInterface {
    @Multipart
    @POST("/UploadProfilePicture.php")
    void postImage(@Part("image") TypedFile image , @Part("username") String username , Callback<UploadImage> callback);
}
