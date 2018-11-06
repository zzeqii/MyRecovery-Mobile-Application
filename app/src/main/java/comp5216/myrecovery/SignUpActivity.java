package comp5216.myrecovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mUserID;
    private EditText mPassword;
    private EditText mSurgery;
    private EditText mSex;
    private EditText mAge;

    private TextView error;

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mName = (EditText) findViewById(R.id.editText5);
        mUserID = (EditText) findViewById(R.id.editText6);
        mPassword = (EditText) findViewById(R.id.editText7);
        mSurgery = (EditText) findViewById(R.id.editText8);
        mSex = (EditText) findViewById(R.id.editText9);
        mAge = (EditText) findViewById(R.id.editText10);

        error = (TextView) findViewById(R.id.textView4);
    }

    public void onSubmit(View v){

        if( (!mUserID.getText().toString().equals("")) && (!mAge.getText().toString().equals("")) && (!mName.getText().toString().equals("")) && (!mPassword.getText().toString().equals("")) && (!mSex.getText().toString().equals("")) && (!mSurgery.getText().toString().equals(""))) {

            patient = new Patient(mUserID.getText().toString());
            patient.setAge(mAge.getText().toString());
            patient.setName(mName.getText().toString());
            patient.setPassword(mPassword.getText().toString());
            patient.setSex(mSex.getText().toString());
            patient.setSurgeryType(mSurgery.getText().toString());

            savePatient();

            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
            intent.putExtra("user", mUserID.getText().toString());
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
}
