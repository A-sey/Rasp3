package sey.a.rasp3.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Lesson {
    // ID
    private Long id;
    // Dependency
    private Schedule schedule;
    private Discipline discipline;
    private List<Teacher> teachers;
    private Time time;
    private Type type;
    private Integer hide;
    // Params
    // ...
    // Depended
    private List<LessonDate> lessonDates;
}
