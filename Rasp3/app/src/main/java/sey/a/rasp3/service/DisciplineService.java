package sey.a.rasp3.service;

import java.util.ArrayList;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.raw.RawDiscipline;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.shell.Xmls;
import sey.a.rasp3.ui.menu.MenuItems;

public class DisciplineService implements CRUD<Discipline, RawDiscipline> {
    private static Long maxId = 0L;

    @Override
    public MenuItems getMenuItems(Discipline discipline) {
        MenuItems items = new MenuItems();
        items.setMoreDetails(MenuItems.MORE_DETAILS_OFF)
                .setUpdate(MenuItems.UPDATE_ON)
                .setDelete(MenuItems.DELETE_ON);
        if(discipline.getHide()==0){
            items.setHide(MenuItems.HIDE_ON)
                    .setShow(MenuItems.SHOW_OFF);
        }else {
            items.setHide(MenuItems.HIDE_OFF)
                    .setShow(MenuItems.SHOW_ON);
        }
        return items;
    }

    @Override
    public Discipline create(Schedule schedule, RawDiscipline raw) {
        Discipline discipline = new Discipline();
        discipline.setId(++maxId);
        discipline.setSchedule(schedule);
        schedule.getDisciplines().add(discipline);
        discipline.setName(raw.getName());
        discipline.setShortName(raw.getShortName());
        discipline.setComment(raw.getComment());
        discipline.setHide(0);
        discipline.setLessons(new ArrayList<Lesson>());
        return discipline;
    }

    @Override
    public Discipline fastCreate(Schedule schedule, RawDiscipline raw) {
        return null;
    }

    @Override
    public RawDiscipline wet(Discipline discipline) {
        RawDiscipline raw = new RawDiscipline();
        raw.setName(discipline.getName());
        raw.setShortName(discipline.getShortName());
        raw.setComment(discipline.getComment());
        return raw;
    }

    @Override
    public Discipline update(Discipline discipline, RawDiscipline rawDiscipline) {
        discipline.setName(rawDiscipline.getName());
        discipline.setShortName(rawDiscipline.getShortName());
        discipline.setComment(rawDiscipline.getComment());
        return discipline;
    }

    @Override
    public Discipline hide(Discipline discipline, boolean b) {
        discipline.setHide(b?1:0);
        return discipline;
    }

    @Override
    public void delete(Discipline discipline) {
        while(discipline.getLessons().size()>0){
            General.delete(discipline.getLessons().get(0));
        }
        discipline.getSchedule().getDisciplines().remove(discipline);
    }

    @Override
    public String toXML(Discipline discipline) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", discipline.getId().toString()));
        xml.append(Xmls.stringToXml("fullName", discipline.getName()));
        xml.append(Xmls.stringToXml("shortName", discipline.getShortName()));
        xml.append(Xmls.stringToXml("comment", discipline.getComment()));
        xml.append(Xmls.stringToXml("hide", discipline.getHide().toString()));
        return xml.toString();
    }

    @Override
    public Discipline fromXML(String text, Schedule schedule) {
        Discipline discipline = new Discipline();
        discipline.setSchedule(schedule);
        discipline.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, discipline.getId());
        discipline.setName(Xmls.extractString("fullName", text));
        discipline.setShortName(Xmls.extractString("shortName", text));
        discipline.setComment(Xmls.extractString("comment", text));
        discipline.setHide(Xmls.extractInteger("hide", text));
        discipline.setLessons(new ArrayList<Lesson>());
        return discipline;
    }
}
