package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.DeleteData;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by semmi on 03/12/2015.
 */
public interface UpdateCareerInterface {

    @Multipart
    @POST("/updateCareer.php")
    void postUpdateCareer(@Part("karirId") String karirId,@Part("karirNama") String karirNama , @Part("karirDetail") String karirDetail, @Part("image") TypedFile image,Callback<DeleteData> callback);
}
