package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 03/11/2015.
 */
public class NewProduk {

    private String kode;
    private String info;

    public NewProduk(){

    }

    public NewProduk(String kode, String info){
        this.kode=kode;
        this.info=info;
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
}
