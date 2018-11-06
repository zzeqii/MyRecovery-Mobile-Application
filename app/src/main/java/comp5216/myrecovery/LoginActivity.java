package comp5216.myrecovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mPasswordView;
    private TextView error;

    private Patient patient;
    private String testID;
    private boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPasswordView = (EditText) findViewById(R.id.password);
        mUsernameView = (EditText) findViewById(R.id.username);
        error = (TextView) findViewById(R.id.textView3);
    }

    public void onSignIn(View v){
        String tempUser = mUsernameView.getText().toString();
        String tempPass = mPasswordView.getText().toString();

        testID = tempUser;

        checkExistence();

        if(!check){
            error.setText("ID doesn't exist");
        }
        else {
            getPatient();
            if ((patient.getPassword() != null) && (tempPass.equals(patient.getPassword()))) {
                Intent data = new Intent(LoginActivity.this, MainActivity.class);
                data.putExtra("user", tempUser);
                startActivity(data);
            } else {
                error.setText("Password doesn't match!");
            }
        }
    }

    public void onSignUp(View v){
        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    public void checkExistence(){
        Thread t = new Thread() {
            public void run() {
                try {
                    check = DataMgt.checkExists(testID);
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
