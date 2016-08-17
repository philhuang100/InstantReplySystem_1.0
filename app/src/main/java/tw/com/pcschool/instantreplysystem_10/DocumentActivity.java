package tw.com.pcschool.instantreplysystem_10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText("操作說明\n" +
                "一.準備派工檔：\n" +
                "1.檔案須為CSV格式，編碼為UTF-8\n" +
                "2.什麼是CSV：就是以逗號分隔欄位的文字資料，" +
                "且第一列需有表頭，EXCEL即可另存為CSV\n" +
                "3.檔案內容編排格式如下：\n" +
                "單號,店名,地此,聯絡人,電話,備註\n"+
                "xxxx,xxxx,xxxx,xxxxxx,xxxx,xxxx\n"

                );
    }
}
