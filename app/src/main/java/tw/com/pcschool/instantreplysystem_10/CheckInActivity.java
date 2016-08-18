package tw.com.pcschool.instantreplysystem_10;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;

//public class CheckInActivity extends AppCompatActivity  {
public class CheckInActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener
    {
    protected static final String TAG = "MainActivity";
        String DBName="irs_db20.db";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        mLatitudeLabel = "緯度";
        mLongitudeLabel = "經度";
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
        buildGoogleApiClient();
       // Toast.makeText(this,"A："+String.format("%s: %f", mLatitudeLabel, mLastLocation.getLatitude()), Toast.LENGTH_LONG).show();

    }

        protected synchronized void buildGoogleApiClient()
        {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        @Override
        protected void onStart()
        {
            super.onStart();
            mGoogleApiClient.connect();
        }

        @Override
        protected void onStop()
        {
            super.onStop();
            if (mGoogleApiClient.isConnected())
            {
                mGoogleApiClient.disconnect();
            }
        }


        // 當 GoogleApiClient 連上 Google Play Service 後要執行的動作
        @Override
        public void onConnected(Bundle connectionHint)
        {
            // 這行指令在 IDE 會出現紅線，不過仍可正常執行，可不予理會
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null)
            {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = sDateFormat.format(new java.util.Date());
                mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel, mLastLocation.getLatitude()));
                mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel, mLastLocation.getLongitude()));
                SQLiteDatabase db = openOrCreateDatabase(DBName, MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("select  notice_tb.TaskNo from notice_tb,reply_tb " +
                        "where notice_tb.TaskNo=reply_tb.TaskNo " +
                        "and (CompTime=' ' or CompTime is NULL) order by notice_tb._id ", null);
                // "and (CompTime='' or CompTime is NULL) order by notice_tb._id ", null);
                if (cursor.getCount() != 0) {
                    cursor.moveToPosition(0);
                    //TextView et_taskno = (TextView) findViewById(R.id.R_TaskNo);
                    //et_taskno.setText(cursor.getString(cursor.getColumnIndex("TaskNo")));
                    db.execSQL("update reply_tb set " +
                            "Coordinate='" + mLastLocation.getLatitude() + " "+mLastLocation.getLongitude() + "'," +
                            "ArrivalTime='" + date + "' where "+
                            "TaskNo='" +cursor.getString(cursor.getColumnIndex("TaskNo")) + "'");
                    Toast.makeText(this,"您目前的位置"+"\n"+mLatitudeText.getText()+"\n"
                                    +mLongitudeText.getText()+"\n"+"現在時間："+date,
                            Toast.LENGTH_LONG).show();

                    db.close();
                    CheckInActivity.this.finish();
                }else{
                    Toast.makeText(CheckInActivity.this, "已無案件，無需打卡", Toast.LENGTH_SHORT).show();
                    db.close();
                    CheckInActivity.this.finish();
                }
            }
            else
            {
                Toast.makeText(this, "偵測不到定位，請確認定位功能已開啟。", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onConnectionFailed(ConnectionResult result)
        {
            Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
        }


        @Override
        public void onConnectionSuspended(int cause)
        {
            Log.i(TAG, "Connection suspended");
            mGoogleApiClient.connect();
        }

    }
