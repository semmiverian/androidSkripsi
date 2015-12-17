package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.DeleteData;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 17/12/2015.
 */
public interface DeleteProdukInterface {
    @Multipart
    @POST("/deleteProduk.php")
    void deleteProduk(@Part("produkId") String produkId , Callback<DeleteData> callback);

}
