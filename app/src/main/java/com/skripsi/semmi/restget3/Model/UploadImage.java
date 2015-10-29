package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 29/10/2015.
 */
public class UploadImage {
    private String image;
    private String kode;
    private String info;
    private String username;
    public UploadImage(){

    }
    public UploadImage(String image,String kode,String info,String username){
        this.image=image;
        this.kode=kode;
        this.info=info;
        this.username=username;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
