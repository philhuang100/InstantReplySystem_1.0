package tw.com.pcschool.instantreplysystem_10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReplyActivity extends AppCompatActivity {
    SQLiteDatabase db = null;
    EditText et_Remark;
    String st_Remark;
    String ckComp = "";
    String tono = "";
    String comptime = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        et_Remark = (EditText) findViewById(R.id.R_Remark);
        st_Remark = et_Remark.getText().toString();
        CheckBox cb = (CheckBox) findViewById(R.id.R_isComp);
        //=======================
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked)
                    ckComp="Y";

            }
        });
        //====================

        db = openOrCreateDatabase("irs_db02.db", 0, null);
        Cursor cursor = db.rawQuery("select  notice_tb.TaskNo,ShopName,ArrivalTime,Coordinate,Addr " +
                "from notice_tb,reply_tb " +
                "where notice_tb.TaskNo=reply_tb.TaskNo " +
                "and (CompTime='' or CompTime is NULL) order by notice_tb._id ", null);
        if (cursor.getCount() != 0) {
            cursor.moveToPosition(0);
            TextView et_taskno = (TextView) findViewById(R.id.R_TaskNo);
            et_taskno.setText(cursor.getString(cursor.getColumnIndex("TaskNo")));
            TextView et_shopname = (TextView) findViewById(R.id.R_ShopName);
            et_shopname.setText(cursor.getString(cursor.getColumnIndex("ShopName")));
            TextView et_addr = (TextView) findViewById(R.id.R_Addr);
            et_addr.setText(cursor.getString(cursor.getColumnIndex("Addr")));
            TextView et_comptime = (TextView) findViewById(R.id.R_CompTime);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            comptime = sDateFormat.format(new java.util.Date());
            et_comptime.setText(comptime);
            tono = cursor.getString(cursor.getColumnIndex("TaskNo"));
        }else{
            Toast.makeText(ReplyActivity.this,"本日派工已全部完成", Toast.LENGTH_SHORT).show();
            db.close();
            Intent it = new Intent(ReplyActivity.this, MainActivity.class);
            startActivity(it);
        }
    }
    public void end_click(View v) {
            db.execSQL("update reply_tb set "+
                    "CompTime='"+comptime+"',isComp='"+ckComp+"',"+
                    "Remark='"+st_Remark+"',ExpDate=datetime('now','localtime')"+
                    " where TaskNo='"+tono+"'");
            db.execSQL("update notice_tb set isComp='"+ckComp+"' where TaskNo='"+tono+"'");
        //=======START寄送郵件==============================
        // 傳送郵件(Send mail)
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:phil.huang100@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "這裡是主旨。");
        intent.putExtra(Intent.EXTRA_TEXT, "這是本文內容。");
        startActivity(intent);
 /*
        // 傳送附件(Send mail with enclosed file)
        //和傳送郵件不同的利用Intent.ACTION_SEND，
        //而附件檔案必須利用Uri型別指定。

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        String[] to = {"phil.huang100@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "這是主旨。");
        intent.putExtra(Intent.EXTRA_TEXT, "這是本文內容。");

        // 選擇一張圖片來加入附件
        // 取得的SD卡資料夾
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "test.jpg");
        intent.putExtra(Intent.EXTRA_STREAM,
                Uri.parse("file://"+ file.getAbsolutePath()));
        intent.setType("image/jpeg");
        //呼叫應用程式列表
        startActivity(Intent.createChooser(intent,"請選擇郵件應用程式，例如GMAIL"));
        */
        //=======END寄送郵件=============================
        db.close();
        Intent it = new Intent(ReplyActivity.this, MainActivity.class);
        startActivity(it);
    }


}
