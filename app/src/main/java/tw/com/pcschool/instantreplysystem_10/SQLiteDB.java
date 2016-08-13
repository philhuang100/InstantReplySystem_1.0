package tw.com.pcschool.instantreplysystem_10;

/**
 * Created by Joseph_NB on 2016/8/11.
 */
public class SQLiteDB {
        public String ShopName;
        public String Addr;
        public String isComp;
        public String ContactPerson;
        public String Tel;
        public String Remark;
        public String TaskNo;
        public SQLiteDB(String a, String b, String c, String d,String e,String f,String g)
        {
            ShopName=a;
            Addr=b;
            isComp=c;
            Tel=d;
            ContactPerson=e;
            Remark=f;
            TaskNo=g;
        }

    }
