package tw.com.pcschool.instantreplysystem_10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db=null;
    String CREATE_TABLE1="CREATE TABLE if not exists notice_tb"+
            "(id INTEGER PRIMARY KEY autoincrement,"+
            "TaskNo TEXT,ShopName TEXT,Addr TEXT,ContactPerson TEXT,Tel TEXT,Remark TEXT)";
    String CREATE_TABLE2="CREATE TABLE if not exists reply_tb"+
            "(id INTEGER PRIMARY KEY autoincrement,"+
            "TaskNo TEXT,ArrivalTime TEXT,CompTime TEXT,Coordinate TEXT,isCompl TEXT,Remark TEXT"+
            "SN TEXT,Signature TEXT)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_my_dialog);
        //DB
        db=openOrCreateDatabase("irs.db",0,null);
        db.execSQL(CREATE_TABLE1);db.execSQL(CREATE_TABLE2);
        //DB
        // 取得自定义View
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
        setContentView(R.layout.activity_main);

    }

    public void importClick(View v)
    {
        FileOutputStream out = null;
        try {
            //在 getFilesDir() 目錄底下建立 test.txt 檔案用來進行寫入
            out = openFileOutput("test.txt", Context.MODE_PRIVATE);

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
            in = openFileInput("test.txt");

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
        Toast.makeText(MainActivity.this,data, Toast.LENGTH_SHORT).show();

        // Intent it = new Intent(MainActivity.this, ImportActivity.class);
       // startActivity(it);
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
    public void click2(View v)
    {
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
    }
}
