package eecs395_495.mhealth_moodcircle;

import android.content.Context;
import android.database.Cursor;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by 51375 on 2017/2/26.
 */

public class SendData implements Runnable {

    String TAG="send data";
    private Context context;
    public SendData(Context context){
        this.context=context;
    }

    @Override
    public void run() {
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.v(TAG, id);
//            c.moveToFirst();

        Cursor cursor = SurveyDBHelper.getFirst60Entries(context);
        cursor.moveToFirst();
//            cursor.
//            int energyIndex = cursor.getColumnIndex(SurveyDBHelper.EnergyEntry.COLUMN_NAME_ENERGY);
//            int dateIndex = cursor.getColumnIndex(SurveyDBHelper.EnergyEntry.COLUMN_NAME_TIME);
        int dateIndex = cursor.getColumnIndex(SurveyDBHelper.EnergyEntry.COLUMN_NAME_DATE);
        int phNumber = cursor.getColumnIndex(SurveyDBHelper.EnergyEntry.COLUMN_NAME_PHONENUMBER);

        String data = "";

        while (!cursor.isAfterLast()) {
            data = "";
            try {
                data += URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(cursor.getString(dateIndex), "UTF-8");
                data += "&" + URLEncoder.encode("phonenumber", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(cursor.getInt(phNumber)), "UTF-8");
                data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(id.toString(), "UTF-8");
                Log.v(TAG, data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            OutputStreamWriter writer = null;
            BufferedReader reader;
            try {
                // TODO: make an HttpURLConnection and send data as parameters in a POST (one at a time)
                URL url = new URL("http://murphy.wot.eecs.northwestern.edu/~mjs635/SMSgateway.py");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setUseCaches(false);
                writer = new OutputStreamWriter(urlConnection.getOutputStream());
//                OutputStream os = urlConnection.getOutputStream();
//                writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(data);
                writer.flush();

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    Log.v(TAG, sb.toString());
                }
//  throw new UnsupportedOperationException("Not yet implemented");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (writer != null)
                        writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cursor.moveToNext();
//                SurveyDBHelper.deleteNEntries(context, 1);
        }
    }
}
