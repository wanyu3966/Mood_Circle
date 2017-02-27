package eecs395_495.mhealth_moodcircle;

import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 51375 on 2017/2/10.
 */

public class SMS_Tracker {
    Sms sms;
    List<Sms> smsList;

    public Sms getSms(){
        return sms;
    }

    public List<Sms> getSmsList(){
        return  smsList;
    }
    public void getAllSms(Context context) {
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = context.getContentResolver();
        CursorLoader cl=new CursorLoader(context,message,null,null,null,null);
//        Cursor c = cr.query(message, null, null, null, null);
        Cursor c = cl.loadInBackground();
//        LoaderManager
//        context.CursorLoader(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < 30; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }
                SurveyDBHelper.enterSMS(objSms,context);
                SMS_Tracker.sendDatatoBackend(context);
                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();

        smsList=lstSms;
    }

    public static void sendDatatoBackend(Context context){
        new Replicator(context).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }
}
