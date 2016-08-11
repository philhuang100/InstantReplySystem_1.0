package tw.com.pcschool.instantreplysystem_10;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DBAdapterActivity extends BaseAdapter {
    Context context;
    ArrayList<SQLiteDB> data;
    boolean chk[];
    LayoutInflater inflater;

    public DBAdapterActivity(Context c, ArrayList<SQLiteDB> list) {
        context = c;
        inflater = LayoutInflater.from(context);
        data = list;
        chk = new boolean[data.size()];
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int p = position;


        ViewHolder holder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.activity_list_item, null);
            holder = new ViewHolder();
            holder.tv1 = (TextView) convertView.findViewById(R.id.textView3);
            holder.tv2 = (TextView) convertView.findViewById(R.id.textView4);
            holder.tv3 = (TextView) convertView.findViewById(R.id.textView10);
            holder.tv4 = (TextView) convertView.findViewById(R.id.textView11);
            holder.btn1 = (Button) convertView.findViewById(R.id.button2);
            // holder.chk1 = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv1.setText(data.get(position).ShopName);
        holder.tv4.setText(data.get(position).Addr);
        holder.tv2.setText(data.get(position).ContactPerson);
        holder.tv3.setText(data.get(position).Tel);
        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, tv1.getText(),Toast.LENGTH_SHORT).show();
                Intent it = new Intent(context, DetailActivity.class);
                Bundle b = new Bundle();
                b.putString("ShopName", data.get(p).ShopName);
                b.putString("Addr", data.get(p).Addr);
                b.putString("ContactPerson", data.get(p).ContactPerson);
                b.putString("Tel", data.get(p).Tel);

                it.putExtras(b);
                context.startActivity(it);
            }
        });
        return convertView;
    }

        static class ViewHolder {
            TextView tv1;
            TextView tv2;
            TextView tv3;
            TextView tv4;
            Button btn1;
            //CheckBox chk1;
        }

}
