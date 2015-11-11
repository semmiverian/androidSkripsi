package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 09/11/2015.
 */
public class Message {
    private int from_id;
    private int to_id;
    private String pesan;
    private String from_image;
    private String to_image;

    public Message(){

    }

    public Message(int from_id, int to_id, String pesan, String from_image, String to_image){
        this.from_id= from_id;
        this.to_id = to_id;
        this.pesan= pesan;
        this.from_image = from_image;
        this.to_image = to_image;
    }
    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getFrom_image() {
        return from_image;
    }

    public void setFrom_image(String from_image) {
        this.from_image = from_image;
    }

    public String getTo_image() {
        return to_image;
    }

    public void setTo_image(String to_image) {
        this.to_image = to_image;
    }
}
