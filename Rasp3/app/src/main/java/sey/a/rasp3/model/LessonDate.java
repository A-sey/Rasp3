package sey.a.rasp3.model;

import java.time.LocalDate;
import java.util.Calendar;
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
}
