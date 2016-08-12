package tw.com.pcschool.instantreplysystem_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("https://www.google.com.tw/maps/place/"+b.getString("Addr"));

    }
}
