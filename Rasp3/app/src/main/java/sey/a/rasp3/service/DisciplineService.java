package sey.a.rasp3.service;

import java.util.ArrayList;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.shell.Xmls;

public class DisciplineService {
    private static Long maxId = 0L;

    public Discipline create(Schedule schedule, String fullName, String shortName, String comment) {
        Discipline discipline = new Discipline();
        discipline.setId(++maxId);
        discipline.setSchedule(schedule);
        schedule.getDisciplines().add(discipline);
        discipline.setFullName(fullName);
        discipline.setShortName(shortName);
        discipline.setComment(comment);
        discipline.setHide(0);
        discipline.setLessons(new ArrayList<Lesson>());
        return discipline;
    }

    public Discipline fastCreate() {
        return null;
    }

    public Discipline update() {
        return null;
    }

    public boolean hide() {
        return false;
    }

    public void delete() {

    }

    public String toXML(Discipline discipline) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", discipline.getId().toString()));
        xml.append(Xmls.stringToXml("fullName", discipline.getFullName()));
        xml.append(Xmls.stringToXml("shortName", discipline.getShortName()));
        xml.append(Xmls.stringToXml("comment", discipline.getComment()));
        xml.append(Xmls.stringToXml("hide", discipline.getHide().toString()));
        return xml.toString();
    }

    public Discipline fromXML(String text, Schedule schedule) {
        Discipline discipline = new Discipline();
        discipline.setSchedule(schedule);
        discipline.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, discipline.getId());
        discipline.setFullName(Xmls.extractString("fullName", text));
        discipline.setShortName(Xmls.extractString("shortName", text));
        discipline.setComment(Xmls.extractString("comment", text));
        discipline.setHide(Xmls.extractInteger("hide", text));
        discipline.setLessons(new ArrayList<Lesson>());
        return discipline;
    }
}
