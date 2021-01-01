package sey.a.rasp3.model;

import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;
import sey.a.rasp3.shell.Xmls;

@Setter
@Getter
public class Note {
    // ID
    private Long id;
    // Dependency
    private LessonDate lessonDate;
    // Params
    private Calendar dateTime;
    private int activity;
    private String value;
    private String text;
    private Integer hide;

    // Depended
    // ...

    public Note() {

    }
}
