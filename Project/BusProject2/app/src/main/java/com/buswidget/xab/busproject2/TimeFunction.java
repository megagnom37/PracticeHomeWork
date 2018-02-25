package com.buswidget.xab.busproject2;
import java.util.ArrayList;

/**
 * Created by ivanskripacev on 25.02.18.
 */

public class TimeFunction {

    public static final int LAST = 0;
    public static final int NEXT = 1;
    public static final int AFTER_NEXT = 2;

    public static final int MIN_IN_24H = 1440;

    public static Integer ConvertTimeToInt(String time){
        Integer result;

        String[] hoursAndMinutes = time.split(":");
        if(hoursAndMinutes.length < 2) return -1;

        int hours = Integer.parseInt(hoursAndMinutes[0]) * 60;
        if(hours >= MIN_IN_24H || hours < 0) return -2;

        int minutes = Integer.parseInt(hoursAndMinutes[1]);
        if(minutes > 59 || minutes < 0) return -2;

        result = hours + minutes;

        return result;
    }

    public static String ConvertIntToTime(Integer time){
        String result = new String("");

        if(Math.abs(time) >= MIN_IN_24H) return "uncorrect";

        Integer hours = time / 60;
        Integer minutes = time % 60;

        if (Math.abs(hours) > 0){
            minutes = Math.abs(minutes);
            result += hours.toString() + "h ";
            result += minutes.toString() + "m";
        }else{
            result += minutes.toString() + "m";
        }

        return result;
    }

    public static Integer CalcDiffOfTime(Integer time, Integer curTime){
        Integer result;

        if(Math.abs(time) >= MIN_IN_24H) return -1;
        if(Math.abs(curTime) >= MIN_IN_24H) return -2;

        if (time < curTime){
            result = (time + MIN_IN_24H) - curTime;
        }
        else{
            result = time - curTime;
        }

        return result;
    }

    public static Integer FoundBusTime(ArrayList<Integer> timesArray, Integer curTime, int typeTime){
        Integer result = -1;

        if(curTime >= MIN_IN_24H || curTime < 0) return -1;
        if(typeTime > 3 || typeTime < 0) return -2;
        if(timesArray.isEmpty()) return -3;

        for(int i=(timesArray.size()-1); i >= 0; i--){
            if(timesArray.get(i) <= curTime){
                result = i;
                break;
            }
        }

        if(result == -1){
            result = timesArray.size()-1;
        }

        if(typeTime == NEXT){
            result++;
        }

        if(typeTime == AFTER_NEXT){
            result += 2;
        }

        if(result > (timesArray.size()-1))
            result -= (timesArray.size());

        return result;
    }
}
