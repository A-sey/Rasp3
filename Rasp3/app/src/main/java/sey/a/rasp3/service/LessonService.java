package sey.a.rasp3.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.shell.Xmls;

public class LessonService {
    private static Long maxIdLesson = 0L;
    private static Long maxIdLessonDate = 0L;

    public Lesson create(Schedule schedule, List<Calendar> dates, Discipline discipline, List<Teacher> teachers, Type type, Time time, String auditorium) {
        return null;
    }

    public Lesson fastCreate() {
        return null;
    }

    public Lesson update() {
        return null;
    }

    public boolean hide() {
        return false;
    }

    public void delete() {

    }

    public String toXML(Lesson lesson) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", lesson.getId().toString()));
        xml.append(Xmls.stringToXml("disciplineId", lesson.getDiscipline().getId().toString()));
        for (Teacher t : lesson.getTeachers()) {
            xml.append(Xmls.stringToXml("teacherId", t.getId().toString()));
        }
        xml.append(Xmls.stringToXml("timeId", lesson.getTime().getId().toString()));
        xml.append(Xmls.stringToXml("typeId", lesson.getType().getId().toString()));
        xml.append(Xmls.stringToXml("hide", lesson.getHide().toString()));
        for (LessonDate ld : lesson.getLessonDates()) {
            xml.append(Xmls.stringToXml("lessonDate", lessonDateToXml(ld)));
        }
        return xml.toString();
    }

    private String lessonDateToXml(LessonDate lessonDate) {
        NoteService noteService = new NoteService();
        StringBuffer xml = new StringBuffer();
        xml.append(Xmls.stringToXml("id", lessonDate.getId().toString()));
        xml.append(Xmls.dateToXml("date", lessonDate.getDate()));
        xml.append(Xmls.stringToXml("hide", lessonDate.getHide().toString()));
        for (Note n : lessonDate.getNotes()) {
            xml.append(Xmls.stringToXml("note", noteService.toXML(n)));
        }
        return xml.toString();
    }

    public Lesson fromXML(String text, Schedule schedule) {
        Lesson lesson = new Lesson();
        lesson.setSchedule(schedule);
        lesson.setId(Xmls.extractLong("id", text));
        maxIdLesson = Math.max(maxIdLesson, lesson.getId());
        lesson.setHide(Xmls.extractInteger("hide", text));

        Long disciplineId = Xmls.extractLong("disciplineId", text);
        for (Discipline d : schedule.getDisciplines()) {
            if (d.getId().equals(disciplineId)) {
                d.getLessons().add(lesson);
                lesson.setDiscipline(d);
                break;
            }
        }

        lesson.setTeachers(new ArrayList<Teacher>());
        for (String s : Xmls.extractStringList("teacherId", text)) {
            Long teacherId = Long.parseLong(s);
            for (Teacher t : schedule.getTeachers()) {
                if (t.getId().equals(teacherId)) {
                    t.getLessons().add(lesson);
                    lesson.getTeachers().add(t);
                }
            }
        }

        Long timeId = Xmls.extractLong("timeId", text);
        for (Time t : schedule.getTimes()) {
            if (t.getId().equals(timeId)) {
                t.getLessons().add(lesson);
                lesson.setTime(t);
                break;
            }
        }

        Long typeId = Xmls.extractLong("typeId", text);
        for (Type t : schedule.getTypes()) {
            if (t.getId().equals(typeId)) {
                t.getLessons().add(lesson);
                lesson.setType(t);
                break;
            }
        }

        lesson.setLessonDates(new ArrayList<LessonDate>());
        for (String ld : Xmls.extractStringList("lessonDate", text)) {
            lesson.getLessonDates().add(lessonDateFromXml(text, lesson));
        }
        return lesson;
    }

    private LessonDate lessonDateFromXml(String text, Lesson lesson) {
        LessonDate ld = new LessonDate();
        ld.setLesson(lesson);
        ld.setId(Xmls.extractLong("id", text));
        maxIdLessonDate = Math.max(maxIdLessonDate, ld.getId());
        ld.setHide(Xmls.extractInteger("hide", text));
        ld.setDate(Xmls.extractDate("date", text));
        ld.setNotes(new ArrayList<Note>());
        return ld;
    }
}
