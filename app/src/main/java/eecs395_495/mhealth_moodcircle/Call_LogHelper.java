package eecs395_495.mhealth_moodcircle;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 51375 on 2017/2/16.
 */

public class Call_LogHelper {

    String calllog;
    Call_Log clog;
    List<Call_Log> call_logList;
    public String getCall_Log() {
        return calllog;
    }

    public void getCallDetails(Context context) {
        StringBuffer sb = new StringBuffer();
        List<Call_Log> tempCallLogList=new ArrayList<Call_Log>();
        Call_Log tempCalllog=new Call_Log();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        managedCursor.moveToNext();
        for(int i=0;i<25;i++) {
            if (managedCursor.moveToNext()) {
                String phNumber = managedCursor.getString(number);
                String callType = managedCursor.getString(type);
                String callDate = managedCursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = managedCursor.getString(duration);
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }

                //set calllog information
                tempCalllog.setCallDate(callDayTime);
                tempCalllog.setCallDuration(callDuration);
                tempCalllog.setCallType(callType);
                tempCalllog.setPhNumber(phNumber);

                tempCallLogList.add(tempCalllog);

                SurveyDBHelper.enterCallLog(tempCalllog, context);
                sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
                sb.append("\n----------------------------------");
            }
        }
        managedCursor.close();
        calllog=sb.toString();
        call_logList=tempCallLogList;

    }



}
