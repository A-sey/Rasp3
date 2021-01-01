package sey.a.rasp3.service;

import java.util.ArrayList;

import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.shell.Xmls;

public class TeacherService {
    private static Long maxId = 0L;

    public Teacher create(Schedule schedule, String fullName, String shortName, String comment) {
        Teacher teacher = new Teacher();
        teacher.setId(++maxId);
        teacher.setSchedule(schedule);
        schedule.getTeachers().add(teacher);
        teacher.setFullName(fullName);
        teacher.setShortName(shortName);
        teacher.setComment(comment);
        teacher.setHide(0);
        teacher.setLessons(new ArrayList<Lesson>());
        return teacher;
    }

    public Teacher fastCreate() {
        return null;
    }

    public Teacher update() {
        return null;
    }

    public boolean hide() {
        return false;
    }

    public void delete() {

    }

    public String toXML(Teacher teacher) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", teacher.getId().toString()));
        xml.append(Xmls.stringToXml("fullName", teacher.getFullName()));
        xml.append(Xmls.stringToXml("shortName", teacher.getShortName()));
        xml.append(Xmls.stringToXml("comment", teacher.getComment()));
        xml.append(Xmls.stringToXml("hide", teacher.getHide().toString()));
        return xml.toString();
    }

    public Teacher fromXML(String text, Schedule schedule) {
        Teacher teacher = new Teacher();
        teacher.setSchedule(schedule);
        teacher.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, teacher.getId());
        teacher.setFullName(Xmls.extractString("fullName", text));
        teacher.setShortName(Xmls.extractString("shortName", text));
        teacher.setComment(Xmls.extractString("comment", text));
        teacher.setHide(Xmls.extractInteger("hide", text));
        teacher.setLessons(new ArrayList<Lesson>());
        return teacher;
    }
}
