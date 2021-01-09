package sey.a.rasp3.shell;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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
        String[] part = text.split(delimiter);
        int day = Integer.parseInt(part[0]);
        int month = Integer.parseInt(part[1]) - 1;
        int year = Integer.parseInt(part[2]);
        Calendar calendar = new GregorianCalendar(year, month, day);
        return calendar;
    }

    public static int getWeekOfYear(Calendar date) {
        int offset = 0;
        if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            offset = /*1*/0;
        }
        return date.get(Calendar.WEEK_OF_YEAR) - offset;
    }

    public Dates(Calendar start, Calendar end, Integer weekType, Integer dayOfWeek) {
        this.start = start;
        this.end = end;
        this.weekType = weekType;
        this.dayOfWeek = dayOfWeek;
    }

    private List<Calendar> periodicityToDates() {
        List<Calendar> dates = new LinkedList<>();
        if (scheduleStart == null || start == null || end == null || weekType == null || dayOfWeek == null) {
            return dates;
        }
        int startWeek = scheduleStart.get(Calendar.WEEK_OF_YEAR);
        if (start.get(Calendar.DAY_OF_WEEK) < dayOfWeek) {
            start.add(Calendar.WEEK_OF_YEAR, 1);
        }
        start.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        /*if(end.get(Calendar.DAY_OF_WEEK)>dayOfWeek){
            end.add(Calendar.WEEK_OF_YEAR, -1);
        }
        end.set(Calendar.DAY_OF_WEEK, dayOfWeek);*/


        return dates;
    }
}
