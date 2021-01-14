package sey.a.rasp3.service;

import java.util.ArrayList;
import java.util.Calendar;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawLesson;
import sey.a.rasp3.shell.Xmls;

public class LessonService implements CRUD<Lesson, RawLesson> {
    private static LessonDateService lessonDateService = new LessonDateService();
    private static Long maxId = 0L;

    public Lesson create(Schedule schedule, RawLesson raw) {
        Lesson lesson = new Lesson();
        lesson.setId(++maxId);
        lesson.setSchedule(schedule);
        schedule.getLessons().add(lesson);
        lesson.setDiscipline(raw.getDiscipline());
        raw.getDiscipline().getLessons().add(lesson);
        lesson.setTeachers(raw.getTeachers());
        for(Teacher t: raw.getTeachers()){
            t.getLessons().add(lesson);
        }
        lesson.setType(raw.getType());
        raw.getType().getLessons().add(lesson);
        lesson.setTime(raw.getTime());
        raw.getTime().getLessons().add(lesson);
        lesson.setAuditorium(raw.getAuditorium());
        lesson.setHide(0);
        lesson.setLessonDates(new ArrayList<LessonDate>());
        if (raw.getDates().size() == 0) {
            raw.generateDates(schedule.getStartDate());
        }
        for (Calendar d : raw.getDates()) {
            lesson.getLessonDates().add(lessonDateService.create(lesson, d));
        }
        return lesson;
    }

    @Override
    public Lesson fastCreate(Schedule schedule, RawLesson rawLesson) {
        return null;
    }

    @Override
    public RawLesson wet(Lesson lesson) {
        return null;
    }

    @Override
    public Lesson update(Lesson lesson, RawLesson rawLesson) {
        return null;
    }

    @Override
    public Lesson hide(Lesson lesson, boolean b) {
        lesson.setHide(b?1:0);
        return lesson;
    }

    @Override
    public void delete(Lesson lesson) {
        for(LessonDate ld: lesson.getLessonDates()){
            lessonDateService.delete(ld);
        }
        lesson.getDiscipline().getLessons().remove(lesson);
        lesson.getTime().getLessons().remove(lesson);
        lesson.getType().getLessons().remove(lesson);
        for(Teacher t: lesson.getTeachers()){
            t.getLessons().remove(lesson);
        }
        lesson.getSchedule().getLessons().remove(lesson);
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
        xml.append(Xmls.stringToXml("auditorium", lesson.getAuditorium()));
        for (LessonDate ld : lesson.getLessonDates()) {
            xml.append(Xmls.stringToXml("lessonDate", lessonDateService.toXml(ld)));
        }
        return xml.toString();
    }


    public Lesson fromXML(String text, Schedule schedule) {
        Lesson lesson = new Lesson();
        lesson.setSchedule(schedule);
        lesson.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, lesson.getId());
        lesson.setHide(Xmls.extractInteger("hide", text));
        lesson.setAuditorium(Xmls.extractString("auditorium", text));

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
            lesson.getLessonDates().add(lessonDateService.fromXml(ld, lesson));
        }
        return lesson;
    }


}
