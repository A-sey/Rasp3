package sey.a.rasp3.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher {
    // ID
    private Long id;
    // Dependency
    private Schedule schedule;
    // Params
    private String fullName;
    private String shortName;
    private String comment;
    private Integer hide;
    // Depended
    private List<Lesson> lessons;

}
