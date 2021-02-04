package sey.a.rasp3.model;

import android.graphics.Color;

import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Type extends Default {
    // ID
    private Long id;
    // Dependency
    private Schedule schedule;
    // Params
    private String name;
    private Integer hide;
    private Integer color;
    // Depended
    private List<Lesson> lessons;

    public static Comparator<Type> nameComparator = new Comparator<Type>() {
        @Override
        public int compare(Type t1, Type t2) {
            if (t1.getName().equals(t2.getName()))
                return 0;
            return t1.getName().compareTo(t2.getName());
        }
    };
}
