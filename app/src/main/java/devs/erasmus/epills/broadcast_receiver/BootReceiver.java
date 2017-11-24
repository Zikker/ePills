package devs.erasmus.epills.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import devs.erasmus.epills.model.IntakeMoment;
import devs.erasmus.epills.utils.AlarmUtil;

import static android.database.sqlite.SQLiteDatabase.OPEN_READWRITE;

public class BootReceiver extends BroadcastReceiver {
    String DATABASE_TABLE_NAME = "/data/data/devs.erasmus.epills/databases/ePills.db";

    //Unfortunately we can't inizialize LitePal without an application context, so we need to inizialize
    // the DB for retrieving the alarms using standard SQLite

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_TABLE_NAME, null, OPEN_READWRITE);
            //i need sdf because SQLite doesn't actually support Date as a type, so i have to rebuild it
            SimpleDateFormat dateFormat = new SimpleDateFormat();

           //String[] columns = new String[]{"startDate", "endDate", "medicine", "quantity", "alarmRequestCode", "isAlarmSet"};
           Cursor c = db.query("IntakeMoment", null, null, null, null, null, null);
            int i=0;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                i++;
                long startDateMillis = c.getLong(c.getColumnIndex("startdate"));
                long endDateMillis = c.getLong(c.getColumnIndex("enddate"));
                int quantity = c.getInt(c.getColumnIndex("quantity"));
                int alarmRequestCode = c.getInt(c.getColumnIndex("alarmrequestcode"));


                Date startDate = long2Date(startDateMillis);
                //refresh startDate to current day/month/year
                //startDate = fixDate(startDate);

                Date endDate = long2Date(endDateMillis);

                startDate.setTime(startDateMillis);
                endDate.setTime(endDateMillis);

                AlarmUtil.setAlarm(context, "", quantity, startDate, endDate, alarmRequestCode);
                Toast.makeText(context, "alarm set"+ String.valueOf(alarmRequestCode), Toast.LENGTH_SHORT).show();
            }
            c.close();
        }

    }

    /*
    private Date fixDate(Date date){
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);

        while(dateCalendar.getTimeInMillis() < System.currentTimeMillis()){
            dateCalendar.set(Calendar.DAY_OF_MONTH, dateCalendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        return dateCalendar.getTime();
    }
    */
    private Date long2Date(long dateInMillis){
        Date date = new Date();
        date.setTime(dateInMillis);

        return date;
    }
}
