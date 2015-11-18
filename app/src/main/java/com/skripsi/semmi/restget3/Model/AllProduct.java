package com.skripsi.semmi.restget3.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by semmi on 17/11/2015.
 */
public class AllProduct implements Parcelable {

    private String produknama;
    private String produkdetail;
    private String produkImage;
    private String produkCreate;
    private String userName;
    private String userImage;


    public AllProduct(){}

    public AllProduct(String produknama, String produkdetail, String produkImage, String produkCreate , String userName , String userImage){
        this.produknama = produknama;
        this.produkdetail= produkdetail;
        this.produkImage = produkImage;
        this.produkCreate = produkCreate;
        this.userName = userName;
        this.userImage = userImage;
    }

    public AllProduct(Parcel source){
        produknama= source.readString();
        produkdetail= source.readString();
        produkImage= source.readString();
        produkCreate= source.readString();
        userName= source.readString();
        userImage= source.readString();
    }

    public String getProduknama() {
        return produknama;
    }

    public void setProduknama(String produknama) {
        this.produknama = produknama;
    }

    public String getProdukdetail() {
        return produkdetail;
    }

    public void setProdukdetail(String produkdetail) {
        this.produkdetail = produkdetail;
    }

    public String getProdukImage() {
        return produkImage;
    }

    public void setProdukImage(String produkImage) {
        this.produkImage = produkImage;
    }

    public String getProdukCreate() {
        return produkCreate;
    }

    public void setProdukCreate(String produkCreate) {
        this.produkCreate = produkCreate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        defaultValues();
        dest.writeString(produknama);
        dest.writeString(produkdetail);
        dest.writeString(produkImage);
        dest.writeString(produkCreate);
        dest.writeString(userName);
        dest.writeString(userImage);

    }

    private void defaultValues() {
        if(produknama == null){
            produknama="";
        }
        if(produkdetail == null){
            produkdetail="";
        }
        if(produkImage == null){
            produkImage="";
        }
        if(produkCreate == null){
            produkCreate="";
        }
        if(userName==null){
            userName="";
        }
        if(userImage==null){
            userImage="";
        }
    }

    public static  Creator<AllProduct> CREATOR = new Creator<AllProduct>() {
        @Override
        public AllProduct createFromParcel(Parcel source) {
            return new AllProduct(source);
        }

        @Override
        public AllProduct[] newArray(int size) {
            return new AllProduct[size];
        }
    };
}
