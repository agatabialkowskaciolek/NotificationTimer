package com.example.aciolekwaw.notificationtimer;

import android.util.Log;

import org.joda.time.LocalTime;

import java.util.LinkedList;

/**
 * Created by aciolekwaw on 2015-10-30.
 */
public class LessonTime {

    public static final LinkedList<LocalTime> startTimeLesson= new LinkedList<LocalTime>(){{
        add(new LocalTime("08:00"));
        add(new LocalTime("08:50"));
        add(new LocalTime("09:50"));
        add(new LocalTime("10:40"));
        add(new LocalTime("11:40"));
        add(new LocalTime("12:30"));
        add(new LocalTime("13:30"));
        add(new LocalTime("14:20"));
        add(new LocalTime("15:45"));
        add(new LocalTime("16:35"));
        add(new LocalTime("17:35"));
        add(new LocalTime("18:25"));
        add(new LocalTime("19:25"));
        add(new LocalTime("20:15"));
    }};

    public static final LinkedList<LocalTime> endTimeLesson= new LinkedList<LocalTime>(){{
        add(new LocalTime("08:45"));
        add(new LocalTime("09:35"));
        add(new LocalTime("10:35"));
        add(new LocalTime("11:25"));
        add(new LocalTime("12:25"));
        add(new LocalTime("13:15"));
        add(new LocalTime("14:15"));
        add(new LocalTime("15:05"));
        add(new LocalTime("16:30"));
        add(new LocalTime("17:20"));
        add(new LocalTime("18:20"));
        add(new LocalTime("19:10"));
        add(new LocalTime("20:10"));
        add(new LocalTime("21:00"));
    }};

    public void fiveMinutesBefore(int i){


        for(i=0;i<=endTimeLesson.size();i++){

           // Toast.makeText(, endTimeLesson, Toast.LENGTH_SHORT).show();
            Log.d("1",String.valueOf(endTimeLesson));

        }
    }

}


