package com.skripsi.semmi.restget3.Model;

/**
 * Created by semmi on 11/12/2015.
 */
public class AllMessageByUser {

    private int to_id;
    private String to_username;
    private String to_image;


    public AllMessageByUser(){}

    public AllMessageByUser(int to_id , String to_username , String to_image){
        this.to_id = to_id;
        this.to_username = to_username;
        this.to_image= to_image;
    }


    public String getTo_username() {
        return to_username;
    }

    public void setTo_username(String to_username) {
        this.to_username = to_username;
    }

    public String getTo_image() {
        return to_image;
    }

    public void setTo_image(String to_image) {
        this.to_image = to_image;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }
}
