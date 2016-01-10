package com.skripsi.semmi.restget3.Model;

import io.realm.RealmObject;

/**
 * Created by semmi on 29/12/2015.
 */
public class LocalMessage extends RealmObject {

    private int to_id;
    private String message;

    public LocalMessage(){}

    public LocalMessage(int to_id, String message){
        this.to_id=to_id;
        this.message=message;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTo_id() {
        return to_id;
    }

    public void setTo_id(int to_id) {
        this.to_id = to_id;
    }
}
