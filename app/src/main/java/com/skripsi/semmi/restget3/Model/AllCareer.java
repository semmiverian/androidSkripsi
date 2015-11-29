package com.skripsi.semmi.restget3.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by semmi on 01/11/2015.
 */
public class AllCareer implements Parcelable {
    private String karirnama;
    private String karirdetail;
    private String karirImage;
    private String karirCreate;
    private String userName;
    private String userImage;
    private String karirId;

    public AllCareer(){

    }
    public AllCareer(String karirnama, String karirdetail, String karirImage,String karirCreate, String userName, String userImage, String karirId){
        this.karirnama=karirnama;
        this.karirdetail=karirdetail;
        this.karirImage=karirImage;
        this.karirCreate=karirCreate;
        this.userName=userName;
        this.userImage=userImage;
        this.karirId=karirId;
    }

    public AllCareer(Parcel source){
        karirnama=source.readString();
        karirdetail=source.readString();
        karirImage=source.readString();
        karirCreate=source.readString();
        userName=source.readString();
        userImage=source.readString();
        karirId=source.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        applyDefaultValues();
        dest.writeString(karirnama);
        dest.writeString(karirdetail);
        dest.writeString(karirImage);
        dest.writeString(karirCreate);
        dest.writeString(userName);
        dest.writeString(userImage);
        dest.writeString(karirId);
    }

    private void applyDefaultValues() {
        if(karirnama==null){
            karirnama="";
        }
        if(karirdetail==null){
            karirdetail="";
        }
        if(karirImage==null){
            karirImage="";
        }
        if(karirCreate==null){
            karirCreate="";
        }
        if(userName==null){
            userName="";
        }
        if(userImage==null){
            userImage="";
        }
        if(karirId==null){
            karirId="";
        }
    }

    public static Creator<AllCareer> CREATOR = new Creator<AllCareer>() {
        @Override
        public AllCareer createFromParcel(Parcel source) {
            return new AllCareer(source);
        }

        @Override
        public AllCareer[] newArray(int size) {
            return new AllCareer[size];
        }
    };


      public String getKarirnama() {
        return karirnama;
    }

    public void setKarirnama(String karirnama) {
        this.karirnama = karirnama;
    }

    public String getKarirdetail() {
        return karirdetail;
    }

    public void setKarirdetail(String karirdetail) {
        this.karirdetail = karirdetail;
    }

    public String getKarirImage() {
        return karirImage;
    }

    public void setKarirImage(String karirImage) {
        this.karirImage = karirImage;
    }

    public String getKarirCreate() {
        return karirCreate;
    }

    public void setKarirCreate(String karirCreate) {
        this.karirCreate = karirCreate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getKarirId() {
        return karirId;
    }

    public void setKarirId(String karirId) {
        this.karirId = karirId;
    }
}
