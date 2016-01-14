package com.skripsi.semmi.restget3.Model;

import io.realm.RealmObject;

/**
 * Created by semmi on 29/12/2015.
 */
public class LocalMessage extends RealmObject {

    private int to_id;
    private String message;
    private int from_id;

    public LocalMessage(){}

    public LocalMessage(int to_id, String message, int from_id){
        this.to_id=to_id;
        this.message=message;
        this.from_id = from_id;
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

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }
}
