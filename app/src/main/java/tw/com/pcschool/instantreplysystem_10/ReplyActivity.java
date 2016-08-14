package tw.com.pcschool.instantreplysystem_10;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        db = openOrCreateDatabase("irs_db04.db", 0, null);
        Cursor cursor = db.rawQuery("select  notice_tb.TaskNo,ShopName,ArrivalTime,Coordinate,Addr," +
                "Tel,ContactPerson from notice_tb,reply_tb " +
                "where notice_tb.TaskNo=reply_tb.TaskNo " +
                "and (CompTime=' ' or CompTime is NULL) order by notice_tb._id ", null);
                // "and (CompTime='' or CompTime is NULL) order by notice_tb._id ", null);
        if (cursor.getCount() != 0) {
            cursor.moveToPosition(0);
            TextView et_taskno = (TextView) findViewById(R.id.R_TaskNo);
            et_taskno.setText(cursor.getString(cursor.getColumnIndex("TaskNo")));
            TextView et_shopname = (TextView) findViewById(R.id.R_ShopName);
            et_shopname.setText(cursor.getString(cursor.getColumnIndex("ShopName")));
            TextView et_addr = (TextView) findViewById(R.id.R_Addr);
            et_addr.setText(cursor.getString(cursor.getColumnIndex("Addr")));
            TextView et_contactperson = (TextView) findViewById(R.id.R_ContactPerson);
            et_contactperson.setText(cursor.getString(cursor.getColumnIndex("ContactPerson")));
            TextView et_tel = (TextView) findViewById(R.id.R_Tel);
            et_tel.setText(cursor.getString(cursor.getColumnIndex("Tel")));


            TextView et_comptime = (TextView) findViewById(R.id.R_CompTime);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            comptime = sDateFormat.format(new java.util.Date());
            et_comptime.setText(comptime);
            tono = cursor.getString(cursor.getColumnIndex("TaskNo"));
        }else{
            Toast.makeText(ReplyActivity.this,"本日已無待作業案件", Toast.LENGTH_SHORT).show();
            db.close();
            //Intent it = new Intent(ReplyActivity.this, MainActivity.class);
           // startActivity(it);
            ReplyActivity.this.finish();
        }
    }
    public void barcodeclick(View v) {
        Intent it = new Intent(ReplyActivity.this, BarcodeActivity.class);
        startActivity(it);
    }
    public void signlick(View v) {
        Intent it = new Intent(ReplyActivity.this, signActivity.class);
        startActivity(it);
    }
    public void end_click(View v) {
            db.execSQL("update reply_tb set "+
                    "CompTime='"+comptime+"',isComp='"+ckComp+"',"+
                    "Remark='"+st_Remark+"',ExpDate=datetime('now','localtime'),CompDate=date('now')"+
                    " where TaskNo='"+tono+"'");
            db.execSQL("update notice_tb set isComp='"+ckComp+"' where TaskNo='"+tono+"'");
        //--------------------------------------
        //TaskNo,ArrivalTime,CompTime,Coordinate,isComp,Remark,SN,Signature,ExpDate,CompDate
        String rtn_data="";
        Cursor cursor = db.rawQuery("select * from reply_tb where TaskNo='"+tono+"'", null);
       // if (cursor.getCount() != 0) {
            cursor.moveToPosition(0);
        //Log.d("DDDD",cursor.getString(cursor.getColumnIndex("TaskNo")));
             rtn_data = cursor.getString(cursor.getColumnIndex("TaskNo")) + "," +
                    cursor.getString(cursor.getColumnIndex("ArrivalTime")) + "," +
                    cursor.getString(cursor.getColumnIndex("CompTime")) + "," +
                    cursor.getString(cursor.getColumnIndex("Coordinate")) + "," +
                    cursor.getString(cursor.getColumnIndex("isComp")) + "," +
                    cursor.getString(cursor.getColumnIndex("Remark")) + "," +
                    cursor.getString(cursor.getColumnIndex("SN")) + "," +
                    cursor.getString(cursor.getColumnIndex("Signature")) + "," +
                    cursor.getString(cursor.getColumnIndex("ExpDate")) + "," +
                    cursor.getString(cursor.getColumnIndex("CompDate"));
      //  }
        Log.d("EEEE","222"+rtn_data);
        //取得內部儲存體擺放暫存檔案的目錄
        //預設擺放路徑為 /data/data/[package.name]/cache/
        File dir = getCacheDir();
         File outFile = new File(dir,tono+ ".csv");

        //也可以使用 File.createTempFile() 來建立暫存檔案
        //File outFile2 = File.createTempFile("test", ".txt", dir);
       //將資料寫入檔案中，若 package name 為 com.myapp
       //就會產生 /data/data/com.myapp/cache/test.txt 檔案

       FileOutputStream osw = null;
       try {
           osw = new FileOutputStream(outFile);
           osw.write(rtn_data.getBytes());
           osw.flush();
       } catch (Exception e) {
                ;
       } finally {
       try {
            osw.close();
          } catch (Exception e) {
                    ;
          }
       }
       //----------------------------------
        //=======START寄送郵件==============================
        // 傳送郵件(Send mail)
       /*
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:phil.huang100@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "派工單："+tono+" 回報訊息");
        intent.putExtra(Intent.EXTRA_TEXT, "派工單；"+tono+"， 完成時間："+comptime);
        */
        //------------------------
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        String[] to = {"phil.huang100@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "這是主旨。");
        intent.putExtra(Intent.EXTRA_TEXT, "這是本文內容。");
        // 選擇一張圖片來加入附件
        // 取得的SD卡資料夾
        //File sdcard = Environment.getExternalStorageDirectory();
       // File file = new File(sdcard, "test.jpg");
        //intent.putExtra(Intent.EXTRA_STREAM,
         //       Uri.parse("file://"+ file.getAbsolutePath()));

        intent.putExtra(Intent.EXTRA_STREAM,Uri.parse("file://"+ outFile.toString()));
        intent.setType("text/csv");

        //呼叫應用程式列表
        startActivity(Intent.createChooser(intent,"請選擇郵件應用程式，例如GMAIL"));
        //---------------------------
        try{
            startActivity(intent);
            Toast.makeText(ReplyActivity.this,"已啟動郵件，準備傳送", Toast.LENGTH_SHORT).show();
        }catch (ActivityNotFoundException e){
            Toast.makeText(ReplyActivity.this,"傳送失敗", Toast.LENGTH_SHORT).show();
        }


       /* Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:phil.huang100@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "這裡是主旨。");
        intent.putExtra(Intent.EXTRA_TEXT, "這是本文內容。");
        startActivity(intent);
 */
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
        //Intent it = new Intent(ReplyActivity.this, MainActivity.class);
       //startActivity(it);
        ReplyActivity.this.finish();
    }


}
