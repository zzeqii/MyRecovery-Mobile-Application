package comp5216.myrecovery;

import java.util.Date;

/**
 * Created by Saad on 22/10/17.
 */

public class HeartRate {
    private String rate;
    private Date date;

    public HeartRate(String a){
        rate = a;
        date = new Date();
    }

    public String getHeartRate(){
        return rate;
    }

    public String getDate(){
        return date.toString();
    }
}
