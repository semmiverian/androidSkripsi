package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.DeleteData;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 09/03/2016.
 */
public interface ChangeStatusInterface {
    @Multipart
    @POST("/changeStatus.php")
    void changeStatus(@Part("id") int id , Callback<DeleteData> callback);
}
