package com.example.aciolekwaw.notificationtimer;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int HEADS_UP_NOTIFICATION_ID = 1;
    public static final int SECRET_NOTIFICATION_ID = 2;
    public static final int PRIVATE_NOTIFICATION_ID = 3;
    public static final int PUBLIC_NOTIFICATION_ID = 4;
    public static final int NORMAL_NOTIFICATION_ID = 5;
    public static final int CUSTOM_NOTIFICATION_ID = 6;

    NotificationManager notificationManager;
    Button headsUpButton;
    Button normalButton;
    BroadcastReceiver _broadcastReceiver;
    TextView textTime;
    final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("HH:mm");
    Button buttonTimerNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TextView textViewDifference = (TextView) findViewById(R.id.textViewDifference);

        notificationManager = (NotificationManager) getSystemService(Context
                .NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_main);
        setTitle("LollipopNotifications");

        textTime = (TextView) findViewById(R.id.textViewTime);
        textTime.setText(_sdfWatchTime.format(new Date()));



        headsUpButton = (Button) findViewById(R.id.buttonHeadsUp);
        headsUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createHeadsUpNotification();
            }
        });


        normalButton = (Button) findViewById(R.id.normalButton);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_launcher_notification)
                        .setContentTitle("Simple Notification")
                        .setContentText("This is a normal notification.");

                notificationManager.notify(NORMAL_NOTIFICATION_ID, notificationBuilder.build());
            }
        });

        buttonTimerNotification = (Button) findViewById(R.id.buttonTimerNotification);
        buttonTimerNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTimeNotification();
                textViewDifference.setText(timeToEnd());

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    textTime.setText(_sdfWatchTime.format(new Date()));

            }
        };

        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }


    private void createHeadsUpNotification() {
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_notification)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[] {1, 1, 1})
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle("Simple Heads-Up Notification")
                .setContentText("This is a normal notification.");

        notificationManager.notify(HEADS_UP_NOTIFICATION_ID, notificationBuilder.build());
    }


    private void createTimeNotification() {
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_notification)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[]{1, 1, 1})
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle("Time head up notification")
                .setContentText(textTime.getText().toString());

        notificationManager.notify(HEADS_UP_NOTIFICATION_ID, notificationBuilder.build());
    }


    private void createCustomNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_notification)
                .setContent(remoteViews);

        notificationManager.notify(CUSTOM_NOTIFICATION_ID, notificationBuilder.build());

    }

    public String timeToEnd() {

 /*       int numMinutes = 0;

        String currentTime = textTime.getText().toString();
        String endingLesson = "05:00";

        try {

            SimpleDateFormat formatYmd = new SimpleDateFormat("HH:mm");

            Date d1 = formatYmd.parse(currentTime);
            Date d2 = formatYmd.parse(endingLesson);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);
            numMinutes = Minutes.minutesBetween(dt1, dt2).getMinutes();
            // Days between 2013-09-01 and 2013-09-02: 1
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(numMinutes);*/

        DateTime now = DateTime.now();
        DateTime dateTime = now.plusMinutes(10);
        Seconds seconds = Seconds.secondsBetween(now, dateTime);

        return String.valueOf(seconds);
    }

   /* public long timeToEnd()  {




        String string1 = "05:00:00 PM";
        Date time1=null;
        try {
            time1 = new SimpleDateFormat("HH:mm a").parse(string1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();

        calendar1.setTime(time1);

        String string2 = "03:00:00 PM";
        Date time2 = null;
        try {
            time2 = new SimpleDateFormat("HH:mm a").parse(string2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time2);
        calendar2.add(Calendar.DATE, 1);

      *//*  Date x = calendar1.getTime();
        Date xy = calendar2.getTime();
        long diff = x.getTime() - xy.getTime();
        long diffMinutes = diff / (60 * 1000);
        float diffHours = diffMinutes / 60;
        System.out.println("diff hours" + diffHours);*//*

        if(calendar2.get(Calendar.AM_PM) == 1 && calendar1.get(Calendar.AM_PM) == 0)     {
            calendar2.add(Calendar.DATE, 1);
        }
        long diff = calendar1.getTimeInMillis() - calendar2.getTimeInMillis();



        return (long) diff;
    }*/
}

