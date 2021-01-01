package sey.a.rasp3.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Type {
    // ID
    private Long id;
    // Dependency
    private Schedule schedule;
    // Params
    private String name;
    private Integer hide;
    // Depended
    private List<Lesson> lessons;
}
