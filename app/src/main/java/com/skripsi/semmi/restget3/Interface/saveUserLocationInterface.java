package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.SaveUserLocation;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 20/10/2015.
 *
 */
public interface saveUserLocationInterface {
    @Multipart
    @POST("/savelocation.php")
    void saveLocation(@Part("Latitude") Double latitude,@Part("Longitude") Double longtitude,@Part("username") String username, Callback<SaveUserLocation> callback);

}
