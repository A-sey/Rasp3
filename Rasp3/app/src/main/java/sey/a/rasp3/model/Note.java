package sey.a.rasp3.model;

import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;
import sey.a.rasp3.shell.Xmls;

@Setter
@Getter
public class Note {
    public static int CANCELED = 0;
    public static int PLANNED = 1;
    public static int TYPE = 2;
    public static int DISCIPLINE = 3;
    public static int TEACHER = 4;
    public static int START_TIME = 5;
    public static int END_TIME = 6;
    public static int AUDITORIUM = 7;
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
