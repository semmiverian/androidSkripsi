package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.Career;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 27/10/2015.
 */
public interface UserCareerInterface {
    @POST("/retrieveUserCareer.php")
    @Multipart
    void getCareer(@Part("karirNama") String karirnama, Callback<List<Career>> callback);
}
