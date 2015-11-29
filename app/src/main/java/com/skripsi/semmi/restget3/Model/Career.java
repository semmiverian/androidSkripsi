package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 27/10/2015.
 */
public class Career {
    private String karirNama;
    private String karirImage;

    public Career(){

    }
    public Career(String karirNama, String karirImage){
        this.karirNama=karirNama;
        this.karirImage = karirImage;
    }

    public String getKarirNama() {
        return karirNama;
    }

    public void setKarirNama(String karirNama) {
        this.karirNama = karirNama;
    }

    public String getKarirImage() {
        return karirImage;
    }

    public void setKarirImage(String karirImage) {
        this.karirImage = karirImage;
    }
}
