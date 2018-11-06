package comp5216.myrecovery;

import java.util.Date;

/**
 * Created by Saad on 23/10/17.
 */

public class Steps {
    private int steps;
    private Date date;

    public Steps(int a){
        steps = a;
        date = new Date();
    }

    public int getSteps(){
        return steps;
    }

    public String getDate(){
        return date.toString();
    }
}
