package sey.a.rasp3.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import sey.a.rasp3.shell.Dates;

@Getter
@Setter
public class RawLesson {
    private List<Calendar> dates;
    private Calendar dateFrom;
    private Calendar dateTo;
    private int weekType;
    private int dayOfWeek;
    private Time time;
    private Type type;
    private Discipline discipline;
    private List<Teacher> teachers;
    private String auditorium;

    public RawLesson() {
        teachers = new ArrayList<>();
        dates = new ArrayList<>();
    }

    public boolean validate() {
        if (time == null)
            return false;
        if (type == null)
            return false;
        if (discipline == null)
            return false;
        if (teachers == null)
            return false;
        if (dates == null || dates.size() == 0) {
            if (weekType < 0 || weekType > 2)
                return false;
            if (dayOfWeek < 1 || dayOfWeek > 7)
                return false;
            if (dateFrom == null || dateTo == null)
                return false;
            if (dateTo.before(dateFrom))
                return false;
        }
        return true;
    }

    public void generateDates(Calendar scheduleStart) {
        int firstWeekNumber = Dates.getWeekOfYear(scheduleStart);
        int step = 400;
        if (weekType == 0) {
            step = 7;
        } else if (weekType == 1 || weekType == 2) {
            step = 14;
        }
        Calendar date = dateFrom;
        // find first
        while(!date.after(dateTo)){
            if(checkDate(date, Dates.getWeekOfYear(date)-firstWeekNumber+1)){
                break;
            }
            date.add(Calendar.DAY_OF_YEAR, 1);
        }
        // find the rest
        while (!date.after(dateTo)) {
            dates.add((Calendar)date.clone());
            date.add(Calendar.DAY_OF_YEAR, step);
        }
    }

    private boolean checkDate(Calendar date, int weekNumber) {
        if (weekType == 0 || (weekNumber % 2) == (weekType % 2)) {
            return date.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
        }
        return false;
    }
}
