package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 29/10/2015.
 */
public class UserImage {
    private String username;
    private String kode;
    private String info;
    private String image;
    public UserImage(){

    }
    public UserImage(String username, String kode,String info,String image){
        this.username=username;
        this.kode=kode;
        this.info=info;
        this.image=image;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
