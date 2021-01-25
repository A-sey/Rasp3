package sey.a.rasp3.service;

import java.util.ArrayList;
import java.util.List;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawSchedule;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.shell.Xmls;
import sey.a.rasp3.ui.menu.MenuItems;

public class ScheduleService implements CRUD<Schedule, RawSchedule> {
    private static Long maxId = 0L;

    @Override
    public MenuItems getMenuItems(Schedule schedule) {
        MenuItems items = new MenuItems();
        items.setMoreDetails(MenuItems.MORE_DETAILS_OFF)
                .setUpdate(MenuItems.UPDATE_ON)
                .setDelete(MenuItems.DELETE_ON);
        items.setHide(MenuItems.HIDE_OFF)
                .setShow(MenuItems.SHOW_OFF);
        return items;
    }

    public Schedule create(Schedule non, RawSchedule raw) {
        Schedule schedule = new Schedule();
        schedule.setId(++maxId);
        schedule.setName(raw.getName());
        schedule.setStartDate(raw.getStart());
        schedule.setEndDate(raw.getEnd());
        schedule.setHide(0);
        schedule.setDisciplines(new ArrayList<Discipline>());
        schedule.setTeachers(new ArrayList<Teacher>());
        schedule.setTypes(new ArrayList<Type>());
        schedule.setTimes(new ArrayList<Time>());
        schedule.setLessons(new ArrayList<Lesson>());
        General.getFiles().writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
        return schedule;
    }

    @Override
    public Schedule fastCreate(Schedule schedule, RawSchedule rawSchedule) {
        return null;
    }

    @Override
    public RawSchedule wet(Schedule schedule) {
        RawSchedule raw = new RawSchedule();
        raw.setName(schedule.getName());
        raw.setStart(schedule.getStartDate());
        raw.setEnd(schedule.getEndDate());
        return raw;
    }

    @Override
    public Schedule update(Schedule schedule, RawSchedule rawSchedule) {
        schedule.setName(rawSchedule.getName());
        schedule.setStartDate(rawSchedule.getStart());
        schedule.setEndDate(rawSchedule.getEnd());
        General.getFiles().writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
        return schedule;
    }

    @Override
    public Schedule hide(Schedule schedule, boolean b) {
        schedule.setHide(b ? 1 : 0);
        General.getFiles().writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
        return schedule;
    }

    @Override
    public void delete(Schedule schedule) {
        General.getFiles().removeFile(schedule.getName());
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

    public Schedule fromXML(String text, Schedule non) {
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
