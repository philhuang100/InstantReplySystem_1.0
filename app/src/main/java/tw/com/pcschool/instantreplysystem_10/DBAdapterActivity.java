package tw.com.pcschool.instantreplysystem_10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DBAdapterActivity extends BaseAdapter {
    Context context;
    ArrayList<SQLiteDB> data;
    boolean chk[];
    LayoutInflater inflater;
    String chkcomp="";

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
            holder.tv_shopname = (TextView) convertView.findViewById(R.id.ShopName);
            holder.tv_tel = (TextView) convertView.findViewById(R.id.Tel);
            //holder.tv3 = (TextView) convertView.findViewById(R.id.textView10);
            holder.tv_addr = (TextView) convertView.findViewById(R.id.Addr);
           // holder.btn1 = (Button) convertView.findViewById(R.id.button2);
            // holder.chk1 = (CheckBox) convertView.findViewById(R.id.checkBox);
           // holder.img1 = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_shopname.setText(data.get(position).ShopName);
       // holder.tv2.setText(data.get(position).isComp);
        holder.tv_tel.setText(data.get(position).Tel);
        //holder.tv3.setText(data.get(position).ContactPerson);
        holder.tv_addr.setText(data.get(position).Addr);
        //chkcomp=data.get(position).isComp;
        //TextView chkComp = (TextView) convertView.findViewById(R.id.textView4);

      //  if(chkComp.getText().toString().equals("Y")) {
       /* if(chkcomp.equals("Y")) {
            holder.img1.setImageResource(R.drawable.ok1);
        }*/
        /*
        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, tv1.getText(),Toast.LENGTH_SHORT).show();
                Intent it = new Intent(context, DetailActivity.class);
                Bundle b = new Bundle();
                b.putString("ShopName", data.get(p).ShopName);
                b.putString("Addr", data.get(p).Addr);

               // b.putString("ContactPerson", data.get(p).ContactPerson);
               // b.putString("Tel", data.get(p).Tel);

                it.putExtras(b);
                context.startActivity(it);
            }
        }); */

        return convertView;
    }

        static class ViewHolder {
            TextView tv_shopname;
            TextView tv_tel;
           // TextView tv3;
            TextView tv_addr;
            //Button btn1;
            //ImageView img1;
            //CheckBox chk1;
        }

}
