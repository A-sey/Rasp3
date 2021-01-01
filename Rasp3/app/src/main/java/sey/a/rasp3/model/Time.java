package sey.a.rasp3.model;

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
}
