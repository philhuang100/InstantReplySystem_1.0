package tw.com.pcschool.instantreplysystem_10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ScheduleBActivity extends AppCompatActivity {
    ListView lv3;
    ArrayList<SQLiteDB> mylist;
    DBAdapterActivity adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_b);
        SQLiteDatabase db = openOrCreateDatabase("irs_db11.db", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select _id,ShopName,Addr,ContactPerson,Tel,Remark,TaskNo from notice_tb",null);
        mylist = new ArrayList<>();
        for(int i = 0 ; i < cursor.getCount() ; i++ )
        {
            //利用for迴圈切換指標位置
            cursor.moveToPosition(i);
            String ShopName = cursor.getString(cursor.getColumnIndex("ShopName"));
            String Addr = cursor.getString(cursor.getColumnIndex("Addr"));
            String ContactPerson = cursor.getString(cursor.getColumnIndex("ContactPerson"));
            String Tel = cursor.getString(cursor.getColumnIndex("Tel"));
            String Remark = cursor.getString(cursor.getColumnIndex("Remark"));
            String TaskNo = cursor.getString(cursor.getColumnIndex("TaskNo"));
            mylist.add(new SQLiteDB(ShopName.trim(), Addr, ContactPerson,Tel,Remark,TaskNo));
        }
              // if (cursor != null && cursor.getCount() >= 0) {
        lv3 = (ListView) findViewById(R.id.listView3);
        adapter = new DBAdapterActivity(ScheduleBActivity.this, mylist);

        lv3.setAdapter(adapter);
        db.close();

        /*
        lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle b = new Bundle();
                b.putString("name", mylist.get(position).name);
                b.putString("phone", mylist.get(position).phone);
                b.putInt("photo", mylist.get(position).photoid);

                Intent it = new Intent(Main3Activity.this, DetailActivity.class);
                it.putExtras(b);
                startActivity(it);
            }
        });
        */

    }
}
