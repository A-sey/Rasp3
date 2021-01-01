package sey.a.rasp3.shell;

import java.util.Calendar;
import java.util.List;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.service.DisciplineService;
import sey.a.rasp3.service.LessonService;
import sey.a.rasp3.service.ScheduleService;
import sey.a.rasp3.service.TeacherService;
import sey.a.rasp3.service.TimeService;
import sey.a.rasp3.service.TypeService;

public class General {
    private static Schedule schedule;
    private static ScheduleService scheduleService = new ScheduleService();
    private static DisciplineService disciplineService = new DisciplineService();
    private static TeacherService teacherService = new TeacherService();
    private static TypeService typeService = new TypeService();
    private static TimeService timeService = new TimeService();
    private static LessonService lessonService = new LessonService();

    public static Schedule createSchedule(String name, Calendar startDate, Calendar endDate) {
        Schedule schedule = scheduleService.create(name, startDate, endDate);
        return schedule;
    }

    public static Discipline createDiscipline(String fullName, String shortName, String comment) {
        Discipline discipline = disciplineService.create(schedule, fullName, shortName, comment);
        return discipline;
    }

    public static Teacher createTeacher(String fullName, String shortName, String comment) {
        Teacher teacher = teacherService.create(schedule, fullName, shortName, comment);
        return teacher;
    }

    public static Time createTime(String fN, Clocks sClock, Clocks eClock) {
        Time time = timeService.create(schedule, fN, sClock, eClock);
        return time;
    }

    public static Type createType(String fN) {
        Type type = typeService.create(schedule, fN);
        return type;
    }

    public static Lesson createLesson(Schedule schedule, List<Calendar> dates, Discipline discipline, List<Teacher> teachers, Type type, Time time, String auditorium){
        Lesson lesson = lessonService.create(schedule, dates, discipline, teachers, type, time, auditorium);
        return lesson;
    }

    public static void setSchedule(Schedule schedule) {
        General.schedule = schedule;
    }

    public static Schedule getSchedule() {
        return schedule;
    }

}
