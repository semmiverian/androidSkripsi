package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.ShowAllUserLocation;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by semmi on 21/10/2015.
 */
public interface ShowAllUserLocationInterface {
    @GET("/showalluserlocation.php")
    void getLocation(Callback<List<ShowAllUserLocation>> callback);
}
