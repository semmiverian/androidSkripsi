package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.DeleteData;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by semmi on 17/12/2015.
 */
public interface UpdateProdukInterface {
    @Multipart
    @POST("/updateProduk.php")
    void updateProduk(@Part("produkId") String produkId,@Part("produkNama") String produkNama , @Part("produkDetail") String produkDetail ,
                      @Part("produkEmail") String produkEmail,@Part("produkTelepon") String produkTelepon, @Part("image") TypedFile image,Callback<DeleteData> callback);

}
