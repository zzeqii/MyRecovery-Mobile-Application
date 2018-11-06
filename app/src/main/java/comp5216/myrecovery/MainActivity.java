package comp5216.myrecovery;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements SensorEventListener,StepListener {

    private TextView greetingWords;
    private TextView message;

    private int numSteps;
    private TextView TvSteps;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;

    String testID;
    private Patient patient;

    Context context = this;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_checklist:
                    Intent checklistPage = new Intent(MainActivity.this, ChecklistActivity.class);
                    checklistPage.putExtra("user",testID);
                    startActivity(checklistPage);
                    break;
                case R.id.navigation_history:
                    Intent historyPage = new Intent(MainActivity.this, HistoryActivity.class);
                    historyPage.putExtra("user",testID);
                    startActivity(historyPage);
                    break;
                case R.id.navigation_report:
                    Intent reportPage = new Intent(MainActivity.this, ReportActivity.class);
                    reportPage.putExtra("user",testID);
                    reportPage.putExtra("numSteps",numSteps);
                    startActivity(reportPage);
                    break;
                case R.id.navigation_more:
                    Intent morePage = new Intent(MainActivity.this, MoreActivity.class);
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
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bot_nav);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        greetingWords = (TextView) findViewById(R.id.greeting);
        message = (TextView) findViewById(R.id.message);

        testID = getIntent().getStringExtra("user");

        getPatient();

        ArrayList<Steps> stepsHistory = patient.getStepsHistory();
        int size = stepsHistory.size();

        if(size > 0){
            numSteps = stepsHistory.get(size - 1).getSteps();
        } else {
            numSteps = 0;
        }

        greetingWords.setText("Hi, " + patient.getName() + "!");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = (TextView) findViewById(R.id.textView7);
        TvSteps.setText(numSteps+"");

        sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(numSteps+"");
    }
}
