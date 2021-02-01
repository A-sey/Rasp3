package sey.a.rasp3.model;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

import lombok.Getter;
import lombok.Setter;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.Xmls;

@Setter
@Getter
public class Note extends Default{
    public static final int CANCELED = 0;
    public static final int PLANNED = 1;
    public static final int TYPE = 2;
    public static final int DISCIPLINE = 3;
    public static final int TEACHER = 4;
    public static final int TIME = 5;
    public static final int AUDITORIUM = 6;
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

    public static Comparator<Note> timeComparator = new Comparator<Note>() {
        @Override
        public int compare(Note n1, Note n2) {
            return n1.getDateTime().compareTo(n2.getDateTime());
        }
    };

    @NonNull
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String s = sdf.format(dateTime.getTime());
        s+= "; \t" + getActivityText();
        if(value!=null && !value.equals("")) {
            s += ": \t" + value;
        }
        if(text!=null && !text.equals("")){
            s+=";\n \t" + text;
        }
        return s;
    }

    public String getActivityText(){
        switch (activity){
            case CANCELED: return "Пара отменена";
            case PLANNED: return "Пара запланирована";
            case TYPE: return "Изменён тип";
            case DISCIPLINE: return "Изменён предмет";
            case TEACHER: return "Изменён преподаватель";
            case TIME: return "Изменено время";
            case AUDITORIUM: return "Изменена аудитория";
            default: return "";
        }
    }
}
