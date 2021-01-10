package sey.a.rasp3.model;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonDate {
    // ID
    private Long id;
    // Dependency
    private Lesson lesson;
    // Params
    private Calendar date;
    private Integer hide;
    // Depended
    private List<Note> notes;

    public static Comparator<LessonDate> startTimeComparator = new Comparator<LessonDate>() {
        @Override
        public int compare(LessonDate ld1, LessonDate ld2) {
            Time t1 = ld1.getLesson().getTime();
            Time t2 = ld2.getLesson().getTime();
            if(t1.getStartTime().equals(t2.getStartTime()))
                return 0;
            return t1.getStartTime().isBefore(t2.getStartTime())?1:-1;
        }
    };
}
