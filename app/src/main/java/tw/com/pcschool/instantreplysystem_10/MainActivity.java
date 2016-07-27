package tw.com.pcschool.instantreplysystem_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void importClick(View v)
    {
        Intent it = new Intent(MainActivity.this, ImportActivity.class);
        startActivity(it);
    }
    public void checkInClick(View v)
    {
        Intent it = new Intent(MainActivity.this, CheckInActivity.class);
        startActivity(it);
    }
    public void replyClick(View v)
    {
        Intent it = new Intent(MainActivity.this, ReplyActivity.class);
        startActivity(it);
    }
    public void scheduleClick(View v)
    {
        Intent it = new Intent(MainActivity.this, ScheduleActivity.class);
        startActivity(it);
    }
}
