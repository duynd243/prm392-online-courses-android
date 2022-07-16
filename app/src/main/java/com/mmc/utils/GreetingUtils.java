package com.mmc.utils;

import java.util.Calendar;

public class GreetingUtils {
    public static String getGreeting() {
        // Say hello by time of day
        String greeting = "";
        // get current hour
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 12) {
            greeting = "Good morning";
        } else if (hour < 18) {
            greeting = "Good afternoon";
        } else {
            greeting = "Good evening";
        }
        return greeting;
    }
}
