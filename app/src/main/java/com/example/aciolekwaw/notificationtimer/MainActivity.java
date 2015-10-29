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

import org.joda.time.LocalTime;
import org.joda.time.Period;

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
    TextView textViewDifference;
    TextView tv;
    private Object zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        notificationManager = (NotificationManager) getSystemService(Context
                .NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_main);
        setTitle("LollipopNotifications");

        textViewDifference  = (TextView) findViewById(R.id.textViewDifference);
        tv = (TextView) findViewById(R.id.textViewTime1);


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
                tv.setText(timeToEnd());
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
                .setVibrate(new long[]{1, 1, 1})
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
                .setContentText(textTime.getText().toString())
                .setContentText(timeToEnd());


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

       
        LocalTime localTime= new LocalTime();
        LocalTime endTime = new LocalTime("16:00");

// period of 1 year and 7 days
        Period period = new Period(localTime, endTime);

// calc will equal end
       // DateTime calc = start.plus(period);

// able to calculate whole days between two dates easily
       // Days days = Days.daysBetween(start, end);

// able to calculate whole months between two dates easily
        //Months months = Months.monthsBetween(start, end);

        //textViewDifference.setText(String.valueOf(period));
        return String.valueOf(period.getMinutes());
    }
}



