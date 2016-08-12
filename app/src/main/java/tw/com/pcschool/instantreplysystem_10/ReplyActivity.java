package tw.com.pcschool.instantreplysystem_10;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ReplyActivity extends AppCompatActivity {
    SQLiteDatabase db = null;
    EditText et_Remark;
    String st_Remark;
    String ckComp = "";
    String tono = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        et_Remark = (EditText) findViewById(R.id.R_Remark);
        st_Remark = et_Remark.getText().toString();
        CheckBox cb = (CheckBox) findViewById(R.id.R_isComp);
        ckComp = "";
        if (cb.isChecked() == true)
            ckComp = "Y";
        db = openOrCreateDatabase("irs_db11.db", 0, null);
        Cursor cursor = db.rawQuery("select TOP 1 TaskNo,ShopName,ArrivalTime,Coordinate " +
                "from notice_tb,reply_tb " +
                "where notice_tb.TaskNo=reply_tb.TaskNo " +
                "and (CompTime='' or CompTime is NULL) order by _id", null);
        TextView et_taskno=  (TextView) findViewById(R.id.R_TaskNo);
        et_taskno.setText(cursor.getString(cursor.getColumnIndex("TaskNo")));
        TextView et_shopname=  (TextView) findViewById(R.id.R_ShopName);

    }
    public void end_click(View v) {
            db.execSQL("update reply_tb set "+
                    "CompTime=datetime('now','localtime')"+",isComp='"+ckComp+"',"+
                    "Remark='"+st_Remark+"',ExpDate=datetime('now','localtime')"+
                    "where TaskNo='"+tono+"'");
        db.close();
    }


}
