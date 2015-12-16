package com.skripsi.semmi.restget3.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by semmi on 13/10/2015.
 */
public class Register {
    private String username;
    private String dob;
    private String email;
    private String kode;
    private String status;
    private String jurusan;
    private String tahunLulus;
    // Default Constructor
    public Register(){

    }
    // Constructor
    public Register(String username,String dob,String email,String kode ,String status, String jurusan, String tahunLulus){
        this.username=username;
        this.dob=dob;
        this.email=email;
        this.kode=kode;
        this.status=status;
        this.jurusan=jurusan;
        this.tahunLulus=tahunLulus;
    }

    // Setter Getter setiap field
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String toJSON() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("dob", dob);
            jsonObject.put("email", email);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
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

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getTahunLulus() {
        return tahunLulus;
    }

    public void setTahunLulus(String tahunLulus) {
        this.tahunLulus = tahunLulus;
    }
}
