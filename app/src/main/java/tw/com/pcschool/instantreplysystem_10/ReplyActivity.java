package tw.com.pcschool.instantreplysystem_10;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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
    String bcsn = "";
    String tno = "";
    EditText et_SN;

    String DBName="irs_db21.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        et_Remark = (EditText) findViewById(R.id.R_Remark);
        st_Remark = et_Remark.getText().toString();
       ImageView iv = (ImageView) findViewById(R.id.R_Signature);
       Intent it=getIntent();
       bcsn=it.getStringExtra("BC");
       tno=it.getStringExtra("TO");
       db = openOrCreateDatabase(DBName, 0, null);
       if(bcsn!="" && bcsn!=null) {
           db.execSQL("update reply_tb set SN='" + bcsn + "' where TaskNo='" + tno + "'");
           Cursor cs = db.rawQuery("select  SN from reply_tb where TaskNo='" + tno + "'", null);
           if (cs.getCount() != 0) {
               cs.moveToPosition(0);
               et_SN = (EditText) findViewById(R.id.R_SN);
               et_SN.setText(cs.getString(cs.getColumnIndex("SN")));
           }
       }

       //iv.setImageBitmap(null);
       //iv.destroyDrawingCache();
//======================
/*
        File root = Environment.getExternalStorageDirectory();
        String pathToMyAttachedFile = "irs/"+tono+ ".jpg";
        File file = new File(root, pathToMyAttachedFile);
        if (!file.exists() || !file.canRead()) {
            return;
        }
        Log.d("SSS","AA"+tono);
        //========================
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bm = BitmapFactory.decodeFile(file.toString(), options);
        iv.setImageBitmap(bm);
        */
        //=====================
        //ByteArrayOutputStream os = new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
        //Bitmap bm = BitmapFactory.decodeFile(file);
        //=======================

       /*CheckBox cb = (CheckBox) findViewById(R.id.R_isComp);
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
        */

       // db = openOrCreateDatabase(DBName, 0, null);
        Cursor cursor = db.rawQuery("select  notice_tb.TaskNo,ShopName,ArrivalTime,Coordinate,Addr," +
                "Tel,ContactPerson,SN from notice_tb,reply_tb " +
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
            TextView et_arrivalTime = (TextView) findViewById(R.id.R_ArrivalTime);
            et_arrivalTime.setText(cursor.getString(cursor.getColumnIndex("ArrivalTime")));
            et_SN = (EditText) findViewById(R.id.R_SN);
            et_SN.setText(cursor.getString(cursor.getColumnIndex("SN")));

            TextView et_comptime = (TextView) findViewById(R.id.R_CompTime);
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            comptime = sDateFormat.format(new java.util.Date());
            et_comptime.setText(comptime);
            tono = cursor.getString(cursor.getColumnIndex("TaskNo"));
            //=========================================================
            //et_SN = (EditText) findViewById(R.id.R_SN);
           // et_SN.setText(cursor.getString(cursor.getColumnIndex("SN")));
           // db.execSQL("update reply_tb set SN='"+bcsn+"' where TaskNo='"+tono+"'");
            //=====================
            File root = Environment.getExternalStorageDirectory();
            String pathToMyAttachedFile = "irs/"+tono+ ".jpg";
            File file = new File(root, pathToMyAttachedFile);
            if (!file.exists() || !file.canRead()) {
                return;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap bm = BitmapFactory.decodeFile(file.toString(), options);
            iv.setImageBitmap(bm);
            //======================

        }else{
            Toast.makeText(ReplyActivity.this,"本日已無待作業案件", Toast.LENGTH_SHORT).show();
            db.close();
            //Intent it = new Intent(ReplyActivity.this, MainActivity.class);
           // startActivity(it);
            ReplyActivity.this.finish();
        }
    }
    public void barcode_click(View v) {
        //=======================
        Intent intent = new Intent("MainActivity.intent.action.Launch");
       // Bundle extras=new Bundle();
       // extras.putString("TN",tono);
       intent.putExtra("TN",tono);
        startActivity(intent);
        ReplyActivity.this.finish();
        //=================
        // Intent it = new Intent(ReplyActivity.this, BarcodeActivity.class);
        // startActivity(it);
       // Toast.makeText(ReplyActivity.this,"此為專業版功能，請洽本公司", Toast.LENGTH_LONG).show();
       // Intent it = new Intent();
        //it.setAction(Intent.ACTION_VIEW);
       // startActivity(it);
        /*======================
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(com.edwardvanraak.materialbarcodescannerexample,com.edwardvanraak.materialbarcodescannerexample.MainActivity);
        intent.setComponent(cn);
        startActivity(intent);
        //======================*/
      /*  Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(
                                "com.edwardvanraak.materialbarcodescannerexample",
                                "com.edwardvanraak.materialbarcodescannerexample.MainActivity"
                        ));
                Bundle bundle = new Bundle();
        bundle.putString("msg", "this message is from project B ");
        intent.putExtras(bundle);

        intent.putExtra("pid", android.os.Process.myPid());

        startActivityForResult(intent, 1);*/
        //========================
    }
    public void sign_click(View v) {
        // Intent it = new Intent(ReplyActivity.this, signActivity.class);
        // startActivity(it);
        //Toast.makeText(ReplyActivity.this,"此為專業版功能，請洽本公司", Toast.LENGTH_LONG).show();
       // Intent it = new Intent();
       // it.setAction(Intent.ACTION_VIEW);
       // startActivity(it);
        //===========================
        Intent intent = new Intent("MainActivity2.intent.action.Launch");
        //Bundle extras=new Bundle();
        //extras.putString("TN",tono);
        intent.putExtra("TN",tono);
        startActivity(intent);
        ReplyActivity.this.finish();
        //=================
    }
    public void end_click(View v) {
            db.execSQL("update reply_tb set "+
                    "CompTime='"+comptime+"',isComp='Y',"+
                    "Remark='"+st_Remark+"',ExpDate=datetime('now','localtime'),CompDate=date('now')"+
                    " where TaskNo='"+tono+"'");
            db.execSQL("update notice_tb set isComp='Y' where TaskNo='"+tono+"'");
        //--------------------------------------
        //TaskNo,ArrivalTime,CompTime,Coordinate,isComp,Remark,SN,Signature,ExpDate,CompDate
        String rtn_data="";
        Cursor cursor = db.rawQuery("select * from reply_tb where TaskNo='"+tono+"'", null);
       // if (cursor.getCount() != 0) {
            cursor.moveToPosition(0);

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
          //取得內部儲存體擺放暫存檔案的目錄
        //預設擺放路徑為 /data/data/[package.name]/cache/
        //===============================
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED) ) {
            try {
                //============================
          /* Request user permissions in runtime*/
            int permissionW = ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionR = ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionW != PackageManager.PERMISSION_GRANTED ||
                permissionR != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ReplyActivity.this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        100);
            }
           /*Request user permissions in runtime */
                //==============================
                //取得SD卡路徑
                File SDCardpath = Environment.getExternalStorageDirectory();
               // File DataPath = new File(SDCardpath.getAbsolutePath() + "/irs");
                File DataPath = new File( SDCardpath.getParent() + "/" + SDCardpath.getName() + "/irs" );
                if (!DataPath.exists()) DataPath.mkdirs();
                //將資料寫入到SD卡
                FileWriter myFile = new FileWriter(SDCardpath.getParent()+ "/" + SDCardpath.getName() + "/irs/" + tono + ".csv");
                myFile.write(rtn_data);
                myFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //=======START寄送郵件==============================
        // 傳送郵件(Send mail)
        //------------------------
        String mailadd="";
        cursor = db.rawQuery("select ReplyMail from email_tb", null);
        if (cursor.getCount() != 0) {
            cursor.moveToPosition(0);
            mailadd = cursor.getString(cursor.getColumnIndex("ReplyMail"));
        }
        Intent intent = new Intent();
       // intent.setAction(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        String[] to = {mailadd};
        //String[] to = {"phil.huang100@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        //intent.putExtra(Intent.EXTRA_EMAIL, mailadd);
        intent.putExtra(Intent.EXTRA_SUBJECT, "派工單："+tono+" 回報訊息");
        intent.putExtra(Intent.EXTRA_TEXT, "派工單；"+tono+"\n"+
                                             "完成時間："+comptime+"\n"+
                                             "作業說明："+st_Remark);
            //==================
        ArrayList<Uri> uris = new ArrayList<Uri>();
        File root = Environment.getExternalStorageDirectory();
        String pathToMyAttachedFile = "irs/"+tono+ ".csv";
        File file = new File(root, pathToMyAttachedFile);
        if (!file.exists() || !file.canRead()) {
             return;
         }
        intent.setType("text/csv");
        Uri uri = Uri.fromFile(file);
        uris.add(uri);
        //==============================
        String pathToMyAttachedFile2 = "irs/"+tono+ ".jpg";
        File file2 = new File(root, pathToMyAttachedFile2);
        if (!file2.exists() || !file2.canRead()) {
            return;
        }
        intent.setType("image/jpeg");
        Uri uri2 = Uri.fromFile(file2);
        uris.add(uri2);
       //==================
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        startActivity(Intent.createChooser(intent, "Pick an Email provider"));
        Toast.makeText(ReplyActivity.this,"已啟動郵件，準備傳送", Toast.LENGTH_SHORT).show();


        //=======END寄送郵件=============================
        db.close();
        ReplyActivity.this.finish();

    }
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0
                        || grantResults[0] == PackageManager.PERMISSION_GRANTED
                        || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ReplyActivity.this, "允許後請再一次案結案鈕", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ReplyActivity.this, "無法取得寄送檔案權限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
        }
    }

}
