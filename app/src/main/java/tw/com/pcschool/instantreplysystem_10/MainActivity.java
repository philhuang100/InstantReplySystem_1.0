package tw.com.pcschool.instantreplysystem_10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_my_dialog);
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
