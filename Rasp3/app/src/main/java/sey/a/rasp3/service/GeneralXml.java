package sey.a.rasp3.service;

import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.shell.Xmls;

public class GeneralXml {
    public static String scheduleXmlPacking(Schedule schedule){
        ScheduleService scheduleService = new ScheduleService();
        return Xmls.stringToXml("schedule", scheduleService.toXML(schedule)).toString();
    }

    public static Schedule scheduleXmlUnpacking(String text){
        ScheduleService scheduleService = new ScheduleService();
        return scheduleService.fromXML(Xmls.extractString("schedule", text));
    }
}
