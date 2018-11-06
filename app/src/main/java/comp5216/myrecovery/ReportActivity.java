package comp5216.myrecovery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class ReportActivity extends AppCompatActivity {

    private String seekBarValue;
    private String moodValue;
    private String heartRate;
    private ArrayList<String> medicines;

    private String testID;
    private Patient patient;
    private int numSteps;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homePage = new Intent(ReportActivity.this, MainActivity.class);
                    homePage.putExtra("user",testID);
                    startActivity(homePage);
                    break;
                case R.id.navigation_checklist:
                    Intent checklistPage = new Intent(ReportActivity.this, ChecklistActivity.class);
                    checklistPage.putExtra("user",testID);
                    startActivity(checklistPage);
                    break;
                case R.id.navigation_history:
                    Intent historyPage = new Intent(ReportActivity.this, HistoryActivity.class);
                    historyPage.putExtra("user",testID);
                    startActivity(historyPage);
                    break;
                case R.id.navigation_report:
                    return true;
                case R.id.navigation_more:
                    Intent morePage = new Intent(ReportActivity.this, MoreActivity.class);
                    morePage.putExtra("user",testID);
                    startActivity(morePage);
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bot_nav);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_report);

        testID = getIntent().getStringExtra("user");
        numSteps = getIntent().getIntExtra("numSteps",0);

        getPatient();

        patient.addSteps(numSteps);
        savePatient();

        MarshMallowPermission marshMallowPermission = new MarshMallowPermission(this);

            if (!marshMallowPermission.checkPermissionForCamera()){
                marshMallowPermission.requestPermissionForCamera();
            }
    }

    public void onClickPain(View v){
        Intent pain = new Intent(ReportActivity.this, ReportPain.class);
        pain.putExtra("user",testID);
        startActivityForResult(pain, 2);
    }

    public void onClickMedicine(View v){
        Intent medicine = new Intent(ReportActivity.this, ReportMedicine.class);
        medicine.putExtra("user",testID);
        startActivityForResult(medicine,5);
    }

    public void onClickMood(View v){
        Intent mood = new Intent(ReportActivity.this, ReportMood.class);
        mood.putExtra("user",testID);
        startActivityForResult(mood, 3);
    }

    public void onClickHeart(View v){
        Intent heart = new Intent(ReportActivity.this, HeartRateMonitor.class);
        heart.putExtra("user",testID);
        startActivityForResult(heart,4);
    }

    public void getPatient() {
        Thread t = new Thread() {
            public void run() {
                try {
                    patient = DataMgt.downloadPatient(testID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                seekBarValue = data.getStringExtra("level");
                patient.addPain(seekBarValue);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }

        if (requestCode == 3) {
            if(resultCode == Activity.RESULT_OK){
                moodValue = data.getStringExtra("mood");
                patient.addMood(moodValue);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }

        if (requestCode == 4) {
            if(resultCode == Activity.RESULT_OK){
                heartRate = data.getStringExtra("rate");
                patient.addHeartRate(heartRate);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }

        if (requestCode == 5) {
            if(resultCode == Activity.RESULT_OK){
                medicines = data.getExtras().getStringArrayList("list");
                patient.addMeds(medicines);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    public void savePatient(){
        Thread t = new Thread() {
            public void run() {
                try {
                    DataMgt.uploadPatient(patient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        try {
            t.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void onSubmit(View v) throws Exception {

        savePatient();

        Intent intent = new Intent(ReportActivity.this, MainActivity.class);
        intent.putExtra("user",testID);
        startActivity(intent);
    }
}
