package sey.a.rasp3.model;

import java.util.Comparator;
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

    public static Comparator<Discipline> nameComparator = new Comparator<Discipline>() {
        @Override
        public int compare(Discipline d1, Discipline d2) {
            if(d1.getFullName().equals(d2.getFullName()))
                return 0;
            return d1.getFullName().compareTo(d2.getFullName());
        }
    };
}
