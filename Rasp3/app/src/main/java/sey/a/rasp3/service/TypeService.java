package sey.a.rasp3.service;

import android.graphics.Color;

import java.util.ArrayList;

import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawType;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.shell.Xmls;
import sey.a.rasp3.ui.menu.MenuItems;

public class TypeService implements CRUD<Type, RawType> {
    private static Long maxId = 0L;

    @Override
    public MenuItems getMenuItems(Type type) {
        MenuItems items = new MenuItems();
        items.setMoreDetails(MenuItems.MORE_DETAILS_OFF)
                .setUpdate(MenuItems.UPDATE_ON)
                .setDelete(MenuItems.DELETE_ON);
        if(type.getHide()==0){
            items.setHide(MenuItems.HIDE_ON)
                    .setShow(MenuItems.SHOW_OFF);
        }else {
            items.setHide(MenuItems.HIDE_OFF)
                    .setShow(MenuItems.SHOW_ON);
        }
        return items;
    }

    public Type create(Schedule schedule, RawType raw) {
        Type type = new Type();
        type.setId(++maxId);
        type.setSchedule(schedule);
        schedule.getTypes().add(type);
        type.setName(raw.getName());
        type.setHide(0);
        type.setLessons(new ArrayList<Lesson>());
        type.setColor(raw.getColor());
        return type;
    }

    @Override
    public Type fastCreate(Schedule schedule, RawType rawType) {
        return null;
    }

    @Override
    public RawType wet(Type type) {
        RawType raw = new RawType();
        raw.setName(type.getName());
        raw.setColor(type.getColor());
        return raw;
    }

    @Override
    public Type update(Type type, RawType rawType) {
        type.setName(rawType.getName());
        type.setColor(rawType.getColor());
        return type;
    }

    @Override
    public Type hide(Type type, boolean b) {
        type.setHide(b?1:0);
        return type;
    }

    @Override
    public void delete(Type type) {
        while(type.getLessons().size()>0){
            General.delete(type.getLessons().get(0));
        }
        type.getSchedule().getTypes().remove(type);
    }

    public String toXML(Type type) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", type.getId().toString()));
        xml.append(Xmls.stringToXml("name", type.getName()));
        xml.append(Xmls.stringToXml("hide", type.getHide().toString()));
        xml.append(Xmls.stringToXml("color", type.getColor().toString()));
        return xml.toString();
    }

    public Type fromXML(String text, Schedule schedule) {
        Type type = new Type();
        type.setSchedule(schedule);
        type.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, type.getId());
        type.setName(Xmls.extractString("name", text));
        type.setHide(Xmls.extractInteger("hide", text));
        type.setColor(Xmls.extractInteger("color", text));
        type.setLessons(new ArrayList<Lesson>());
        return type;
    }
}
