package sey.a.rasp3.service;

import android.util.Xml;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.shell.Xmls;

public class ScheduleService {
    private static Long maxId = 0L;

    public Schedule create(String name, Calendar startDate, Calendar endDate) {
        Schedule schedule = new Schedule();
        schedule.setId(++maxId);
        schedule.setName(name);
        schedule.setStartDate(startDate);
        schedule.setEndDate(endDate);
        schedule.setHide(0);
        schedule.setDisciplines(new ArrayList<Discipline>());
        schedule.setTeachers(new ArrayList<Teacher>());
        schedule.setTypes(new ArrayList<Type>());
        schedule.setTimes(new ArrayList<Time>());
        schedule.setLessons(new ArrayList<Lesson>());
        return schedule;
    }

    public Schedule fastCreate() {
        return null;
    }

    public Schedule update() {
        return null;
    }

    public boolean hide() {
        return false;
    }

    public void delete() {

    }

    public String toXML(Schedule schedule) {
        DisciplineService disciplineService = new DisciplineService();
        TeacherService teacherService = new TeacherService();
        TypeService typeService = new TypeService();
        TimeService timeService = new TimeService();
        LessonService lessonService = new LessonService();
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", schedule.getId().toString()));
        xml.append(Xmls.stringToXml("name", schedule.getName()));
        xml.append(Xmls.dateToXml("startDate", schedule.getStartDate()));
        xml.append(Xmls.dateToXml("endDate", schedule.getEndDate()));
        xml.append(Xmls.stringToXml("hide", schedule.getHide().toString()));
        for (Discipline d : schedule.getDisciplines()) {
            xml.append(Xmls.stringToXml("discipline", disciplineService.toXML(d)));
        }
        for (Teacher t : schedule.getTeachers()) {
            xml.append(Xmls.stringToXml("teacher", teacherService.toXML(t)));
        }
        for (Type t : schedule.getTypes()) {
            xml.append(Xmls.stringToXml("type", typeService.toXML(t)));
        }
        for (Time t : schedule.getTimes()) {
            xml.append(Xmls.stringToXml("time", timeService.toXML(t)));
        }
        for (Lesson l : schedule.getLessons()) {
            xml.append(Xmls.stringToXml("lesson", lessonService.toXML(l)));
        }
        return xml.toString();
    }

    public Schedule fromXML(String text) {
        Schedule schedule = new Schedule();
        schedule.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, schedule.getId());
        schedule.setName(Xmls.extractString("name", text));
        schedule.setStartDate(Xmls.extractDate("startDate", text));
        schedule.setEndDate(Xmls.extractDate("endDate", text));
        schedule.setHide(Xmls.extractInteger("hide", text));

        List<Discipline> disciplines = new ArrayList<>();
        DisciplineService disciplineService = new DisciplineService();
        for (String d : Xmls.extractStringList("discipline", text)) {
            disciplines.add(disciplineService.fromXML(d, schedule));
        }
        schedule.setDisciplines(disciplines);

        List<Teacher> teachers = new ArrayList<>();
        TeacherService teacherService = new TeacherService();
        for (String t : Xmls.extractStringList("teacher", text)) {
            teachers.add(teacherService.fromXML(t, schedule));
        }
        schedule.setTeachers(teachers);

        List<Type> types = new ArrayList<>();
        TypeService typeService = new TypeService();
        for (String t : Xmls.extractStringList("type", text)) {
            types.add(typeService.fromXML(t, schedule));
        }
        schedule.setTypes(types);

        List<Time> times = new ArrayList<>();
        TimeService timeService = new TimeService();
        for (String t : Xmls.extractStringList("time", text)) {
            times.add(timeService.fromXML(t, schedule));
        }
        schedule.setTimes(times);

        List<Lesson> lessons = new ArrayList<>();
        LessonService lessonService = new LessonService();
        for (String l : Xmls.extractStringList("lesson", text)) {
            lessons.add(lessonService.fromXML(l, schedule));
        }
        schedule.setLessons(lessons);

        return schedule;
    }
}
