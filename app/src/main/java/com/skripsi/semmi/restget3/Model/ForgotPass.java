package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 19/10/2015.
 */
public class ForgotPass {
    private String username;
    private String email;
    private String kode;
    private String status;

    public ForgotPass(){}

    public ForgotPass(String username,String email,String kode,String status){
        this.username=username;
        this.email=email;
        this.kode=kode;
        this.status=status;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
