package tw.com.pcschool.instantreplysystem_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent it = getIntent();
        Bundle b = it.getExtras();

        TextView tv9 = (TextView) findViewById(R.id.textView9);
        TextView tv12 = (TextView) findViewById(R.id.textView12);
        TextView tv13 = (TextView) findViewById(R.id.textView13);
        TextView tv14 = (TextView) findViewById(R.id.textView14);
        TextView tv15 = (TextView) findViewById(R.id.textView15);


        tv9.setText(b.getString("ShopName"));
        tv12.setText(b.getString("Addr"));
        tv13.setText(b.getString("ContactPerson"));
        tv14.setText(b.getString("Tel"));
        tv15.setText(b.getString("Remark"));
        //===================
        // 設定連結
        //txt_web.setAutoLinkMask(Linkify.WEB_URLS);
        //tv14.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        //txt_email.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
        //txt_all.setAutoLinkMask(Linkify.ALL);
        // 如果要生效,那麼加入以下代碼
        //txt_web.setMovementMethod(LinkMovementMethod.getInstance());
       // tv14.setMovementMethod(LinkMovementMethod.getInstance());
        //txt_email.setMovementMethod(LinkMovementMethod.getInstance());
       // txt_all.setMovementMethod(LinkMovementMethod.getInstance());
        //====================
        wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("https://www.google.com.tw/maps/place/"+b.getString("Addr"));

    }
}
