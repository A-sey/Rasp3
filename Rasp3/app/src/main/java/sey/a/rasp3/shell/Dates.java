package sey.a.rasp3.shell;

import java.util.Calendar;
import java.util.GregorianCalendar;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Dates {
    private Calendar scheduleStart;
    private Calendar start;
    private Calendar end;
    private Integer weekType;
    private Integer dayOfWeek;

    public static String dateToString(Calendar calendar) {
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        if (day.length() < 2) {
            day = "0" + day;
        }
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        if (month.length() < 2) {
            month = "0" + month;
        }
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        return day + "." + month + "." + year;
    }

    public static Calendar parseDate(String text, String delimiter) {
        try {
            String[] part = text.split(delimiter);
            int day = Integer.parseInt(part[0]);
            int month = Integer.parseInt(part[1]) - 1;
            int year = Integer.parseInt(part[2]);
            Calendar calendar = new GregorianCalendar(year, month, day);
            return calendar;
        } catch (Exception e) {
            return null;
        }
    }

    public static int getWeekOfYear(Calendar date) {
        int offset = 0;
        if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            offset = /*1*/0;
        }
        return date.get(Calendar.WEEK_OF_YEAR) - offset;
    }

    public static int weeksDiff(Calendar first, Calendar second) {
        int w1 = first.get(Calendar.WEEK_OF_YEAR);
        int y1 = first.get(Calendar.YEAR);
        int w2 = second.get(Calendar.WEEK_OF_YEAR);
        int y2 = second.get(Calendar.YEAR);
        if (first.get(Calendar.MONTH) == Calendar.DECEMBER && w1 < 10) {
            w1 += 52;
        }
        if (second.get(Calendar.MONTH) == Calendar.DECEMBER && w2 < 10) {
            w2 += 52;
        }
        return (y2 - y1) * 52 + (w2 - w1) + 1;
    }

    public static int daysDiff(Calendar first, Calendar second) {
        Calendar f = new GregorianCalendar();
        f.set(Calendar.YEAR, first.get(Calendar.YEAR));
        f.set(Calendar.DAY_OF_YEAR, first.get(Calendar.DAY_OF_YEAR));
        Calendar s = new GregorianCalendar();
        s.set(Calendar.YEAR, second.get(Calendar.YEAR));
        s.set(Calendar.DAY_OF_YEAR, second.get(Calendar.DAY_OF_YEAR));
        long milliseconds = s.getTime().getTime() - f.getTime().getTime();
        return (int) (milliseconds / (24 * 60 * 60 * 1000));
    }

    public Dates(Calendar start, Calendar end, Integer weekType, Integer dayOfWeek) {
        this.start = start;
        this.end = end;
        this.weekType = weekType;
        this.dayOfWeek = dayOfWeek;
    }
}
