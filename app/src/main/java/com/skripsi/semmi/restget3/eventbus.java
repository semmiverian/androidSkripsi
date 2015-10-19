package com.skripsi.semmi.restget3;

import com.squareup.otto.Bus;

/**
 * Created by semmi on 11/10/2015.
 */
public class eventbus extends Bus {
    private static final eventbus bus=new eventbus();

    public static  Bus getInstance(){
        return bus;
    }

    private eventbus(){}
}
