package eecs395_495.mhealth_moodcircle;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
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
 * Sends the energy data to the back end when requested.
 * The Replicator keeps track of when it is replicating and does not replicate multiple times.
 * The replicated data is deleted from the local database.
 * To replicate, this class should be instantiated and execute() should be called.
 * <p/>
 * Students are required to write the meat of the transmission of data to the back end.
 * <p/>
 * Created by William on 12/30/2016.
 */
public class Replicator extends AsyncTask<Void, Void, Object> {
    private static final String TAG = "Replicator";
    private static boolean isReplicating = false;
    private boolean isCanceled = false;

    private Context context;

    public Replicator(Context context) {
        this.context = context;
    }

    @Override
    /**
     * When execute() is called, this happens first
     */
    protected void onPreExecute() {
        isCanceled = false;
        isReplicating = true;
        Log.v(TAG, "pre");
    }

    @Override
    /**
     * When execute() is called, this happens second
     */
    protected Void doInBackground(Void... params) {

        // Don't do anything if the execution is canceled
        if (!isCanceled) {
            // Query the database and package the data
//            Cursor c = EnergyDBHelper.getFirst60Entries(context);
//            int timeCol = c.getColumnIndex(EnergyDBHelper.EnergyEntry.COLUMN_NAME_TIME);
//            int energyCol = c.getColumnIndex(EnergyDBHelper.EnergyEntry.COLUMN_NAME_ENERGY);
            // EnergyDBHelper.deleteNEntries(context, 0);
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
                    data += URLEncoder.encode("phonenumber", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(cursor.getInt(phNumber)), "UTF-8");
                    data += "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(cursor.getString(dateIndex), "UTF-8");
                    data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(id.toString(), "UTF-8");
                    Log.v(TAG, data);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                OutputStreamWriter writer = null;
                BufferedReader reader;
                try {
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
        return null;
    }

    @Override
    /**
     * When execute is called, this happens third
     */
    protected void onPostExecute(Object result) {
        isReplicating = false;
    }

}