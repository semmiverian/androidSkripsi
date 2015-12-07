package com.skripsi.semmi.restget3.Helper;

/**
 * Created by semmi on 06/12/2015.
 */
public class DateHelper {

    public String changeDateFormat(String date){
        String month = "";
        switch (date.substring(5,7)){
            case "01":
                    month = "Januari";
                break;
            case "02":
                    month = "Februari";
                break;
            case "03":
                month = "Maret";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "Mei";
                break;
            case "06":
                month = "Juni";
                break;
            case "07":
                month = "Juli";
                break;
            case "08":
                month = "Agustus";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "Oktober";
                break;
            case "11":
                month = "November";
                break;
            case "12":
                month = "Desember";
                break;
        }

        String day = date.substring(8,10);
        return day+"  "+month;
    }
}
