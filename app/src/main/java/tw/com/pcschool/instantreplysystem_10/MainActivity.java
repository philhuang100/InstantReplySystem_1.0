package tw.com.pcschool.instantreplysystem_10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.FileOutputStream;
public class MainActivity extends AppCompatActivity {
    String replymail="";//儲存EMAIL變數
    SQLiteDatabase db = null;
    String DBName="irs_db21.db";
    //資料庫結構=====================
    String CREATE_TABLE1 = "CREATE TABLE if not exists notice_tb" +
            "(_id INTEGER PRIMARY KEY autoincrement," +
            "TaskNo TEXT UNIQUE,ShopName TEXT,Addr TEXT,ContactPerson TEXT DEFAULT ' ',Tel TEXT,"+
            "Remark TEXT DEFAULT ' ',isComp TEXT DEFAULT ' ',ImpTime TEXT,ImpDate TEXT)";
    String CREATE_TABLE2 = "CREATE TABLE if not exists reply_tb" +
            "(_id INTEGER PRIMARY KEY autoincrement," +
            "TaskNo TEXT UNIQUE,ArrivalTime TEXT DEFAULT ' ',CompTime TEXT DEFAULT ' '," +
            "Coordinate TEXT DEFAULT ' ',isComp TEXT DEFAULT ' ',Remark TEXT DEFAULT ' ',SN TEXT DEFAULT ' '," +
            "Signature TEXT DEFAULT ' ',ExpDate TEXT DEFAULT ' ',CompDate TEXT DEFAULT ' ',ImpDate TEXT)";
    String CREATE_TABLE3 = "CREATE TABLE if not exists email_tb" +
            "(_id INTEGER PRIMARY KEY autoincrement,ReplyMail TEXT)";
    //資料庫結構=====================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_my_dialog);
        //DB
        db = openOrCreateDatabase(DBName, 0, null);//DB存在則讀取，不存在則建立
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
       // Cursor cursor = db.rawQuery("select * from reply_tb where NoticeDate<date('now')" , null);
       // if (cursor.getCount() != 0) {
        db.execSQL("delete  from  reply_tb where ImpDate<date('now')");
        db.execSQL("delete  from  notice_tb where ImpDate<date('now')");
        //}

        //DB
        // 取得自定义View
      /*/帳密
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View myLoginView = layoutInflater.inflate(R.layout.activity_my_dialog, null);

        Dialog alertDialog = new AlertDialog.Builder(this).
                setTitle("使用者登入").
                // setIcon(R.drawable.ic_launcher).
                        setView(myLoginView).
                        setPositiveButton("確定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).
                        setNegativeButton("取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }).
                        create();
        alertDialog.show();
       // 帳密*/
        //start 郵件郵件轉入
        Intent intent = getIntent();
        String action = intent.getAction();
        if(Intent.ACTION_VIEW.equals(action)) {
            try {
                InputStream in = getContentResolver().openInputStream(getIntent().getData());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                StringBuffer buffer = new StringBuffer();
                String line="" ;
                int flag_1=1;
                while ((line = br.readLine()) != null){
                    //====================
                    String item[] = line.split(",");//csv文件為依據逗號切割
                   if(flag_1!=1){
                      db.execSQL("insert into notice_tb(TaskNo,ShopName,Addr,Tel,ContactPerson,Remark,ImpTime,ImpDate) values('"+
                                 item[0]+"','"+item[1]+"','"+item[2]+"','"+
                                 item[3]+"','"+item[4]+"','"+item[5]+
                                "',datetime('now','localtime'),date('now'))");
                       db.execSQL("insert into reply_tb(TaskNo,SN,ImpDate) values('"+ item[0]+"','',date('now'))");

                   }
                   flag_1=0;
                 }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //end 郵件郵件轉入
        setContentView(R.layout.activity_main);


    }

    public void importClick(View v) {
        FileOutputStream out = null;
        try {
            //在 getFilesDir() 目錄底下建立 test.txt 檔案用來進行寫入
            out = openFileOutput("test1.txt", Context.MODE_PRIVATE);

            //將資料寫入檔案中
            out.write("Hello! 大家好\n".getBytes());
            out.flush();
        } catch (Exception e) {
            ;
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                ;
            }
        }

        FileInputStream in = null;
        StringBuffer data = new StringBuffer();
        try {
            //開啟 getFilesDir() 目錄底下名稱為 test.txt 檔案
            in = openFileInput("test1.txt");

            //讀取該檔案的內容
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        } catch (Exception e) {
            ;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                ;
            }
        }
        // Toast.makeText(MainActivity.this,data, Toast.LENGTH_SHORT).show();

