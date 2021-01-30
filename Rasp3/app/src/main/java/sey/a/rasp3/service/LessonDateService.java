package sey.a.rasp3.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.raw.RawLessonDate;
import sey.a.rasp3.shell.Clocks;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.Xmls;

public class LessonDateService {
    private static Long maxId = 0L;
    NoteService noteService = new NoteService();

    public LessonDate create(Lesson lesson, Calendar date) {
        LessonDate ld = new LessonDate();
        ld.setId(++maxId);
        ld.setLesson(lesson);
        ld.setDate(date);
        ld.setHide(0);
        ld.setNotes(new ArrayList<Note>());
        return ld;
    }

    public LessonDate fastCreate() {
        return null;
    }

    public RawLessonDate wet(LessonDate ld) {
        Lesson lesson = ld.getLesson();
        Note lastStatusNote = ld.getLastStatusNote();
        Note lastTypeNote = ld.getLastNoteWithActivity(Note.TYPE);
        Note lastDisciplineNote = ld.getLastNoteWithActivity(Note.DISCIPLINE);
        Note lastTeachersNote = ld.getLastNoteWithActivity(Note.TEACHER);
        Note lastAuditoriumNote = ld.getLastNoteWithActivity(Note.AUDITORIUM);

        RawLessonDate raw = new RawLessonDate();
        if (lastStatusNote == null || lastStatusNote.getActivity() == Note.PLANNED) {
            Calendar thisTime = Calendar.getInstance();
            int daysDiff = Dates.daysDiff(thisTime, ld.getDate());
            if (daysDiff < 0) {
                raw.setCondition("Была");
            } else if (daysDiff > 0) {
                raw.setCondition("Будет");
            } else {
                Clocks now = new Clocks(thisTime.get(Calendar.HOUR_OF_DAY), thisTime.get(Calendar.MINUTE));
                if (now.isAfter(lesson.getTime().getStartTime())) {
                    raw.setCondition("Будет");
                } else if (now.isBefore(lesson.getTime().getEndTime())) {
                    raw.setCondition("Была");
                } else {
                    raw.setCondition("Идёт");
                }
            }
        } else {
            raw.setCondition("Не будет");
        }
        raw.setLessonTime(lesson.getTime().getName());
        raw.setStartTime(lesson.getTime().getStartTime().toString());
        raw.setEndTime(lesson.getTime().getEndTime().toString());
        if (lastTypeNote == null) {
            raw.setLessonType(lesson.getType().getName());
        } else {
            raw.setLessonType(lastTypeNote.getValue());
        }
        if (lastDisciplineNote == null) {
            raw.setLessonDiscipline(lesson.getDiscipline().getShortName());
        } else {
            raw.setLessonDiscipline(lastDisciplineNote.getValue());
        }
        if (lastTeachersNote == null) {
            StringBuilder teachersText = new StringBuilder();
            for (Teacher t : lesson.getTeachers()) {
                teachersText.append(t.getShortName()).append("\n");
            }
            raw.setTeachers(teachersText.toString().trim());
        } else {
            raw.setTeachers(lastTeachersNote.getValue());
        }
        if (lastAuditoriumNote == null) {
            raw.setAuditorium(lesson.getAuditorium());
        } else {
            raw.setAuditorium(lastAuditoriumNote.getValue());
        }
        return raw;
    }

    public LessonDate update() {
        return null;
    }

    public boolean hide() {
        return false;
    }

    public void delete(LessonDate lessonDate) {
        lessonDate.getLesson().getLessonDates().remove(lessonDate);
    }

    public String toXml(LessonDate lessonDate) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", lessonDate.getId().toString()));
        xml.append(Xmls.dateToXml("date", lessonDate.getDate()));
        xml.append(Xmls.stringToXml("hide", lessonDate.getHide().toString()));
        for (Note n : lessonDate.getNotes()) {
            xml.append(Xmls.stringToXml("note", noteService.toXML(n)));
        }
        return xml.toString();
    }

    public LessonDate fromXml(String text, Lesson lesson) {
        LessonDate ld = new LessonDate();
        ld.setLesson(lesson);
        ld.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, ld.getId());
        ld.setHide(Xmls.extractInteger("hide", text));
        ld.setDate(Xmls.extractDate("date", text));
        List<Note> notes = new ArrayList<>();
        for (String n : Xmls.extractStringList("note", text)) {
            notes.add(noteService.fromXML(n, ld));
        }
        ld.setNotes(notes);
        return ld;
    }
}
