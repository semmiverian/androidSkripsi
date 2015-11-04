package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.NewCareer;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by semmi on 03/11/2015.
 */
public interface NewCareerInterface {
    @Multipart
    @POST("/addNewCareer.php")
    void postCareer(@Part("username") String username, @Part("judul") String judul, @Part("detail") String detail,@Part("image") TypedFile image, Callback<NewCareer> callback);
}
