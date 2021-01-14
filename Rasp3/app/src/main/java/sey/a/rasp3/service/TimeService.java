package sey.a.rasp3.service;

import java.util.ArrayList;

import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.raw.RawTime;
import sey.a.rasp3.shell.Clocks;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.shell.Xmls;

public class TimeService implements CRUD<Time, RawTime> {
    private static Long maxId = 0L;

    public Time create(Schedule schedule, RawTime raw) {
        Time time = new Time();
        time.setId(++maxId);
        time.setSchedule(schedule);
        schedule.getTimes().add(time);
        time.setName(raw.getName());
        time.setStartTime(raw.getStart());
        time.setEndTime(raw.getEnd());
        time.setHide(0);
        time.setLessons(new ArrayList<Lesson>());
        return time;
    }

    @Override
    public Time fastCreate(Schedule schedule, RawTime rawTime) {
        return null;
    }

    @Override
    public RawTime wet(Time time) {
        RawTime raw = new RawTime();
        raw.setName(time.getName());
        raw.setStart(time.getStartTime());
        raw.setEnd(time.getEndTime());
        return raw;
    }

    @Override
    public Time update(Time time, RawTime rawTime) {
        time.setName(rawTime.getName());
        time.setStartTime(rawTime.getStart());
        time.setEndTime(rawTime.getEnd());
        return time;
    }

    @Override
    public Time hide(Time time, boolean b) {
        return null;
    }

    @Override
    public void delete(Time time) {
        while(time.getLessons().size()>0){
            General.delete(time.getLessons().get(0));
        }
        time.getSchedule().getTimes().remove(time);
    }

    public String toXML(Time time) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", time.getId().toString()));
        xml.append(Xmls.stringToXml("name", time.getName()));
        xml.append(Xmls.stringToXml("startTime", time.getStartTime().toString()));
        xml.append(Xmls.stringToXml("endTime", time.getEndTime().toString()));
        xml.append(Xmls.stringToXml("hide", time.getHide().toString()));
        return xml.toString();
    }

    public Time fromXML(String text, Schedule schedule) {
        Time time = new Time();
        time.setSchedule(schedule);
        time.setId(Xmls.extractLong("id", text));
        maxId = Math.max(maxId, time.getId());
        time.setName(Xmls.extractString("name", text));
        time.setStartTime(new Clocks(Xmls.extractString("startTime", text)));
        time.setEndTime(new Clocks(Xmls.extractString("endTime", text)));
        time.setHide(Xmls.extractInteger("hide", text));
        time.setLessons(new ArrayList<Lesson>());
        return time;
    }
}
