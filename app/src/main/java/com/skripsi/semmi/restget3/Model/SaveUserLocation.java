package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 20/10/2015.
 */
public class SaveUserLocation {
    private Double latitude;
    private Double longtitude;
    private String kode;

    public SaveUserLocation(){

    }
    public SaveUserLocation(Double latitude,Double longtitude,String kode){
        this.latitude=latitude;
        this.longtitude=longtitude;
        this.kode=kode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
}
