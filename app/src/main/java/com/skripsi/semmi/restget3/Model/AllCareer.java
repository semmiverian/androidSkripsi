package com.skripsi.semmi.restget3.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by semmi on 01/11/2015.
 */
public class AllCareer implements Parcelable {
    private String nama;
    private String detail;
    private String image;

    public AllCareer(){

    }
    public AllCareer(String nama, String detail, String image){
        this.nama=nama;
        this.detail=detail;
        this.image=image;
    }

    public AllCareer(Parcel source){
        nama=source.readString();
        detail=source.readString();
        image=source.readString();
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        applyDefaultValues();
        dest.writeString(nama);
        dest.writeString(detail);
        dest.writeString(image);
    }

    private void applyDefaultValues() {
        if(nama==null){
            nama="";
        }
        if(detail==null){
            detail="";
        }
        if(image==null){
            image="";
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
}
