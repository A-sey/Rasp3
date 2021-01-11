package sey.a.rasp3.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;

public class ScheduleFragment extends Fragment {
    View root;
    Calendar date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_schedule, container, false);

        date = Calendar.getInstance();

        date.set(2020, 8, 29);

        drawAll();
        return root;
    }

    private void drawAll() {
        if (General.getSchedule() == null) {
            Intent intent = new Intent(getContext(), SelectScheduleFragment.class);
            startActivityForResult(intent, 0);
            return;
        }
        drawNavigate();
        drawLessons();
    }

    private void drawNavigate() {
        LinearLayout layout = root.findViewById(R.id.date_navigation);
        getLayoutInflater().inflate(R.layout.fragment_lesson_navigation, layout);
        Button left = layout.findViewById(R.id.left_button);
        Button right = layout.findViewById(R.id.right_button);
        final TextView weekNumber = layout.findViewById(R.id.week_number);
        final TextView textDate = layout.findViewById(R.id.date);
        final LinearLayout dateLayout = layout.findViewById(R.id.date_layout);
        writeDate(weekNumber, textDate);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.add(Calendar.DAY_OF_YEAR, -1);
                writeDate(weekNumber, textDate);
                drawLessons();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.add(Calendar.DAY_OF_YEAR, 1);
                writeDate(weekNumber, textDate);
                drawLessons();
            }
        });
        left.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                date.add(Calendar.WEEK_OF_YEAR, -1);
                writeDate(weekNumber, textDate);
                drawLessons();
                return true;
            }
        });
        right.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                date.add(Calendar.WEEK_OF_YEAR, 1);
                writeDate(weekNumber, textDate);
                drawLessons();
                return true;
            }
        });
        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = Calendar.getInstance();
                writeDate(weekNumber, textDate);
                drawLessons();
            }
        });
    }

    private void writeDate(TextView weekNumber, TextView textDate) {
        textDate.setText(Dates.dateToString(date));
        weekNumber.setText((date.get(Calendar.WEEK_OF_YEAR) - General.getSchedule().getStartDate().get(Calendar.WEEK_OF_YEAR) + 1) + " неделя");
    }

    private void drawLessons() {
        LinearLayout LL = root.findViewById(R.id.layout);
        TextView dayOfWeek = LL.findViewById(R.id.day_of_week);
        LL.removeAllViews();
        LL.addView(dayOfWeek);
        List<String> days = new ArrayList<>(Arrays.asList("Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"));
        dayOfWeek.setText(days.get(date.get(Calendar.DAY_OF_WEEK) - 1));
        List<Lesson> lessons = General.getSchedule().getLessons();
        List<LessonDate> lessonDates = new ArrayList<>();
        for (Lesson l : lessons) {
            for (LessonDate ld : l.getLessonDates()) {
                if (date.get(Calendar.YEAR) == ld.getDate().get(Calendar.YEAR) &&
                        date.get(Calendar.DAY_OF_YEAR) == ld.getDate().get(Calendar.DAY_OF_YEAR)) {
                    lessonDates.add(ld);
                }
            }
        }
        Collections.sort(lessonDates, LessonDate.startTimeComparator);
        for(LessonDate ld: lessonDates){
            LL.addView(drawLesson(ld));
        }

    }

    private View drawLesson(LessonDate lessonDate) {
        Lesson l = lessonDate.getLesson();
        LinearLayout common = (LinearLayout) getLayoutInflater().inflate(R.layout.fragment_lesson, null, false);
        TextView lessonTime = common.findViewById(R.id.lesson_time);
        TextView condition = common.findViewById(R.id.condition);
        TextView startTime = common.findViewById(R.id.start_time);
        TextView endTime = common.findViewById(R.id.end_time);
        TextView lessonType = common.findViewById(R.id.lesson_type);
        TextView lessonDiscipline = common.findViewById(R.id.lesson_discipline);
        TextView lessonTeachers = common.findViewById(R.id.lesson_teachers);
        TextView auditorium = common.findViewById(R.id.auditorium);

        lessonTime.setText(l.getTime().getName());
        startTime.setText(l.getTime().getStartTime().toString());
        endTime.setText(l.getTime().getEndTime().toString());
        lessonType.setText(l.getType().getName());
        lessonDiscipline.setText(l.getDiscipline().getShortName());
        StringBuilder teachers = new StringBuilder();
        for (Teacher t : l.getTeachers()) {
            teachers.append(t.getShortName()).append("\n");
        }
        lessonTeachers.setText(teachers.toString().trim());
        auditorium.setText(l.getAuditorium());
        return common;
    }

    @Override
    public void onActivityResult(int RequestCode, int resultCode, Intent data) {
        if (RequestCode == 0) {
            drawAll();
        }
    }


    private void test() {
//        Files service = new Files(getContext());
//        Schedule schedule = Test.generateSchedule();
//        String result = GeneralXml.scheduleXmlPacking(schedule);
//        service.writeFile("Schedule", result);
//
//        Schedule reverse = GeneralXml.scheduleXmlUnpacking(result);
//        General.setSchedule(reverse);
//        System.out.println(service.getFilesList());
    }
}