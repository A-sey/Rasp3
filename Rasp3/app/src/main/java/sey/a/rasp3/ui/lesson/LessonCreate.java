package sey.a.rasp3.ui.lesson;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawLesson;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class LessonCreate implements DefaultCreate<Lesson> {
    private View root;
    private boolean update = false;
    private Lesson lesson = null;

    private List<Discipline> disciplines;
    private List<Teacher> teachers;
    private List<Time> times;
    private List<Type> types;
    private final String spinnerStub = " -- ";

    @Override
    public View createForm(Context context, Lesson lesson) {
        this.lesson = lesson;
        root = View.inflate(context, R.layout.fragment_lesson_create, null);
        RawLesson raw = General.wet(lesson);
        Schedule schedule = General.getSchedule();
        times = schedule.getTimes();
        types = schedule.getTypes();
        disciplines = schedule.getDisciplines();
        teachers = schedule.getTeachers();
        drawFields(context, schedule, raw);
        update = true;
        return root;
    }

    @Override
    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_lesson_create, null);
//        raw = new RawLesson();
        Schedule schedule = General.getSchedule();
        times = schedule.getTimes();
        types = schedule.getTypes();
        disciplines = schedule.getDisciplines();
        teachers = schedule.getTeachers();
        drawFields(context, schedule, null);
        update = false;
        return root;
    }

    @Override
    public boolean positiveClick() {
        final EditText startDate = root.findViewById(R.id.start_date);
        final EditText endDate = root.findViewById(R.id.end_date);
        final Spinner spinnerWeekType = root.findViewById(R.id.spinner_week);
        final Spinner spinnerDayOfWeek = root.findViewById(R.id.spinner_day_of_week);
        final Spinner spinnerTime = root.findViewById(R.id.spinner_time);
        final Spinner spinnerType = root.findViewById(R.id.spinner_type);
        final Spinner spinnerDiscipline = root.findViewById(R.id.spinner_discipline);
        final Spinner spinnerTeacher = root.findViewById(R.id.spinner_teacher);
        final EditText auditorium = root.findViewById(R.id.auditorium);
        RawLesson raw = new RawLesson();
        raw.setAuditorium(auditorium.getText().toString());
        if (spinnerTime.getSelectedItemId() > 0) {
            raw.setTime(times.get((int) spinnerTime.getSelectedItemId() - 1));
        }
        if (spinnerType.getSelectedItemId() > 0) {
            raw.setType(types.get((int) spinnerType.getSelectedItemId() - 1));
        }
        if (spinnerDiscipline.getSelectedItemId() > 0) {
            raw.setDiscipline(disciplines.get((int) spinnerDiscipline.getSelectedItemId() - 1));
        }
        if (spinnerTeacher.getSelectedItemId() > 0) {
            raw.getTeachers().add(teachers.get((int) spinnerTeacher.getSelectedItemId() - 1));
        }
        if (spinnerWeekType.getSelectedItemId() > 0) {
            raw.setWeekType((int) spinnerWeekType.getSelectedItemId() - 1);
        }
        if (spinnerDayOfWeek.getSelectedItemId() > 0) {
            raw.setDayOfWeek(((int) spinnerDayOfWeek.getSelectedItemId()) % 7 + 1);
        }
        raw.setDateFrom(Dates.parseDate(startDate.getText().toString(), "\\."));
        raw.setDateTo(Dates.parseDate(endDate.getText().toString(), "\\."));
        raw.generateDates(General.getSchedule().getStartDate());
        if (raw.validate()) {
            if (update) {
                General.update(lesson, raw);
            } else {
                General.create(raw);
            }
            return true;
        }
        return false;
    }

    private void drawFields(Context context, final Schedule schedule, RawLesson raw) {
        final CheckBox scheduleRange = root.findViewById(R.id.range);
        final EditText startDate = root.findViewById(R.id.start_date);
        final EditText endDate = root.findViewById(R.id.end_date);
        final Spinner spinnerWeekType = root.findViewById(R.id.spinner_week);
        final Spinner spinnerDayOfWeek = root.findViewById(R.id.spinner_day_of_week);
        final Spinner spinnerTime = root.findViewById(R.id.spinner_time);
        final Spinner spinnerType = root.findViewById(R.id.spinner_type);
        final Spinner spinnerDiscipline = root.findViewById(R.id.spinner_discipline);
        final Spinner spinnerTeacher = root.findViewById(R.id.spinner_teacher);
        final EditText auditorium = root.findViewById(R.id.auditorium);

        scheduleRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scheduleRange.isChecked()) {
                    startDate.setEnabled(false);
                    startDate.setText(Dates.dateToString(schedule.getStartDate()));
                    endDate.setEnabled(false);
                    endDate.setText(Dates.dateToString(schedule.getEndDate()));
                    scheduleRange.setText("Использовать");
                } else {
                    startDate.setEnabled(true);
                    endDate.setEnabled(true);
                    scheduleRange.setText("Не использовать");
                }
            }
        });

        startDate.setText(Dates.dateToString(schedule.getStartDate()));
        endDate.setText(Dates.dateToString(schedule.getEndDate()));
        List<String> weekType = new ArrayList<>(Arrays.asList("Каждая", "Нечётная", "Чётная", "Отдельные даты"));
        setSpinnerText(context, spinnerWeekType, weekType);
        List<String> dayOfWeek = new ArrayList<>(Arrays.asList("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"));
        setSpinnerText(context, spinnerDayOfWeek, dayOfWeek);
        List<String> timesText = new ArrayList<>();
        for (Time t : times) {
            if (t.getHide() != 0) {
                continue;
            }
            timesText.add(t.getName());
        }
        setSpinnerText(context, spinnerTime, timesText);
        spinnerTime.getSelectedItemId();
        List<String> typesText = new ArrayList<>();
        for (Type t : types) {
            if (t.getHide() != 0) {
                continue;
            }
            typesText.add(t.getName());
        }
        setSpinnerText(context, spinnerType, typesText);
        final List<String> disciplinesText = new ArrayList<>();
        for (Discipline d : disciplines) {
            if (d.getHide() != 0) {
                continue;
            }
            disciplinesText.add(d.getShortName());
        }
        setSpinnerText(context, spinnerDiscipline, disciplinesText);
        List<String> teachersText = new ArrayList<>();
        for (Teacher t : teachers) {
            if (t.getHide() != 0) {
                continue;
            }
            teachersText.add(t.getShortName());
        }
        setSpinnerText(context, spinnerTeacher, teachersText);

        if (raw != null) {
            if (schedule.getStartDate().getTime().equals(raw.getDateFrom().getTime())
                    && schedule.getEndDate().getTime().equals(raw.getDateTo().getTime())) {
                scheduleRange.setChecked(true);
                startDate.setEnabled(false);
                endDate.setEnabled(false);
            } else {
                scheduleRange.setChecked(false);
                startDate.setEnabled(true);
                endDate.setEnabled(true);
            }
            startDate.setText(Dates.dateToString(raw.getDateFrom()));
            endDate.setText(Dates.dateToString(raw.getDateTo()));
            spinnerWeekType.setSelection(raw.getWeekType() + 1);
            spinnerDayOfWeek.setSelection((raw.getDayOfWeek() + 5) % 7 + 1);
            spinnerTime.setSelection(times.indexOf(raw.getTime()) + 1);
            spinnerType.setSelection(types.indexOf(raw.getType()) + 1);
            spinnerDiscipline.setSelection(disciplines.indexOf(raw.getDiscipline()) + 1);
            if (raw.getTeachers().size() > 0) {
                spinnerTeacher.setSelection(teachers.indexOf(raw.getTeachers().get(0)) + 1);
            }
            auditorium.setText(raw.getAuditorium());
        }
    }

    private void setSpinnerText(Context context, Spinner spinner, List<String> list) {
        if (list.size() == 0 || !list.get(0).equals(spinnerStub)) {
            list.add(0, spinnerStub);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
