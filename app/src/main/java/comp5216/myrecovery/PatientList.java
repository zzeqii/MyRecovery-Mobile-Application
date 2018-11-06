package comp5216.myrecovery;

import java.util.ArrayList;

/**
 * Created by Saad on 4/10/17.
 */

public class PatientList {
    private ArrayList<Patient> patients;

    public PatientList(){
        patients = new ArrayList<Patient>();
    }

    public void addPatient(Patient p){
        patients.add(p);
    }
}
