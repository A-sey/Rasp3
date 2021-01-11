package sey.a.rasp3.service;

import java.util.ArrayList;

import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawType;
import sey.a.rasp3.shell.Xmls;

public class TypeService implements CRUD<Type, RawType> {
    private static Long maxId = 0L;

    public Type create(Schedule schedule, RawType raw) {
        Type type = new Type();
        type.setId(++maxId);
        type.setSchedule(schedule);
        schedule.getTypes().add(type);
        type.setName(raw.getName());
        type.setHide(0);
        type.setLessons(new ArrayList<Lesson>());
        return type;
    }

    @Override
    public Type fastCreate(Schedule schedule, RawType rawType) {
        return null;
    }

    @Override
    public Type update(Type type, RawType rawType) {
        return null;
    }

    @Override
    public Type hide(Type type, boolean b) {
        return null;
    }

    @Override
    public void delete(Type type) {

    }

    public String toXML(Type type) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", type.getId().toString()));
        xml.append(Xmls.stringToXml("name", type.getName()));
        xml.append(Xmls.stringToXml("hide", type.getHide().toString()));
        return xml.toString();
    }

    public Type fromXML(String text, Schedule schedule) {
        Type type = new Type();
        type.setSchedule(schedule);
        type.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, type.getId());
        type.setName(Xmls.extractString("name", text));
        type.setHide(Xmls.extractInteger("hide", text));
        type.setLessons(new ArrayList<Lesson>());
        return type;
    }
}
