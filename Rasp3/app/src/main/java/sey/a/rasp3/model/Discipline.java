package sey.a.rasp3.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Discipline {
    // Dependency
    Schedule schedule;
    // ID
    private Long id;
    // Params
    private String fullName;
    private String shortName;
    private String comment;
    private Integer hide;
    // Depended
    private List<Lesson> lessons;

}
