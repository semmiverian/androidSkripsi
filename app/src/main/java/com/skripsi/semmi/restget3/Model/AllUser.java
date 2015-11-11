package com.skripsi.semmi.restget3.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by semmi on 09/11/2015.
 */
public class AllUser  implements Parcelable{
    private String username;
    private String image;
    private String nama;
    private String status;
    private int id;

    public AllUser(){

    }

    public AllUser(String username, String image, String nama, String status, int id ){
        this.username= username;
        this.image = image;
        this.nama = nama;
        this.status= status;
        this.id = id;
    }

    public AllUser(Parcel source){
        username=source.readString();
        image=source.readString();
        nama=source.readString();
        status=source.readString();
        id= source.readInt();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        defaultValues();
        dest.writeString(username);
        dest.writeString(image);
        dest.writeString(nama);
        dest.writeString(status);
        dest.writeInt(id);
    }

    private void defaultValues() {
        if(username== null){
            username="";
        }
        if(image == null){
            image= "";
        }
        if(nama == null){
            nama="";
        }
        if(status == null){
            status="";
        }

    }

    public static Creator<AllUser> CREATOR = new Creator<AllUser>() {
        @Override
        public AllUser createFromParcel(Parcel source) {
            return new AllUser(source);
        }

        @Override
        public AllUser[] newArray(int size) {
            return new AllUser[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
