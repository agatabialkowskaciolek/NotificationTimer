package com.example.aciolekwaw.notificationtimer;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by aciolekwaw on 2015-10-30.
 */
public class MyAp extends Application {

    @Override
    public void onCreate()
    { super.onCreate(); JodaTimeAndroid.init(this);

    }
}
