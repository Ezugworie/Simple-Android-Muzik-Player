package com.isoft.application;

import android.arch.core.util.Function;

public class Utilities {
    /***
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     */
    public String milliSecondsToTimer(long milliseconds){
        String finaTimerString = "";
        String secondsString = "";

        //COnvert total duration into time
        int hours = (int)(milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int)((milliseconds % (1000*60*60)) % (1000*60) / 1000) ;

        //add hours if there
        if (hours > 0){
            finaTimerString = hours + "";
        }

        //prepending 0 to seconds if it is one digit
        if (seconds < 10){
            secondsString = "0" + seconds; }

        else { secondsString = "" + seconds;  }

        finaTimerString = finaTimerString + minutes+ ":" + secondsString;

        //return timer
        return finaTimerString;
    }

    /**Function to get progress percentage
     *@param currentDuration
     * @param totalDuration
     * */
    public int getProgressPercentage (long currentDuration, long totalDuration){
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        //calculating percnetage
        percentage = (((double) currentSeconds) / totalSeconds)*100;

        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     * @param progress
     * @param totalDuration
     * returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration){
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);

        //return current duration in milliseconds
        return currentDuration * 1000;
    }

}
