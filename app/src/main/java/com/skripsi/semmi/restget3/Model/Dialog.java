package com.skripsi.semmi.restget3.Model;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

/**
 * Created by semmi on 13/12/2015.
 */
public class Dialog {
    private MaterialDrawableBuilder.IconValue icon;
    private String content;

    public Dialog(){}

    public Dialog(MaterialDrawableBuilder.IconValue icon, String content){
        this.icon=icon;
        this.content=content;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MaterialDrawableBuilder.IconValue getIcon() {
        return icon;
    }

    public void setIcon(MaterialDrawableBuilder.IconValue icon) {
        this.icon = icon;
    }
}
