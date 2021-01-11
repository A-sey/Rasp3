package sey.a.rasp3.model;

import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import sey.a.rasp3.raw.RawDefault;

@Setter
@Getter
public class Discipline extends Default {
    // Dependency
    Schedule schedule;
    // ID
    private Long id;
    // Params
    private String name;
    private String shortName;
    private String comment;
    private Integer hide;
    // Depended
    private List<Lesson> lessons;

    public static Comparator<Discipline> nameComparator = new Comparator<Discipline>() {
        @Override
        public int compare(Discipline d1, Discipline d2) {
            if(d1.getName().equals(d2.getName()))
                return 0;
            return d1.getName().compareTo(d2.getName());
        }
    };
}
