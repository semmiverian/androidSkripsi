package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 24/10/2015.
 */
public class ProfilePicture {
    private String image;

    public ProfilePicture(){

    }
    public ProfilePicture(String image){
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
