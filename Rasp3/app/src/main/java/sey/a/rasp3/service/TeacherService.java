package sey.a.rasp3.service;

import java.time.chrono.IsoChronology;
import java.util.ArrayList;

import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.raw.RawTeacher;
import sey.a.rasp3.raw.RawType;
import sey.a.rasp3.shell.Xmls;

public class TeacherService implements CRUD<Teacher, RawTeacher> {
    private static Long maxId = 0L;

    @Override
    public Teacher create(Schedule schedule, RawTeacher raw) {
        Teacher teacher = new Teacher();
        teacher.setId(++maxId);
        teacher.setSchedule(schedule);
        schedule.getTeachers().add(teacher);
        teacher.setName(raw.getName());
        teacher.setShortName(raw.getShortName());
        teacher.setComment(raw.getComment());
        teacher.setHide(0);
        teacher.setLessons(new ArrayList<Lesson>());
        return teacher;
    }

    @Override
    public Teacher fastCreate(Schedule schedule, RawTeacher rawTeacher) {
        return null;
    }

    @Override
    public RawTeacher wet(Teacher teacher) {
        RawTeacher raw = new RawTeacher();
        raw.setName(teacher.getName());
        raw.setShortName(teacher.getShortName());
        raw.setComment(teacher.getComment());
        return raw;
    }

    @Override
    public Teacher update(Teacher teacher, RawTeacher rawTeacher) {
        teacher.setName(rawTeacher.getName());
        teacher.setShortName(rawTeacher.getShortName());
        teacher.setComment(rawTeacher.getComment());
        return teacher;
    }

    @Override
    public Teacher hide(Teacher teacher, boolean b) {
        return null;
    }

    @Override
    public void delete(Teacher teacher) {
        for(Lesson l: teacher.getLessons()){
            l.getTeachers().remove(teacher);
        }
        teacher.getSchedule().getTeachers().remove(teacher);
    }

    public String toXML(Teacher teacher) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", teacher.getId().toString()));
        xml.append(Xmls.stringToXml("fullName", teacher.getName()));
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
        teacher.setName(Xmls.extractString("fullName", text));
        teacher.setShortName(Xmls.extractString("shortName", text));
        teacher.setComment(Xmls.extractString("comment", text));
        teacher.setHide(Xmls.extractInteger("hide", text));
        teacher.setLessons(new ArrayList<Lesson>());
        return teacher;
    }
}
