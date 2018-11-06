package comp5216.myrecovery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private String testID;
    private Patient patient;

    ListView lv, lv2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homePage = new Intent(HistoryActivity.this, MainActivity.class);
                    homePage.putExtra("user",testID);
                    startActivity(homePage);
                    break;
                case R.id.navigation_checklist:
                    Intent checklistPage = new Intent(HistoryActivity.this, ChecklistActivity.class);
                    checklistPage.putExtra("user",testID);
                    startActivity(checklistPage);
                    break;
                case R.id.navigation_history:
                    return true;
                case R.id.navigation_report:
                    Intent reportPage = new Intent(HistoryActivity.this, ReportActivity.class);
                    reportPage.putExtra("user",testID);
                    startActivity(reportPage);
                    break;
                case R.id.navigation_more:
                    Intent morePage = new Intent(HistoryActivity.this, MoreActivity.class);
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
        setContentView(R.layout.activity_history);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        testID = getIntent().getStringExtra("user");

        lv = (ListView) findViewById(R.id.listView);
        lv2 = (ListView) findViewById(R.id.listView2);

        getPatient();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bot_nav);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_history);

        ArrayList<BarData> list = new ArrayList<BarData>();
        ArrayList<BarData> list2 = new ArrayList<BarData>();


        list.add(generateHeartRateData());
        list.add(generatePainData());
        list.add(generateStepsData());

        list2.add(generateMoodData());

        String[] strings = {"Happy","Sad","Bored","Depressed","Sick","Sleepy","Shocked","Scared","Angry"};

        ChartAdapter cda = new ChartAdapter(getApplicationContext(), list);
        ChartAdapter2 cdm = new ChartAdapter2(getApplicationContext(), list2, strings);
        lv.setAdapter(cdm);
        lv2.setAdapter(cda);
    }

    private BarData generateMoodData() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        ArrayList<Mood> temp = patient.getMoodHistory();

        int happy = 0;
        int sad = 0;
        int bored = 0;
        int depressed = 0;
        int sick = 0;
        int sleepy = 0;
        int shocked = 0;
        int scared = 0;
        int angry = 0;

        for (int i=0;i<temp.size();i++){
            if(temp.get(i).getMood().equals("Happy"))
                happy++;
            else if(temp.get(i).getMood().equals("Sad"))
                sad++;
            else if(temp.get(i).getMood().equals("Bored"))
                bored++;
            else if(temp.get(i).getMood().equals("Depressed"))
                depressed++;
            else if(temp.get(i).getMood().equals("Sick"))
                sick++;
            else if(temp.get(i).getMood().equals("Sleepy"))
                sleepy++;
            else if(temp.get(i).getMood().equals("Shocked"))
                shocked++;
            else if(temp.get(i).getMood().equals("Scared"))
                scared++;
            else if(temp.get(i).getMood().equals("Angry"))
                angry++;
        }

        entries.add(new BarEntry(0,happy));
        entries.add(new BarEntry(1,sad));
        entries.add(new BarEntry(2,bored));
        entries.add(new BarEntry(3,depressed));
        entries.add(new BarEntry(4,sick));
        entries.add(new BarEntry(5,sleepy));
        entries.add(new BarEntry(6,shocked));
        entries.add(new BarEntry(7,scared));
        entries.add(new BarEntry(8,angry));


        BarDataSet d = new BarDataSet(entries, "Mood Recorded");
        d.setColors(ColorTemplate.COLORFUL_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));


        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private BarData generateHeartRateData() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        ArrayList<HeartRate> temp = patient.getHeartRateHistory();

        for (int i=0;i<temp.size();i++){
            entries.add(new BarEntry(i, Float.parseFloat(temp.get(i).getHeartRate())));
        }

        BarDataSet d = new BarDataSet(entries, "Heart Rate Recorded");
        d.setColors(ColorTemplate.COLORFUL_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private BarData generateStepsData() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        ArrayList<Steps> temp = patient.getStepsHistory();

        int k = 0;

        for (int i=0;i<temp.size();i++){
            if(temp.get(i).getSteps() > 0) {
                entries.add(new BarEntry(i-k, temp.get(i).getSteps()));
            }else{
                k++;
            }
        }

        BarDataSet d = new BarDataSet(entries, "Steps Taken");
        d.setColors(ColorTemplate.COLORFUL_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
    }

    private BarData generatePainData() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        ArrayList<PainLevel> temp = patient.getPainHistory();

        for (int i=0;i<temp.size();i++){
            entries.add(new BarEntry(i, Float.parseFloat(temp.get(i).getLevel())));
        }

        BarDataSet d = new BarDataSet(entries, "Pain Recorded");
        d.setColors(ColorTemplate.COLORFUL_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(sets);
        cd.setBarWidth(0.9f);
        return cd;
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
