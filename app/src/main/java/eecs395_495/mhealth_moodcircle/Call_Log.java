package eecs395_495.mhealth_moodcircle;

import java.util.Date;

/**
 * Created by 51375 on 2017/2/26.
 */

public class Call_Log {
    private String phNumber;
    private String callType;
    private Date callDate;
    private String callDuration;

    public String getPhNumber(){
        return phNumber;
    }

    public String getCallType(){
        return callType;
    }

    public Date getCallDate(){
        return callDate;
    }
    public String getCallDuration(){
        return callDuration;
    }

    public void setPhNumber(String phNumber){
        this.phNumber=phNumber;
    }
    public void setCallType(String callType){
        this.callType=callType;
    }
    public void setCallDate(Date callDate){
        this.callDate=callDate;
    }
    public void setCallDuration(String callDuration){
        this.callDuration=callDuration;
    }



}
