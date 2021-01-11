package sey.a.rasp3.ui.lesson;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.raw.RawLesson;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;

public class LessonCreate extends Activity {
    private RawLesson raw;
    private List<Discipline> disciplines;
    private List<Teacher> teachers;
    private List<Time> times;
    private List<Type> types;
    private String spinnerStub = " -- ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lesson_create);

        raw = new RawLesson();
        Schedule schedule = General.getSchedule();
        times = schedule.getTimes();
        types = schedule.getTypes();
        disciplines = schedule.getDisciplines();
        teachers = schedule.getTeachers();

        drawFields(schedule);
    }

    private void drawFields(final Schedule schedule) {
        final CheckBox scheduleRange = findViewById(R.id.range);
        final EditText startDate = findViewById(R.id.start_date);
        final EditText endDate = findViewById(R.id.end_date);
        final Spinner spinnerWeekType = findViewById(R.id.spinner_week);
        final Spinner spinnerDayOfWeek = findViewById(R.id.spinner_day_of_week);
        final Spinner spinnerTime = findViewById(R.id.spinner_time);
        final Spinner spinnerType = findViewById(R.id.spinner_type);
        final Spinner spinnerDiscipline = findViewById(R.id.spinner_discipline);
        final Spinner spinnerTeacher = findViewById(R.id.spinner_teacher);
        final EditText auditorium = findViewById(R.id.auditorium);

        scheduleRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (scheduleRange.isChecked()) {
                    startDate.setEnabled(false);
                    endDate.setEnabled(false);
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
        setSpinnerText(spinnerWeekType, weekType);
        List<String> dayOfWeek = new ArrayList<>(Arrays.asList("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"));
        setSpinnerText(spinnerDayOfWeek, dayOfWeek);
        List<String> timesText = new ArrayList<>();
        for (Time t : times) {
            timesText.add(t.getName());
        }
        setSpinnerText(spinnerTime, timesText);
        spinnerTime.getSelectedItemId();
        List<String> typesText = new ArrayList<>();
        for (Type t : types) {
            typesText.add(t.getName());
        }
        setSpinnerText(spinnerType, typesText);
        final List<String> disciplinesText = new ArrayList<>();
        for (Discipline d : disciplines) {
            disciplinesText.add(d.getShortName());
        }
        setSpinnerText(spinnerDiscipline, disciplinesText);
        List<String> teachersText = new ArrayList<>();
        for (Teacher t : teachers) {
            teachersText.add(t.getShortName());
        }
        setSpinnerText(spinnerTeacher, teachersText);


        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Toast.makeText(LessonCreate.this, ""+spinnerTime.getSelectedItemId(), Toast.LENGTH_SHORT).show();
            }
        });

        Button ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText auditorium = findViewById(R.id.auditorium);
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
                if (!raw.validate()) {
                    Toast.makeText(LessonCreate.this, "Неверный ввод", Toast.LENGTH_SHORT).show();
                    return;
                }
                General.create(raw);
                finish();
            }
        });
    }

    private void setSpinnerText(Spinner spinner, List<String> list) {
        if (list.size() == 0 || !list.get(0).equals(spinnerStub)) {
            list.add(0, spinnerStub);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
