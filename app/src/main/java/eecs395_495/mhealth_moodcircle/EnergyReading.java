package eecs395_495.mhealth_moodcircle;

import java.sql.Date;
import java.util.Calendar;

/**
 * Represents an energy at a specific time.
 * <p/>
 * This class is given and should not need modification.
 * <p/>
 * Created by William on 12/29/2016.
 */
public class EnergyReading {
    private String date;
    private double energy;

    public String getDate() {
        return date;
    }

    public double getEnergy() {
        return energy;
    }

    public EnergyReading(double energy, long timestamp) {
        this.energy = energy;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(timestamp));
        // Format the date, being sure to add leading zeros as needed
        this.date = c.get(Calendar.YEAR) + "-" +
                ((c.get(Calendar.MONTH) < 9) ? "0" /*c.get(Calendar.MONTH)*/ : "") +
                (c.get(Calendar.MONTH) + 1) + "-" +
                ((c.get(Calendar.DAY_OF_MONTH) < 10) ? "0" : "") +
                c.get(Calendar.DAY_OF_MONTH) + " " +
                ((c.get(Calendar.HOUR_OF_DAY) < 10) ? "0" : "") +
                c.get(Calendar.HOUR_OF_DAY) + ":" +
                ((c.get(Calendar.MINUTE) < 10) ? "0" : "") +
                c.get(Calendar.MINUTE) + ":" +
                ((c.get(Calendar.SECOND) < 10) ? "0" : "") +
                c.get(Calendar.SECOND);
    }

}
