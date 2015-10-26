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
import android.widget.Toast;

import java.text.ParseException;
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

        // textTime.setText(getCurrentTime());

        headsUpButton = (Button) findViewById(R.id.buttonHeadsUp);
        headsUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createHeadsUpNotification();
            }
        });

        //textTime.setText(_sdfWatchTime.format(new Date()));


        //  TextView _tvTime;



    /*    visibilityRadioGroup = (RadioGroup) findViewById(R.id.visibilityRadioGroup);
        visibilityButton = (Button) findViewById(R.id.visibilityButton);
        visibilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOtherNotification();
            }
       });*/

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
                textViewDifference.setText(String.valueOf(timeToEnd()));



            }
        });





   /*     customButton = (Button) findViewById(R.id.customButton);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCustomNotification();
            }
        });*/
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

 /*   private String getCurrentTimeFormat(String timeFormat){
        String time = "";
        SimpleDateFormat df = new SimpleDateFormat(timeFormat);
        Calendar c = Calendar.getInstance();
        time = df.format(c.getTime());

        return time;
    }*/

    /*  private String getCurrentTime() {

          final Calendar c = Calendar.getInstance();

          return(new StringBuilder()
                  .append(c.get(Calendar.HOUR_OF_DAY)).append(":")
                  .append(c.get(Calendar.MINUTE))).toString();
      }*/
    private void createHeadsUpNotification() {
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_notification)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[] {1, 1, 1})
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentTitle("Simple Heads-Up Notification")
                .setContentText("This is a normal notification.");
//        if (makeHeadsUpNotification) {

//            Intent push = new Intent();
//            push.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            push.setClass(this, MainActivity.class);
//
//            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
//                    push, PendingIntent.FLAG_CANCEL_CURRENT);
//            notificationBuilder
//                    .setContentText("A Heads-Up notification for Lollipop and above")
//                    .setFullScreenIntent(fullScreenPendingIntent, true);

//        }

//        notificationBuilder.set

//        NotificationCompat.InboxStyle inboxStyle =
//                new NotificationCompat.InboxStyle();
//        String[] events = {"Additional text one","Additional Text two"};
//// Sets a title for the Inbox in expanded layout
//        inboxStyle.setBigContentTitle("Extended contents");
//
//// Moves events into the expanded layout
//        for (int i=0; i < events.length; i++) {
//            inboxStyle.addLine(events[i]);
//        }
//// Moves the expanded layout object into the notification object.
//        notificationBuilder.setStyle(inboxStyle);
//
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
 /*   private void createOtherNotification() {
        int visibility;
        String description;
        int notificationId;
        int notificationIconId;

        switch (visibilityRadioGroup.getCheckedRadioButtonId()) {
            case R.id.privateRadioButton:
                visibility = NotificationCompat.VISIBILITY_PRIVATE;
                description = "This notification is partially visible on lock screen";
                notificationId = PRIVATE_NOTIFICATION_ID;
                notificationIconId = R.drawable.ic_private_notification;
                break;
            case R.id.secretRadioButton:
                visibility = NotificationCompat.VISIBILITY_SECRET;
                description = "This notification is never visible on lock screen";
                notificationId = SECRET_NOTIFICATION_ID;
                notificationIconId = R.drawable.ic_secret_notification;
                break;
            default:
                //If not selected, returns PUBLIC as default.
                visibility = NotificationCompat.VISIBILITY_PUBLIC;
                description = "This notification is always visible on lock screen";
                notificationId = PUBLIC_NOTIFICATION_ID;
                notificationIconId = R.drawable.ic_public_notification;
        }

       NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("Notification Visibility for Lollipop");

        notificationBuilder.setVisibility(visibility);
        notificationBuilder.setContentText(description);
        notificationBuilder.setSmallIcon(notificationIconId);
        notificationManager.notify(notificationId, notificationBuilder.build());
    }*/

    private void createCustomNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_notification)
                .setContent(remoteViews);

        notificationManager.notify(CUSTOM_NOTIFICATION_ID, notificationBuilder.build());

    }

    public long timeToEnd()  {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        Date date1 = null;
        try {
            date1 = simpleDateFormat.parse(textTime.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = simpleDateFormat.parse("01:15 AM");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        long difference = date2.getTime() - date1.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;


        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        Toast.makeText(MainActivity.this, (int) difference, Toast.LENGTH_LONG).show();



        return difference;
    }
}

