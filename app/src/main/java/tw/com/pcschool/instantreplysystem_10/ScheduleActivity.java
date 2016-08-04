package tw.com.pcschool.instantreplysystem_10;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class ScheduleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ListView lv1 = (ListView) findViewById(R.id.listView);
        SQLiteDatabase db = openOrCreateDatabase("irs1.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select _id, address, tel from notice_tb",null);
        if (cursor != null && cursor.getCount() >= 0) {
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[]{"address", "tel"}, new int[]{android.R.id.text1, android.R.id.text2}, 0);
            lv1.setAdapter(adapter);

        }

    }
}
