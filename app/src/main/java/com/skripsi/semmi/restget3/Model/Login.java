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

    public Login(){}
    public Login(String username,String password,String kode,String status,String info){
        this.username=username;
        this.password=password;
        this.kode=kode;
        this.status=status;
        this.info=info;
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
}
