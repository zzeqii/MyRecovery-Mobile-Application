package comp5216.myrecovery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class ReportMood extends AppCompatActivity {

    private ArrayList<String> listFeeling;
    private ArrayList<Integer> listEmoji;

    private String testID;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent homePage = new Intent(ReportMood.this, MainActivity.class);
                    homePage.putExtra("user",testID);
                    startActivity(homePage);
                    break;
                case R.id.navigation_checklist:
                    Intent checklistPage = new Intent(ReportMood.this, ChecklistActivity.class);
                    checklistPage.putExtra("user",testID);
                    startActivity(checklistPage);
                    break;
                case R.id.navigation_history:
                    Intent historyPage = new Intent(ReportMood.this, HistoryActivity.class);
                    historyPage.putExtra("user",testID);
                    startActivity(historyPage);
                    break;
                case R.id.navigation_report:
                    return true;
                case R.id.navigation_more:
                    Intent morePage = new Intent(ReportMood.this, MoreActivity.class);
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
        setContentView(R.layout.activity_report_mood);

        testID = getIntent().getStringExtra("user");

        prepareList();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bot_nav);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_report);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,listFeeling,listEmoji));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String mood = "";
                if(position == 0) mood = "Happy";
                if(position == 1) mood = "Bored";
                if(position == 2) mood = "Depressed";
                if(position == 3) mood = "Sad";
                if(position == 4) mood = "Sick";
                if(position == 5) mood = "Sleepy";
                if(position == 6) mood = "Shocked";
                if(position == 7) mood = "Scared";
                if(position == 8) mood = "Angry";

                Intent data = new Intent();
                data.putExtra("mood", mood);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    public void prepareList()
    {
        listFeeling = new ArrayList<String>();

        listFeeling.add("Happy");
        listFeeling.add("Bored");
        listFeeling.add("Depressed");
        listFeeling.add("Sad");
        listFeeling.add("Sick");
        listFeeling.add("Sleepy");
        listFeeling.add("Shocked");
        listFeeling.add("Scared");
        listFeeling.add("Angry");

        listEmoji = new ArrayList<Integer>();

        listEmoji.add(R.drawable.happy);
        listEmoji.add(R.drawable.bored);
        listEmoji.add(R.drawable.depressed);
        listEmoji.add(R.drawable.sad);
        listEmoji.add(R.drawable.sick);
        listEmoji.add(R.drawable.sleepy);
        listEmoji.add(R.drawable.shocked);
        listEmoji.add(R.drawable.scare);
        listEmoji.add(R.drawable.angry);
    }
}
