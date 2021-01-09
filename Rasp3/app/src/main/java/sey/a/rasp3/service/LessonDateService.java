package sey.a.rasp3.service;

import java.util.ArrayList;
import java.util.Calendar;

import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.shell.Xmls;

public class LessonDateService {
    private static Long maxId = 0L;

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

    public LessonDate update() {
        return null;
    }

    public boolean hide() {
        return false;
    }

    public void delete() {

    }

    public String toXml(LessonDate lessonDate) {
        NoteService noteService = new NoteService();
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
        ld.setNotes(new ArrayList<Note>());
        return ld;
    }
}
