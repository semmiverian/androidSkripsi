package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 25/10/2015.
 */
public class Product {
    private String produkNama;

    public Product(){

    }
    public Product(String produkNama){
        this.produkNama=produkNama;
    }


    public String getProdukNama() {
        return produkNama;
    }

    public void setProdukNama(String produkNama) {
        this.produkNama = produkNama;
    }
}
