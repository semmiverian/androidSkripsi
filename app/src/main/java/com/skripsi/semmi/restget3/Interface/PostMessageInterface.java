package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.PostMessage;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 11/11/2015.
 */
public interface PostMessageInterface {
    @Multipart
    @POST("/postMessage.php")
    void postMessage(@Part("from_id") int from_id, @Part("to_id") int to_id, @Part("pesan") String pesan, Callback<PostMessage> callback);
}
