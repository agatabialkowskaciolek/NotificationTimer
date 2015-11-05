package com.example.aciolekwaw.notificationtimer;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
    private int notification_id = 1;
    LocalTime nowTime;

    PendingIntent pendingIntent;

    Timer timer;
    TimerTask timerTask;

    final Handler handler = new Handler();
    LocalTime localTaskTimer ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        notificationManager = (NotificationManager) getSystemService(Context
                .NOTIFICATION_SERVICE);
        setContentView(R.layout.activity_main);
        setTitle("LollipopNotifications");


        nowTime = new LocalTime();

        textViewDifference = (TextView) findViewById(R.id.textViewDifference);
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
               /* createTimeNotification();
                tv.setText(timeToEnd());*/

                //presentHeadsUpNotification(Notification.VISIBILITY_PUBLIC, R.drawable.ic_launcher_notification, "titttititi", "tetetettxxxxttt");


            }
        });





/*//
        Toast toast = Toast.makeText(getApplicationContext(), nowTime.toString(), Toast.LENGTH_SHORT);

        toast.show();*/


        /*for (int i = 0; i < LessonTime.endTimeLesson.size(); i++) {
            if (nowTime.equals(LessonTime.endTimeLesson.get(i))) {*/

                presentHeadsUpNotification(Notification.VISIBILITY_PUBLIC, R.drawable.ic_launcher_notification, "titttititi", "tetetettxxxxttt");
            }
 /*       }
    }*/


    @Override
    protected void onResume() {

        super.onResume();

        //onResume we start our timer so it can start when the app comes from the background

        startTimer();

    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 1000); //

    }



    public void stoptimertask(View v) {
        //stop the timer, if it's not already null
        if (timer != null) {

            timer.cancel();

            timer = null;

        }

    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {

            public void run() {

                //use a handler to run a toast that shows the current timestamp

                handler.post(new Runnable() {

                    public void run() {

                        //get the current timeStamp

                        //Calendar calendar = Calendar.getInstance();

                        localTaskTimer = new LocalTime();

                       // final String strDate = simpleDateFormat.format(calendar.getTime());

                        //show the toast

                     int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(getApplicationContext(), localTaskTimer.toString(), duration);

                        toast.show();

                    }


                });

            }

        };

    }


    private void presentHeadsUpNotification(int visibility, int icon, String title, String text) {
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse("http://www.wgn.com"));
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setVisibility(visibility)
                .addAction(android.R.drawable.ic_menu_view, "details string", contentIntent)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notification_id, notification);
    }

    public void exampleTime(){

        Intent myIntent = new Intent(MainActivity.this , MyService.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);




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


        LocalTime nowlTime = new LocalTime();


        if ((nowlTime.isAfter(LessonTime.startTimeLesson.get(0)) & nowlTime.isBefore(LessonTime.endTimeLesson.get(LessonTime.endTimeLesson.size() - 1)))){

            for (int i = 0; i < LessonTime.startTimeLesson.size(); i++) {
                LocalTime startTime = LessonTime.startTimeLesson.get(i);
                LocalTime endTime = LessonTime.endTimeLesson.get(i);


                if(nowTime.equals("08:00")){
                    Toast.makeText(this, "Początkek zajęć", Toast.LENGTH_LONG).show();
                }
                else if(endTime.equals("21:00")){
                    Toast.makeText(this, "Koniec zajęć zajęć", Toast.LENGTH_LONG).show();
                }
                else if((nowTime.isAfter(LessonTime.endTimeLesson.get(i)) &&(nowTime.isBefore(LessonTime.startTimeLesson.get(i+1))))){

             /*       if((LessonTime.endTimeLesson.equals(LessonTime.endTimeLesson.size()-1))||(LessonTime.startTimeLesson.equals(LessonTime.startTimeLesson.size()-1))){

                    }
                    else {
                        Toast.makeText(this, "Jest przerwa", Toast.LENGTH_LONG).show();
                    }*/

                    try{

                        Toast.makeText(this, "Jest przerwa", Toast.LENGTH_LONG).show();
                    }
                    catch (IndexOutOfBoundsException e){}

                }

                else if (nowTime.isAfter(startTime) && (nowTime.isBefore(endTime))) {

                    //if((nowTime.isAfter(endTime.minusMinutes(5))&(nowTime.isBefore(endTime)))) {

                        Minutes minutes = Minutes.minutesBetween(nowlTime, endTime);
                        String minutesToEnd = String.valueOf(minutes.getMinutes());
                        i++;
                        String text = "jestes w " + i + "bloku lekcyjnym";
                        Toast.makeText(this, text, Toast.LENGTH_LONG).show();


                        return minutesToEnd;
                   // }

                }

            }

        }

        return"nieznane";
    }
}



