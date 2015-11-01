package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllCareer;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by semmi on 01/11/2015.
 */
public interface  AllCareerInterface {
    @GET("/retrieveCareer.php")
    void getCareer(Callback<List<AllCareer>> callback);
}
