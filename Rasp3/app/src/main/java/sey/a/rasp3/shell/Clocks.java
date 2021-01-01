package sey.a.rasp3.shell;

import androidx.annotation.NonNull;

import lombok.Getter;

@Getter
public class Clocks {
    private int hour;
    private int minute;

    public Clocks(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Clocks(String text) {
        String[] part = text.split(":");
        this.hour = Integer.parseInt(part[0]);
        this.minute = Integer.parseInt(part[1]);
    }

    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public void setHour(int hour) {
        if (hour >= 0) {
            this.hour = hour % 24;
        }
    }

    public void setMinute(int minute) {
        if (minute >= 0) {
            this.minute = minute % 60;
        }
    }

    public boolean isAfter(Clocks clocks) {
        if (clocks.getHour() == hour) {
            return clocks.getMinute() > minute;
        } else {
            return clocks.getHour() > hour;
        }
    }

    public boolean isBefore(Clocks clocks) {
        if (clocks.getHour() == hour) {
            return clocks.getMinute() < minute;
        } else {
            return clocks.getHour() < hour;
        }
    }

    public boolean isEqual(Clocks clocks) {
        return clocks.getHour() == hour && clocks.getMinute() == minute;
    }

    @NonNull
    @Override
    public String toString() {
        return twoDigits(hour)+":"+twoDigits(minute);
    }

    public static String twoDigits(int a){
        if(a/10>0){
            return Integer.toString(a);
        }else {
            return "0"+ a;
        }
    }
}
