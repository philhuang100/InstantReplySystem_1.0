package tw.com.pcschool.instantreplysystem_10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class DocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv.setText("FAST派修通操作說明\n\n" +
                "一.準備派工檔：\n" +
                "1.檔案須為CSV格式。\n" +
                "2.什麼是CSV：就是以逗號分隔欄位的文字資料，" +
                "且第一列需有表頭，EXCEL即可另存為CSV。\n" +
                "3.檔案內容編排格式如下：\n" +
                "單號,店名,地此,聯絡人,電話,備註\n"+
                "xxxx,xxxx,xxxx,xxxxxx,xxxx,xxxx\n"+
                "(xxxx表示資料內容，可多筆)。\n\n"+
                "二.執行派工EMAL：\n"+
                "1.將CSV檔以EMAIL寄給案件作業人員。\n" +
                "2.案件作業人員收取EMAIL後，點選附件(*.CSV)，選擇以FAST派修通開啟，"+
                "則派工資料將匯入APP中。\n\n"+
                "三.排程規劃：\n"+
                "1.點選FAST派修通之排程規劃。\n"+
                "2.顯示匯入之案件清單，案件作業順序已定義由上而下。\n"+
                "3.此功能可顯示店家位置圖。\n\n"+
                "四.到場打卡：\n"+
                "1.抵達商店現場，須點選到場打卡。\n"+
                "2.到場打卡的用途在記錄工程師的到場時間及位置(經緯度)。\n\n"+
                "五.結案回報：\n"+
                "1.作業完成點選結案回報。\n"+
                "2.結案回報在記錄相關回報資訊，提供條碼掃描功能，以及客戶簽名板功能。\n"+
                "3.按下結案鈕，即完成結案。\n\n"+
                "六.回報EMAL：\n"+
                "1.按下結案鈕後，系統會產生回報檔(*.CSV)及簽名檔(*.JPG)，"+
                  "且自動產生回報EMAIL，並附此二檔。\n"+
                "2.回報EMAIL的收件地址需先設定，可由首頁右下角圖示點選設定。\n\n"+
                "七.技術服務：\n"+
                "1.如需進一步說明，請聯絡：\n"+
                "phil.huang100@gmail.com \n\n"+
                "請不吝提供改善建議，謝謝!!"
                );


    }
}
