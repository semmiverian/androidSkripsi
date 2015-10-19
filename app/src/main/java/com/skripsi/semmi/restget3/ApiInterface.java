package com.skripsi.semmi.restget3;



import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by semmi on 11/10/2015.
 */
public interface ApiInterface {
    @GET("/api.php")
    void getStreams(Callback<List<Data>> callback);

}
