package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.Product;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 25/10/2015.
 */
public interface UserProductInterface {

    @Multipart
    @POST("/userProduct.php")
    void getUserProduct(@Part("username") String username, Callback<Product> callback );
}
