package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllProduct;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by semmi on 18/11/2015.
 */
public interface AllProductInterface {

    @GET("/retrieveProduct.php")
    void getProduct(Callback<List<AllProduct>> callback);
}
