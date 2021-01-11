package sey.a.rasp3;

import java.util.ArrayList;
import java.util.Calendar;

import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.service.DisciplineService;
import sey.a.rasp3.service.LessonService;
import sey.a.rasp3.service.NoteService;
import sey.a.rasp3.service.ScheduleService;
import sey.a.rasp3.service.TeacherService;
import sey.a.rasp3.service.TimeService;
import sey.a.rasp3.service.TypeService;
import sey.a.rasp3.shell.Clocks;

public class Test {
    public Schedule create(){
        NoteService noteService = new NoteService();
        LessonService lessonService = new LessonService();
        DisciplineService disciplineService = new DisciplineService();
        TeacherService teacherService = new TeacherService();
        TypeService typeService = new TypeService();
        TimeService timeService = new TimeService();
        ScheduleService scheduleService = new ScheduleService();

        return null;
    }

    public static Note generateNote(){
        Note note = new Note();
        note.setId(1L);
        note.setActivity(0);
        note.setDateTime(Calendar.getInstance());
        note.setHide(0);
        note.setText("text");
        note.setValue("value");
        return note;
    }

    public static Schedule generateSchedule(){
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        schedule.setHide(0);
        schedule.setName("name");
        schedule.setStartDate(Calendar.getInstance());
        schedule.setEndDate(Calendar.getInstance());
        schedule.setDisciplines(new ArrayList<Discipline>());
        schedule.setLessons(new ArrayList<Lesson>());
        schedule.setTeachers(new ArrayList<Teacher>());
        schedule.setTimes(new ArrayList<Time>());
        schedule.setTypes(new ArrayList<Type>());

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("FullName");
        teacher.setShortName("ShortName");
        teacher.setComment("Comment");
        teacher.setHide(0);
        teacher.setSchedule(schedule);
        schedule.getTeachers().add(teacher);
        teacher.setLessons(new ArrayList<Lesson>());

        Teacher t1 = new Teacher();
        t1.setId(2L);
        t1.setName("Беленькая Марина Наумовна");
        t1.setShortName("Беленькая М.Н.");
        t1.setComment("");
        t1.setHide(0);
        t1.setSchedule(schedule);
        schedule.getTeachers().add(t1);
        t1.setLessons(new ArrayList<Lesson>());

        Discipline discipline = new Discipline();
        discipline.setId(1L);
        discipline.setName("FullName");
        discipline.setShortName("ShortName");
        discipline.setComment("Comment");
        discipline.setHide(0);
        discipline.setSchedule(schedule);
        schedule.getDisciplines().add(discipline);
        discipline.setLessons(new ArrayList<Lesson>());

        Type type = new Type();
        type.setId(1L);
        type.setName("name");
        type.setHide(0);
        type.setSchedule(schedule);
        schedule.getTypes().add(type);
        type.setLessons(new ArrayList<Lesson>());

        Time time = new Time();
        time.setId(1L);
        time.setName("Name");
        time.setStartTime(new Clocks("10:10"));
        time.setEndTime(new Clocks("11:11"));
        time.setHide(0);
        time.setSchedule(schedule);
        schedule.getTimes().add(time);
        time.setLessons(new ArrayList<Lesson>());

        Lesson lesson = new Lesson();
        lesson.setId(1L);
        lesson.setHide(0);
        lesson.setDiscipline(discipline);
        discipline.getLessons().add(lesson);
        ArrayList<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        lesson.setTeachers(teachers);
        teacher.getLessons().add(lesson);
        lesson.setType(type);
        type.getLessons().add(lesson);
        lesson.setTime(time);
        time.getLessons().add(lesson);
        lesson.setSchedule(schedule);
        schedule.getLessons().add(lesson);
        lesson.setLessonDates(new ArrayList<LessonDate>());

        LessonDate lessonDate = new LessonDate();
        lessonDate.setId(1L);
        lessonDate.setDate(Calendar.getInstance());
        lessonDate.setHide(0);
        lessonDate.setLesson(lesson);
        lesson.getLessonDates().add(lessonDate);
        lessonDate.setNotes(new ArrayList<Note>());

        Note note = new Note();
        note.setId(1L);
        note.setActivity(0);
        note.setDateTime(Calendar.getInstance());
        note.setHide(0);
        note.setText("text");
        note.setValue("value");
        note.setLessonDate(lessonDate);
        lessonDate.getNotes().add(note);

        return schedule;
    }
}
