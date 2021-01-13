package sey.a.rasp3.shell;

import android.content.Context;

import sey.a.rasp3.model.Default;
import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawDiscipline;
import sey.a.rasp3.raw.RawLesson;
import sey.a.rasp3.raw.RawSchedule;
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

    public static void createFiles(Context context) {
        files = new Files(context);
    }

    public static Schedule createSchedule(RawSchedule raw) {
        Schedule schedule = scheduleService.create(null, raw);
        General.schedule = schedule;
        files.writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
        return schedule;
    }

    private static <T extends Default, D> CRUD<T, D> findService(D d) {
        CRUD<T, D> crud;
        if (d instanceof RawTeacher) {
            crud = (CRUD<T, D>) teacherService;
        } else if (d instanceof RawDiscipline) {
            crud = (CRUD<T, D>) disciplineService;
        } else if (d instanceof RawTime) {
            crud = (CRUD<T, D>) timeService;
        } else if (d instanceof RawType) {
            crud = (CRUD<T, D>) typeService;
        } else if (d instanceof RawLesson) {
            crud = (CRUD<T, D>) lessonService;
        } else {
            crud = null;
        }
        return crud;
    }

    private static <T extends Default, D> CRUD<T, D> findService(T t) {
        CRUD<T, D> crud;
        if (t instanceof Teacher) {
            crud = (CRUD<T, D>) teacherService;
        } else if (t instanceof Discipline) {
            crud = (CRUD<T, D>) disciplineService;
        } else if (t instanceof Time) {
            crud = (CRUD<T, D>) timeService;
        } else if (t instanceof Type) {
            crud = (CRUD<T, D>) typeService;
        } else if (t instanceof Lesson) {
            crud = (CRUD<T, D>) lessonService;
        } else {
            crud = null;
        }
        return crud;
    }

    public static <T extends Default, D> T create(D d) {
        CRUD<T, D> crud = findService(d);
        if (crud == null) {
            return null;
        }
        T t = crud.create(schedule, d);
        files.writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
        return t;
    }

    public static <T extends Default, D> T update(T t, D d) {
        CRUD<T, D> crud = findService(d);
        if (crud == null) {
            return null;
        }
        T t1 = crud.update(t, d);

        return t1;
    }

    public static <T extends Default, D> void delete(T t) {
        CRUD<T, D> crud = findService(t);
        crud.delete(t);
//        files.writeFile(schedule.getName(), GeneralXml.scheduleXmlPacking(schedule));
    }

    public static void setSchedule(Schedule schedule) {
        General.schedule = schedule;
    }

    public static Schedule getSchedule() {
        return schedule;
    }

}
