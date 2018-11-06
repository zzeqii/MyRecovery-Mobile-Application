package comp5216.myrecovery;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Saad on 23/10/17.
 */

public class Medicine {
    private ArrayList<String> meds;
    private Date date;

    public Medicine(ArrayList<String> a){
        meds = a;
        date = new Date();
    }

    public ArrayList<String> getMeds(){
        return meds;
    }

    public String getDate(){
        return date.toString();
    }
}
