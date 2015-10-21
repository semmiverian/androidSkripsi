package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 21/10/2015.
 */
public class ShowAllUserLocation {
    private String username;
    private double longitude;
    private double latitude;

    public ShowAllUserLocation(){

    }
    public ShowAllUserLocation(String username,double longitude,double latitude){
        this.username=username;
        this.longitude=longitude;
        this.latitude=latitude;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
