package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 15/10/2015.
 */
public class Login {
    private String username;
    private String password;
    private String kode;
    private String status;
    private String info;
    private String image;
    private int id;
    private String jurusan;
    private String tahunlulus;
    private String nama;

    public Login(){}
    public Login(String username,String password,String kode,String status,String info,String image,int id, String jurusan, String tahunlulus, String nama ){
        this.username=username;
        this.password=password;
        this.kode=kode;
        this.status=status;
        this.info=info;
        this.image=image;
        this.id=id;
        this.jurusan=jurusan;
        this.tahunlulus=tahunlulus;
        this.nama=nama;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }



    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTahunlulus() {
        return tahunlulus;
    }

    public void setTahunlulus(String tahunlulus) {
        this.tahunlulus = tahunlulus;
    }
}
