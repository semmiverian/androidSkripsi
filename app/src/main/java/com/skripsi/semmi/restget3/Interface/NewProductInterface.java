package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.NewCareer;
import com.skripsi.semmi.restget3.Model.NewProduk;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by semmi on 03/11/2015.
 */
public interface NewProductInterface {
    @Multipart
    @POST("/addNewProduk.php")
    void postProduk(@Part("username") String username, @Part("judul") String judul, @Part("detail") String detail, @Part("image") TypedFile image,@Part("email") String email, @Part("telepon") String telepon,@Part("price") String price, Callback<NewProduk> callback);
}
