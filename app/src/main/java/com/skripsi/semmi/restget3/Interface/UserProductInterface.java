package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllProduct;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 25/10/2015.
 */
public interface UserProductInterface {

    @Multipart
    @POST("/retrieveProductByUser.php")
    void getUserProduct(@Part("produkNama") String produkNama, Callback<List<AllProduct>> callback );
}
