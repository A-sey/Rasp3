package sey.a.rasp3.service;

import sey.a.rasp3.model.Default;
import sey.a.rasp3.model.Schedule;

public interface CRUD<T extends Default, D> {
    T create(Schedule schedule, D d);
    T fastCreate(Schedule schedule, D d);
    D wet(T t);
    T update(T t, D d);
    T hide(T t, boolean b);
    void delete(T t);
    String toXML(T t);
    T fromXML(String text, Schedule schedule);
}
