package tw.com.pcschool.instantreplysystem_10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScheduleBActivity extends AppCompatActivity {
    ListView lv3;
    ArrayList<SQLiteDB> mylist;
    DBAdapterActivity adapter;
    String DBName="irs_db10.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_b);
        SQLiteDatabase db = openOrCreateDatabase(DBName, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select _id,ShopName,Addr,isComp,Tel,ContactPerson,Remark,TaskNo from notice_tb", null);
        if (cursor.getCount() != 0) {
            mylist = new ArrayList<>();
            for (int i = 0; i < cursor.getCount(); i++) {
                //利用for迴圈切換指標位置
                cursor.moveToPosition(i);
                String ShopName = cursor.getString(cursor.getColumnIndex("ShopName"));
                String Addr = cursor.getString(cursor.getColumnIndex("Addr"));
                String isComp = cursor.getString(cursor.getColumnIndex("isComp"));
                String ContactPerson = cursor.getString(cursor.getColumnIndex("ContactPerson"));
                String Tel = cursor.getString(cursor.getColumnIndex("Tel"));
                String Remark = cursor.getString(cursor.getColumnIndex("Remark"));
                String TaskNo = cursor.getString(cursor.getColumnIndex("TaskNo"));
                mylist.add(new SQLiteDB(ShopName, Addr, isComp, Tel, ContactPerson, Remark,TaskNo));
            }
            // if (cursor != null && cursor.getCount() >= 0) {
            lv3 = (ListView) findViewById(R.id.listView3);
            adapter = new DBAdapterActivity(ScheduleBActivity.this, mylist);

            lv3.setAdapter(adapter);
            db.close();

            //==========================
            lv3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Intent it = new Intent(ScheduleBActivity.this, DetailActivity.class);
                    Bundle b = new Bundle();
                    //b.putString("ShopName", data.get(p).ShopName);
                    // b.putString("Addr", data.get(p).Addr);
                    b.putString("ShopName", mylist.get(position).ShopName);
                    b.putString("Addr", mylist.get(position).Addr);
                    b.putString("Tel", mylist.get(position).Tel);
                    b.putString("ContactPerson", mylist.get(position).ContactPerson);
                    b.putString("Remark", mylist.get(position).Remark);
                    Intent it = new Intent(ScheduleBActivity.this, DetailActivity.class);
                    it.putExtras(b);
                    startActivity(it);

                }
            });
            //=========================
        } else {
            Toast.makeText(ScheduleBActivity.this, "本日無派工或尚未轉入", Toast.LENGTH_SHORT).show();
            db.close();
            // Intent it = new Intent(ScheduleBActivity.this, MainActivity.class);
            //  startActivity(it);
            ScheduleBActivity.this.finish();
        }
    }
}
