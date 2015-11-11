package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.Message;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 10/11/2015.
 */
public interface getMessageInterface {
    @Multipart
    @POST("/getMessage.php")
    void getMessage(@Part("from_id") int from_id , @Part("to_id") int to_id, Callback<List<Message>> callback);
}
