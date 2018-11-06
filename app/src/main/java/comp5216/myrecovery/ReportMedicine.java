package comp5216.myrecovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import java.util.ArrayList;

public class ReportMedicine extends AppCompatActivity {

    private String testID;

    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homePage = new Intent(ReportMedicine.this, MainActivity.class);
                    homePage.putExtra("user",testID);
                    startActivity(homePage);
                    break;
                case R.id.navigation_checklist:
                    Intent checklistPage = new Intent(ReportMedicine.this, ChecklistActivity.class);
                    checklistPage.putExtra("user",testID);
                    startActivity(checklistPage);
                    break;
                case R.id.navigation_history:
                    Intent historyPage = new Intent(ReportMedicine.this, HistoryActivity.class);
                    historyPage.putExtra("user",testID);
                    startActivity(historyPage);
                    break;
                case R.id.navigation_report:
                    return true;
                case R.id.navigation_more:
                    Intent morePage = new Intent(ReportMedicine.this, MoreActivity.class);
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
        setContentView(R.layout.activity_report_medicine);

        testID = getIntent().getStringExtra("user");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bot_nav);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_report);

        cb1 = (CheckBox) findViewById(R.id.medicineA);
        cb2 = (CheckBox) findViewById(R.id.medicineB);
        cb3 = (CheckBox) findViewById(R.id.medicineC);
        cb4 = (CheckBox) findViewById(R.id.medicineD);
    }

    public void onSubmit(View view) {
        // Is the view now checked?
        ArrayList<String> temp = new ArrayList<>();

        if(cb1.isChecked())
            temp.add(cb1.getText().toString());
        if(cb2.isChecked())
            temp.add(cb2.getText().toString());
        if(cb3.isChecked())
            temp.add(cb3.getText().toString());
        if(cb4.isChecked())
            temp.add(cb4.getText().toString());

        //Toast.makeText(ReportMedicine.this,""+temp.toString(),Toast.LENGTH_SHORT).show();

        Intent data = new Intent();
        data.putStringArrayListExtra("list", temp);
        setResult(RESULT_OK, data);
        finish();
    }
}
