package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllProduct;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 23/11/2015.
 */
public interface SearchProductInterface {
    @Multipart
    @POST("/searchResultProduk.php")
    void fetchSearch(@Part("query") String query, Callback<List<AllProduct>> callback );
}
