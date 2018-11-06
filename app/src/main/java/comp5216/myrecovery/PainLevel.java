package comp5216.myrecovery;

import android.content.Intent;
import java.util.Date;

/**
 * Created by Saad on 4/10/17.
 */

public class PainLevel {

    private String level;
    private Date date;

    public PainLevel(String a){
        level = a;
        date = new Date();
    }

    public String getLevel(){
        return level;
    }

    public String getDate(){
        return date.toString();
    }
}
