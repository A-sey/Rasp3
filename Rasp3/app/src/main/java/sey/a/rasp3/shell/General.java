package sey.a.rasp3.shell;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.raw.RawDiscipline;
import sey.a.rasp3.raw.RawLesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawTeacher;
import sey.a.rasp3.raw.RawTime;
import sey.a.rasp3.raw.RawType;
import sey.a.rasp3.service.CRUD;
import sey.a.rasp3.service.DisciplineService;
import sey.a.rasp3.service.GeneralXml;
import sey.a.rasp3.service.LessonService;
import sey.a.rasp3.service.ScheduleService;
import sey.a.rasp3.service.TeacherService;
import sey.a.rasp3.service.TimeService;
import sey.a.rasp3.service.TypeService;

public class General {
    private static Files files;
    private static Schedule schedule;
    private static ScheduleService scheduleService = new ScheduleService();
    private static DisciplineService disciplineService = new DisciplineService();
    private static TeacherService teacherService = new TeacherService();
    private static TypeService typeService = new TypeService();
    private static TimeService timeService = new TimeService();
    private static LessonService lessonService = new LessonService();

    public static void createFiles(Context context){
        files = new Files(context);
    }

    public static Schedule createSchedule(String name, Calendar startDate, Calendar endDate) {
        Schedule schedule = scheduleService.create(name, startDate, endDate);
        General.schedule = schedule;
        files.writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
        return schedule;
    }

    public static <T, D> T create(D d){
        CRUD<T, D> crud;
        if(d instanceof RawTeacher){
            crud = (CRUD<T, D>) teacherService;
        }else if(d instanceof RawDiscipline){
            crud = (CRUD<T, D>) disciplineService;
        }else if(d instanceof RawTime){
            crud = (CRUD<T, D>) timeService;
        }else if(d instanceof RawType){
            crud = (CRUD<T, D>) typeService;
        }else if(d instanceof RawLesson){
            crud = (CRUD<T, D>) lessonService;
        } else {
            return null;
        }
        T t = crud.create(schedule, d);
        files.writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
        return t;
    }

    public static Lesson createLesson(RawLesson rawLesson) {
        Lesson lesson = lessonService.create(schedule, rawLesson);
        files.writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
        return lesson;
    }

    public static void setSchedule(Schedule schedule) {
        General.schedule = schedule;
    }

    public static Schedule getSchedule() {
        return schedule;
    }

}
