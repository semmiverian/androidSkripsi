package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.DeleteData;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 29/11/2015.
 */
public interface DeleteCareerInterface {
    @Multipart
    @POST("/deleteCareer.php")
    void deleteData(@Part("karirId") String karirId , Callback<DeleteData> callback);
}
