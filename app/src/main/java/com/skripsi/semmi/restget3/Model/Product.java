package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 25/10/2015.
 */
public class Product {
    private String produkNama;
    private String produkImage;

    public Product(){

    }
    public Product(String produkNama, String produkImage){
        this.produkNama=produkNama;
        this.produkImage = produkImage;
    }


    public String getProdukNama() {
        return produkNama;
    }

    public void setProdukNama(String produkNama) {
        this.produkNama = produkNama;
    }

    public String getProdukImage() {
        return produkImage;
    }

    public void setProdukImage(String produkImage) {
        this.produkImage = produkImage;
    }
}
