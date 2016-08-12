package tw.com.pcschool.instantreplysystem_10;

/**
 * Created by Joseph_NB on 2016/8/11.
 */
public class SQLiteDB {
        public String ShopName;
        public String Addr;
        public String ContactPerson;
        public String Tel;
        public String Remark;
        public String TaskNo;
        public SQLiteDB(String a, String b, String c, String d,String e,String f)
        {
            ShopName=a;
            Addr=b;
            Tel=c;
            ContactPerson=d;
            Remark=e;
            TaskNo=f;
        }

    }
