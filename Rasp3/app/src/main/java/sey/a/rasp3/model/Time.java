package sey.a.rasp3.model;

import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import sey.a.rasp3.shell.Clocks;

@Setter
@Getter
public class Time {
    // ID
    private Long id;
    // Dependency
    private Schedule schedule;
    // Params
    private String name;
    private Clocks startTime;
    private Clocks endTime;
    private Integer hide;
    // Depended
    private List<Lesson> lessons;

    // компаратор сортирует список или массив объектов по возрасту
    public static Comparator<Time> startTimeComparator = new Comparator<Time>() {
        @Override
        public int compare(Time t1, Time t2) {
            if(t1.getStartTime().equals(t2.getStartTime()))
                return 0;
            return t1.getStartTime().isBefore(t2.getStartTime())?1:-1;
        }
    };
}
