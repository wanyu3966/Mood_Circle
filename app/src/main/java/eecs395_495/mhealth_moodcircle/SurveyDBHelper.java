package eecs395_495.mhealth_moodcircle;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Helper for the energy database.
 * Steps to reproduce can be found in the tutorial
 * <a href="https://developer.android.com/training/basics/data-storage/databases.html">here</a>.
 * <p/>
 * In this file, the students should write the queries to carry out necessary operations. Currently,
 * they are not required to write queries to create the database and the contract is given to them
 * in order to maintain consistency among solutions.
 * <p/>
 * Created by William on 12/29/2016
 */
public class SurveyDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    /* ********************************* DATABASE STRUCTURE *********************************** */
    // DB metadata
    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "survey.db";

    // SQL instructions for creation and deletion of the table
    private static final String SQL_CREATE_USERS_MOODCIRCLE =
            "CREATE TABLE USERS_MOODCIRCLE(ID int, longitude double, latitude double, time DATE, positive double, negative double, happy int, grateful int, content int, enthusiastic int, inspired int, proud int, guilty int, anxious int, bored int, fearful int, angry int, sad int, pemotion varchar(80),nemotion varchar(80),prate double,nrate double)";
    private static final String SQL_CREATE_CALLLOG_MOODCIRCLE=
            "CREATE TABLE CALLLOG_MOODCIRCLE (phonenumber int, date DATE, duration int)";
    private static final String SQL_CREATE_SMSLOG_MOODCIRCLE=
            "CREATE TABLE SMSLOG_MOODCIRCLE (date DATE, phonenumber int, message varchar(255))";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EnergyEntry.TABLE_NAME;

    /**
     * Maintains the instance for the singleton model
     */
    private static SurveyDBHelper instance = null;

    /**
     * Returns the current instance of the DBHelper
     *
     * @param context of the app
     * @return the helper
     */
    public static SurveyDBHelper getInstance(Context context) {
        if (instance == null) instance = new SurveyDBHelper(context);
        return instance;
    }

    /**
     * Inner class for table contents
     */
    public class EnergyEntry implements BaseColumns {
        //tables
        public static final String TABLE_NAME = "entries";
        public static final String CALLLOG_MOODCIRCLE="CALLLOG_MOODCIRCLE";
        public static final String SMSLOG_MOODCIRCLE="SMSLOG_MOODCIRCLE";
        //energy table
        public static final String COLUMN_NAME_ENERGY = "energy";
        public static final String COLUMN_NAME_TIME = "date";
        public static final String COLUMN_NAME_LATITUDE="latitude";
        public static final String COLUMN_NAME_LONGITUDE="longitude";
        //call log
        public static final String COLUMN_NAME_PHONENUMBER="phonenumber";
        public static final String COLUMN_NAME_DURATION="duration";
        public static final String COLUMN_NAME_DATE="date";
//        public static final String COLUMN_NAME_CALLTYPE=""
        //SMS message
        public static final String COLUMN_NAME_MESSAGE="message";
//        public static final String COLUMN_NAME_
        public static final String COLUMN_NAME_ID="ID";


    }

    private SurveyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS_MOODCIRCLE);
        db.execSQL(SQL_CREATE_CALLLOG_MOODCIRCLE);
        db.execSQL(SQL_CREATE_SMSLOG_MOODCIRCLE);
    }

    /**
     * Called when the database version increases
     *
     * @param db         database
     * @param oldVersion previous version number
     * @param newVersion current version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Called when the database version decreases
     *
     * @param db         database
     * @param oldVersion previous version number
     * @param newVersion current version number
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /* *********************************** INTERRACTION METHODS ****************************** */

    /**
     * Adds an energy value to the current database
     *
     * @param energy  class instance representing the energy
     * @param context of the application
     */
    public static void enterEnergy(EnergyReading energy, Context context) {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EnergyEntry.COLUMN_NAME_ENERGY, energy.getEnergy());
        values.put(EnergyEntry.COLUMN_NAME_TIME, energy.getDate());

        db.insert(EnergyEntry.TABLE_NAME, null, values);
    }

    public static void enterSMS(Sms sms,Context context){
        SQLiteDatabase db=getInstance(context).getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(EnergyEntry.COLUMN_NAME_PHONENUMBER,sms.getAddress());
        values.put(EnergyEntry.COLUMN_NAME_DATE,sms.getTime());
        values.put(EnergyEntry.COLUMN_NAME_MESSAGE,"message");
        db.insert(EnergyEntry.SMSLOG_MOODCIRCLE,null,values);
    }

    public static void enterCallLog(Call_Log call_log,Context context){
        SQLiteDatabase db=getInstance(context).getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(EnergyEntry.COLUMN_NAME_DATE,call_log.getCallDate().toString());
        values.put(EnergyEntry.COLUMN_NAME_PHONENUMBER,call_log.getPhNumber());
        values.put(EnergyEntry.COLUMN_NAME_DURATION,call_log.getCallDuration());
        db.insert(EnergyEntry.CALLLOG_MOODCIRCLE,null,values);
    }
    /**
     * Queries the database for the last 60 entries by datetime. Method taken from tutorial
     * <a href="https://developer.android.com/training/basics/data-storage/databases.html#DbHelper>here</a>.
     *
     * @param context of the application
     * @return a @Cursor containing the data
     */
    public static Cursor getLatest60Entries(Context context) {
//        String[] projection = {
//                EnergyEntry.COLUMN_NAME_ENERGY,
//                EnergyEntry.COLUMN_NAME_TIME
//        };
//        String sortOrder=
//                EnergyEntry.COLUMN_NAME_TIME+" DESC";
//        Cursor cursor = db.query(EnergyEntry.TABLE_NAME,
//                projection,
//                null,
//                null,
//                null,
//                null,
//                sortOrder
//        );
        SQLiteDatabase db = getInstance(context).getReadableDatabase();
        String sql = "select * from " + EnergyEntry.SMSLOG_MOODCIRCLE;
//        Log.v(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
//      db.close();
        return cursor;

//        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Queries for the first 60 entries by datetime. For info about the query() method, see
     * <a href="https://developer.android.com/training/basics/data-storage/databases.html#DbHelper">this</a>
     * link.
     *
     * @param context of the application
     * @return a Cursor containing the data
     */
    public static Cursor getFirst60Entries(Context context) {

        SQLiteDatabase db = getInstance(context).getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + EnergyEntry.SMSLOG_MOODCIRCLE, null);
        // db.close();
        return cursor;
//   throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Deletes the n oldest entries from the sqlite database. This should be used after successfully
     * backing up the entries to the server
     *
     * @param context of the application
     * @param n       number of entries to delete
     */
    public static void deleteNEntries(Context context, int n) {

        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        Cursor cursor = db.rawQuery("select " + EnergyEntry.COLUMN_NAME_TIME + " from " + EnergyEntry.TABLE_NAME + " ORDER BY " + EnergyEntry.COLUMN_NAME_TIME + " ASC LIMIT " + n, null);
        cursor.moveToFirst();
        int dateIndex = cursor.getColumnIndex(SurveyDBHelper.EnergyEntry.COLUMN_NAME_TIME);
        Log.v(TAG, cursor.getString(dateIndex));
        String sql = "delete from " + EnergyEntry.TABLE_NAME + " where " + EnergyEntry.COLUMN_NAME_TIME + " = " + "\'" + cursor.getString(dateIndex) + "\'";
        Log.v(TAG, sql);
        db.execSQL(sql);
//        db.delete(EnergyEntry.TABLE_NAME,null,null);
        //db.close();

//        throw new UnsupportedOperationException("Not yet implemented");
    }
}

