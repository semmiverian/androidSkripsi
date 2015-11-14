package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 11/11/2015.
 */
public class PostMessage {
    private String kode;
    private String info;

    public PostMessage(){}

    public PostMessage(String kode, String info){
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
