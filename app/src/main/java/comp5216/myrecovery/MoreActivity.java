package comp5216.myrecovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MoreActivity extends AppCompatActivity {

    private String testID;
    private Patient patient;

    private EditText mName;
    private EditText mPassword;
    private EditText mSurgery;
    private EditText mSex;
    private EditText mAge;

    private TextView mUserID;

    private TextView error;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homePage = new Intent(MoreActivity.this, MainActivity.class);
                    homePage.putExtra("user",testID);
                    startActivity(homePage);
                    break;
                case R.id.navigation_checklist:
                    Intent checklistPage = new Intent(MoreActivity.this, ChecklistActivity.class);
                    checklistPage.putExtra("user",testID);
                    startActivity(checklistPage);
                    break;
                case R.id.navigation_history:
                    Intent historyPage = new Intent(MoreActivity.this, HistoryActivity.class);
                    historyPage.putExtra("user",testID);
                    startActivity(historyPage);
                    break;
                case R.id.navigation_report:
                    Intent reportPage = new Intent(MoreActivity.this, ReportActivity.class);
                    reportPage.putExtra("user",testID);
                    startActivity(reportPage);
                    break;
                case R.id.navigation_more:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bot_nav);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_more);

        testID = getIntent().getStringExtra("user");
        getPatient();

        mName = (EditText) findViewById(R.id.editText6);
        mPassword = (EditText) findViewById(R.id.editText7);
        mSurgery = (EditText) findViewById(R.id.editText8);
        mSex = (EditText) findViewById(R.id.editText9);
        mAge = (EditText) findViewById(R.id.editText10);

        error = (TextView) findViewById(R.id.textView4);
        mUserID = (TextView) findViewById(R.id.textView8);

        mUserID.setText("User ID: "+testID);

        mName.setText(patient.getName());
        mPassword.setText(patient.getPassword());
        mSurgery.setText(patient.getSurgeryType());
        mSex.setText(patient.getSex());
        mAge.setText(patient.getAge());
    }

    public void onSubmit(View v){

        if( (!mAge.getText().toString().equals("")) && (!mName.getText().toString().equals("")) && (!mPassword.getText().toString().equals("")) && (!mSex.getText().toString().equals("")) && (!mSurgery.getText().toString().equals(""))) {

            patient.setAge(mAge.getText().toString());
            patient.setName(mName.getText().toString());
            patient.setPassword(mPassword.getText().toString());
            patient.setSex(mSex.getText().toString());
            patient.setSurgeryType(mSurgery.getText().toString());

            savePatient();

            Intent intent = new Intent(MoreActivity.this, MainActivity.class);
            intent.putExtra("user", testID);
            startActivity(intent);
        }
        else{
            error.setText("Please fill all the fields!");
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
}
