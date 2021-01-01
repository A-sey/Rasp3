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
        if(start.get(Calendar.DAY_OF_WEEK)<dayOfWeek){
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
