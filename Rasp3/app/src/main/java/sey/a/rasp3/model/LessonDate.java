package sey.a.rasp3.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LessonDate {
    // ID
    private Long id;
    // Dependency
    private Lesson lesson;
    // Params
    private Calendar date;
    private Integer hide;
    // Depended
    private List<Note> notes;

    public List<Note> getNotesWithActivity(int activity){
        List<Note> notes = new ArrayList<>();
        for(Note n: this.notes){
            if(n.getActivity() == activity){
                notes.add(n);
            }
        }
        return notes;
    }

    public Note getLastNoteWithActivity(int activity){
        for(int i = notes.size()-1; i>=0; i--){
            if(notes.get(i).getActivity()==activity){
                return notes.get(i);
            }
        }
        return null;
    }

    public Note getLastStatusNote(){
        for(int i = notes.size()-1; i>=0; i--){
            if(notes.get(i).getActivity()==Note.CANCELED || notes.get(i).getActivity()==Note.PLANNED){
                return notes.get(i);
            }
        }
        return null;
    }

    public static Comparator<LessonDate> startTimeComparator = new Comparator<LessonDate>() {
        @Override
        public int compare(LessonDate ld1, LessonDate ld2) {
            Time t1 = ld1.getLesson().getTime();
            Time t2 = ld2.getLesson().getTime();
            if(t1.getStartTime().equals(t2.getStartTime()))
                return 0;
            return t1.getStartTime().isBefore(t2.getStartTime())?1:-1;
        }
    };

    /*public static Comparator<LessonDate> dateComparator = new Comparator<LessonDate>() {
        @Override
        public int compare(LessonDate ld1, LessonDate ld2) {
            Calendar d1 = ld1.getDate();
            Calendar d2 = ld2.getDate();
            return d1.compareTo(d2);
        }
    };*/
}
