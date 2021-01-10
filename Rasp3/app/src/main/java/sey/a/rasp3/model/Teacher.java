package sey.a.rasp3.model;

import java.util.Comparator;
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

    public static Comparator<Teacher> nameComparator = new Comparator<Teacher>() {
        @Override
        public int compare(Teacher t1, Teacher t2) {
            if(t1.getFullName().equals(t2.getFullName()))
                return 0;
            return t1.getFullName().compareTo(t2.getFullName());
        }
    };
}
