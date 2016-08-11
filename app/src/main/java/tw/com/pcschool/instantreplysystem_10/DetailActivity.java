package tw.com.pcschool.instantreplysystem_10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent it = getIntent();
        Bundle b = it.getExtras();

        TextView tv3 = (TextView) findViewById(R.id.textView3);
        TextView tv4 = (TextView) findViewById(R.id.textView4);
        TextView tv10 = (TextView) findViewById(R.id.textView10);
        TextView tv11 = (TextView) findViewById(R.id.textView11);


        tv3.setText(b.getString("ShopName"));
        tv4.setText(b.getString("Addr"));
        tv10.setText(b.getString("ContactPerson"));
        tv11.setText(b.getString("Tel"));


    }
}
