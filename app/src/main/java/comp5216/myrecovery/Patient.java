package comp5216.myrecovery;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Saad on 4/10/17.
 */

public class Patient {
    private String id;
    private String name;
    private String age;
    private String surgeryType;
    private String password;
    private String sex;
    private ArrayList<PainLevel> painHistory;
    private ArrayList<Mood> moodHistory;
    private ArrayList<HeartRate> heartRateHistory;
    private ArrayList<Steps> stepsHistory;
    private ArrayList<Medicine> medHistory;
    private Date surgeryDate;

    public Patient(String idd){
        id = idd;
        surgeryDate = new Date();
        painHistory = new ArrayList<PainLevel>();
        moodHistory = new ArrayList<Mood>();
        heartRateHistory = new ArrayList<HeartRate>();
        stepsHistory = new ArrayList<Steps>();
        medHistory = new ArrayList<Medicine>();
    }

    public void setName(String nm){
        name = nm;
    }

    public void setAge(String ag){
        age = ag;
    }

    public void setSurgeryType(String surtyp){
        surgeryType = surtyp;
    }

    public void setPassword(String pwd){
        password = pwd;
    }

    public void setSex(String sx){
        sex = sx;
    }

    public String getName(){
        return name;
    }

    public String getAge(){
        return age;
    }

    public String getId(){
        return id;
    }

    public String getSex(){
        return sex;
    }

    public String getPassword(){
        return password;
    }

    public String getSurgeryType(){
        return surgeryType;
    }

    public String getSurgeryDate(){
        return surgeryDate.toString();
    }

    public ArrayList<PainLevel> getPainHistory(){
        return painHistory;
    }

    public ArrayList<Mood> getMoodHistory(){
        return moodHistory;
    }

    public ArrayList<HeartRate> getHeartRateHistory(){
        return heartRateHistory;
    }

    public ArrayList<Steps> getStepsHistory(){
        return stepsHistory;
    }

    public ArrayList<Medicine> getMedHistory(){
        return medHistory;
    }

    public void addHeartRate(String hr){
        heartRateHistory.add(new HeartRate(hr));
    }

    public void addHeartRate(HeartRate hr){
        heartRateHistory.add(hr);
    }

    public void addSteps(int st){
        stepsHistory.add(new Steps(st));
    }

    public void addSteps(Steps st){
        stepsHistory.add(st);
    }

    public void addMeds(ArrayList<String> ars){
        medHistory.add(new Medicine(ars));
    }

    public void addMeds(Medicine m){
        medHistory.add(m);
    }

    public void addPain(String lv){
        painHistory.add(new PainLevel(lv));
    }

    public void addPain(PainLevel lv){
        painHistory.add(lv);
    }

    public void addMood(String md){
        moodHistory.add(new Mood(md));
    }

    public void addMood(Mood md){
        moodHistory.add(md);
    }
}
