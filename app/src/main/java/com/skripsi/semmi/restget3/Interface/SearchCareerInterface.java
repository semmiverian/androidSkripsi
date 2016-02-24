package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllCareer;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 24/02/2016.
 */
public interface SearchCareerInterface {

    @Multipart
    @POST("/searchCareer.php")
    void searchCareer( @Part("query") String query , Callback<List<AllCareer>> callback);
}
