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
    private String karirEmail;
    private String karirTelepon;
    private String userNama;

    public AllCareer(){

    }
    public AllCareer(String karirnama, String karirdetail, String karirImage,String karirCreate, String userName, String userImage, String karirId, String karirEmail, String karirTelepon, String userNama){
        this.karirnama=karirnama;
        this.karirdetail=karirdetail;
        this.karirImage=karirImage;
        this.karirCreate=karirCreate;
        this.userName=userName;
        this.userImage=userImage;
        this.karirId=karirId;
        this.karirEmail=karirEmail;
        this.karirTelepon = karirTelepon;
        this.userNama = userNama;
    }

    public AllCareer(Parcel source){
        karirnama=source.readString();
        karirdetail=source.readString();
        karirImage=source.readString();
        karirCreate=source.readString();
        userName=source.readString();
        userImage=source.readString();
        karirId=source.readString();
        karirEmail=source.readString();
        karirTelepon=source.readString();
        userNama=source.readString();
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
        dest.writeString(karirEmail);
        dest.writeString(karirTelepon);
        dest.writeString(userNama);
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
        if(karirEmail==null){
            karirEmail="";
        }
        if(karirTelepon==null){
            karirTelepon="";
        }if(userNama==null){
            userNama="";
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

    public String getKarirEmail() {
        return karirEmail;
    }

    public void setKarirEmail(String karirEmail) {
        this.karirEmail = karirEmail;
    }

    public String getKarirTelepon() {
        return karirTelepon;
    }

    public void setKarirTelepon(String karirTelepon) {
        this.karirTelepon = karirTelepon;
    }

    public String getUserNama() {
        return userNama;
    }

    public void setUserNama(String userNama) {
        this.userNama = userNama;
    }
}
