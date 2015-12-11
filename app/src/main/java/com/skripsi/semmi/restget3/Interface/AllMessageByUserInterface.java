package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.AllMessageByUser;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 11/12/2015.
 */
public interface AllMessageByUserInterface {

    @Multipart
    @POST("/allMessageByUser.php")
    void fetchMessageByUser(@Part("from_id") int from_id , Callback<List<AllMessageByUser>> callback);
}
