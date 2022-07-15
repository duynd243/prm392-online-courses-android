package com.mmc.utils;

import java.util.Date;

public class GreetingUtils {
    public static String getGreeting() {
        // Say hello by time of day
        String greeting = "";
        // get current hour
        int hour = new Date().getHours();
        if (hour >= 0 && hour < 12) {
            greeting = "Good morning";
        } else if (hour >= 12 && hour < 18) {
            greeting = "Good afternoon";
        } else if (hour >= 18 && hour < 24) {
            greeting = "Good evening";
        }
        return greeting;
    }
}