        // Intent it = new Intent(MainActivity.this, ImportActivity.class);
        // startActivity(it);
        db.close();
    }

    public void checkInClick(View v) {
        Intent it = new Intent(MainActivity.this, CheckInActivity.class);
        startActivity(it);
        db.close();
    }
    public void c_click(View v) {
        Intent it = new Intent(MainActivity.this, CheckInActivity.class);
        startActivity(it);
        db.close();
    }

    public void replyClick(View v) {
        Intent it = new Intent(MainActivity.this, ReplyActivity.class);
        startActivity(it);
        db.close();
    }
    public void r_click(View v) {
        Intent it = new Intent(MainActivity.this, ReplyActivity.class);
        startActivity(it);
        db.close();
    }

    public void scheduleClick(View v) {
        Intent it = new Intent(MainActivity.this, ScheduleBActivity.class);
        startActivity(it);
        db.close();
    }
    public void s_click(View v) {
        Intent it = new Intent(MainActivity.this, ScheduleBActivity.class);
        startActivity(it);
        db.close();
    }
    public void docclick(View v) {
        Intent it = new Intent(MainActivity.this, DocumentActivity.class);
        startActivity(it);
        db.close();
    }
    public void emailclick(View v) {

        db = openOrCreateDatabase(DBName, 0, null);
        Cursor cursor = db.rawQuery("select  ReplyMail from email_tb ",null );
        if (cursor.getCount() != 0) {
            cursor.moveToPosition(0);
            replymail = cursor.getString(cursor.getColumnIndex("ReplyMail"));
        }
           // Toast.makeText(MainActivity.this,"因回報案件會即時以EMAIL回覆派工單位，故須設定一組帳號"
             //       ,Toast.LENGTH_LONG).show();

            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("設定帳號，啟動回報機制");
            builder.setIcon(android.R.drawable.ic_dialog_info);
            builder.setMessage("輸入EMAIL帳號：");
            final EditText et=new EditText(MainActivity.this);
            builder.setView(et);
        Log.d("AAA","A4");
            et.setText(replymail);
        builder.setNegativeButton("忽略", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                      String str = et.getText().toString();
                      db = openOrCreateDatabase(DBName, 0, null);
                      if(replymail.equals("")) {
                       db.execSQL("insert into email_tb(ReplyMail) values('" + str + "')");
                   }else {
                        db.execSQL("update email_tb set ReplyMail='" + str + "' where ReplyMail='" + replymail + "'");
                    }
                        Toast.makeText(MainActivity.this,"帳號已設定完成",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.create().show();

        //}
        db.close();
    }

    public void click2(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("登入訊息");
        builder.setMessage("帳號");
        builder.setMessage("密碼");
        final EditText ed = new EditText(MainActivity.this);
        builder.setView(ed);


        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String str = ed.getText().toString();
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        builder.create().show();
        db.close();
    }
    //按返回鍵時詢問是否離開
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("任務完成")
                    .setMessage("工作結束，要離開了嗎?")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                              Toast.makeText(MainActivity.this, "您辛苦了!! 再見。", Toast.LENGTH_SHORT).show();
                              MainActivity.this.finish();

                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                }
                            }).show();
        }
        db.close();
        return true;
    }

}
