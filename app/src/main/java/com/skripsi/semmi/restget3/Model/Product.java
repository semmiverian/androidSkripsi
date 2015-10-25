package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 25/10/2015.
 */
public class Product {
    private String nama;

    public Product(){

    }
    public Product(String nama){
        this.nama=nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
