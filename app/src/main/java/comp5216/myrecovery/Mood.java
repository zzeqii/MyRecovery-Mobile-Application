package comp5216.myrecovery;

/**
 * Created by Saad on 20/10/17.
 */

import java.util.Date;

public class Mood {
    private String mood;
    private Date date;

    public Mood(String a){
        mood = a;
        date = new Date();
    }

    public String getMood(){
        return mood;
    }

    public String getDate(){
        return date.toString();
    }
}
