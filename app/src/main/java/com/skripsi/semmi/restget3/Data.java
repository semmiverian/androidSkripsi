package com.skripsi.semmi.restget3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by semmi on 11/10/2015.
 */
public class Data implements Parcelable{
    private String judul;
    private String deskripsi;

    public Data(){

    }
    public Data(String judul, String deskripsi){
        this.judul=judul;
        this.deskripsi=deskripsi;
    }

    public Data(Parcel source){
        judul=source.readString();
        deskripsi=source.readString();
    }



    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        applyDefaultValues();
        dest.writeString(judul);
        dest.writeString(deskripsi);
    }

    private void applyDefaultValues() {
        if(judul==null){
            judul="";
        }
        if(deskripsi==null){
            deskripsi="";
        }
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

}
