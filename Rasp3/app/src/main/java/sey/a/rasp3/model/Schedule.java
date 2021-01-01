package sey.a.rasp3.model;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Schedule {
    // ID
    private Long id;
    // Dependency
    // ...
    // Params
    private String name;
    private Calendar startDate;
    private Calendar endDate;
    private Integer hide;
    // Depended
    private List<Discipline> disciplines;
    private List<Teacher> teachers;
    private List<Time> times;
    private List<Type> types;
    private List<Lesson> lessons;
}
