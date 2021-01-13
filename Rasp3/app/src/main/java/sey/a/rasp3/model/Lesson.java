package sey.a.rasp3.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Lesson extends Default {
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
    private String auditorium;
    // Depended
    private List<LessonDate> lessonDates;
}
