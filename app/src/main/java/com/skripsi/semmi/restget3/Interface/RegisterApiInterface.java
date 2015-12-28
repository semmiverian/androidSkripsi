package com.skripsi.semmi.restget3.Interface;

import com.skripsi.semmi.restget3.Model.Register;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by semmi on 13/10/2015.
 */
public interface RegisterApiInterface {

    @Multipart
    @POST("/register.php")
    void postRegister(@Part("username") String username,@Part("dob") String dob,@Part("email") String email, @Part("nama")
    String nama,@Part("jurusan") String jurusan,Callback<Register> callback);
    //void postRegister(@Body Register register, Callback<Register> callback);

}
