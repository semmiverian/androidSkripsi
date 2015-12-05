package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.DeleteData;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 05/12/2015.
 */
public interface ChangePasswordInterface {
    @Multipart
    @POST("/changePassword.php")
    void requestChangePassword(@Part("userId") int id, @Part("oldPassword") String oldPassword, @Part("newPassword") String newPassword , Callback<DeleteData> call);
}
