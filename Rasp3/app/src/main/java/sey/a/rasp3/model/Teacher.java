package sey.a.rasp3.model;

import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Teacher extends Default {
    // ID
    private Long id;
    // Dependency
    private Schedule schedule;
    // Params
    private String name;
    private String shortName;
    private String comment;
    private Integer hide;
    // Depended
    private List<Lesson> lessons;

    public static Comparator<Teacher> nameComparator = new Comparator<Teacher>() {
        @Override
        public int compare(Teacher t1, Teacher t2) {
            if (t1.getName().equals(t2.getName()))
                return 0;
            return t1.getName().compareTo(t2.getName());
        }
    };
}
