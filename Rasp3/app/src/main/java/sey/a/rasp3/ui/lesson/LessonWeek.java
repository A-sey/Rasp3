package sey.a.rasp3.ui.lesson;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.raw.RawLessonDate;
import sey.a.rasp3.service.LessonDateService;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;

public class LessonWeek extends Fragment {
    private Schedule schedule;
    private List<LessonDate> lessonDates;
    private Integer weekNumber;
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lesson_week, null);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (General.getSchedule() != null) {
            if(weekNumber ==null) {
                weekNumber = Dates.weeksDiff(General.getSchedule().getStartDate(), Calendar.getInstance());
            }
            drawTwoWeeksSchedule();
        }
    }

    public LessonWeek() {
        schedule = General.getSchedule();
        lessonDates = new ArrayList<>();
        if (schedule != null) {
            for (Lesson l : schedule.getLessons()) {
                lessonDates.addAll(l.getLessonDates());
            }
            Collections.sort(lessonDates, LessonDate.dateComparator);
        }
    }

    public void drawTwoWeeksSchedule() {
        List<LessonDate> lessonDates1 = getPairsForWeek(weekNumber);
        List<LessonDate> lessonDates2 = getPairsForWeek(weekNumber + 1);
        LinearLayout field = root.findViewById(R.id.lessons_field);
        field.removeAllViews();
        TextView weekName1 = root.findViewById(R.id.week_name1);
        weekName1.setText("◄  " + weekNumber + " неделя");
        weekName1.setTextSize(6 * requireContext().getResources().getDisplayMetrics().density);
        weekName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekNumber--;
                drawTwoWeeksSchedule();
            }
        });
        TextView weekName2 = root.findViewById(R.id.week_name2);
        weekName2.setText(weekNumber + 1 + " неделя  ►");
        weekName2.setTextSize(6 * requireContext().getResources().getDisplayMetrics().density);
        weekName2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weekNumber++;
                drawTwoWeeksSchedule();
            }
        });

        List<String> days = new ArrayList<>(Arrays.asList("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"));
        for (int i = 0; i < 7; i++) {
            int numb = (i + 1) % 7 + 1;
            List<LessonDate> dayLessons1 = new ArrayList<>();
            List<LessonDate> dayLessons2 = new ArrayList<>();
            for (LessonDate ld : lessonDates1) {
                if (ld.getDate().get(Calendar.DAY_OF_WEEK) == numb) {
                    dayLessons1.add(ld);
                }
            }
            for (LessonDate ld : lessonDates2) {
                if (ld.getDate().get(Calendar.DAY_OF_WEEK) == numb) {
                    dayLessons2.add(ld);
                }
            }

            LinearLayout FL = new LinearLayout(getContext());
            FL.setOrientation(LinearLayout.VERTICAL);

            TextView day = new TextView(getContext());
            day.setText(days.get(i));
            day.setGravity(Gravity.CENTER);
            day.setTextSize(5 * requireContext().getResources().getDisplayMetrics().density);
            FL.addView(day);

            TableLayout dayLayout = new TableLayout(getContext());
            SortedSet<Time> dateTimes = new TreeSet<>(Time.startTimeComparator);
            for (LessonDate ld : dayLessons1) {
                dateTimes.add(ld.getLesson().getTime());
            }
            for (LessonDate ld : dayLessons2) {
                dateTimes.add(ld.getLesson().getTime());
            }
            for (Time time : dateTimes) {
                TableRow row = new TableRow(getContext());
                row.setGravity(Gravity.CENTER);
                dayLayout.addView(row);
                row.addView(fillDayTime(dayLessons1, time));
                row.addView(fillDayTime(dayLessons2, time));
            }
            FL.addView(dayLayout);
            field.addView(FL);
        }
    }

    private View fillDayTime(List<LessonDate> lessonDates, Time time) {
        List<LessonDate> lds = new ArrayList<>();
        for (LessonDate ld : lessonDates) {
            if (ld.getLesson().getTime().equals(time)) {
                lds.add(ld);
            }
        }
        if (lds.size() == 0) {
//            View empty = View.inflate(getContext(), R.layout.fragment_lesson1, null);
//            empty.setVisibility(View.INVISIBLE);
//            return empty;
            return new View(getContext());
        } else if (lds.size() == 1) {
            View view = drawLesson(getContext(), lds.get(0));
            return view;
        } else {
            LinearLayout LL = new LinearLayout(getContext());
            LL.setOrientation(LinearLayout.VERTICAL);
            for (LessonDate ld : lds) {
                LL.addView(drawLesson(getContext(), ld));
            }
            return LL;
        }
    }

    public View drawLesson(Context context, LessonDate lessonDate) {
        LinearLayout common = (LinearLayout) View.inflate(context, R.layout.fragment_lesson1, null);
        TextView condition = common.findViewById(R.id.condition);
        TextView lessonTime = common.findViewById(R.id.lesson_time);
        TextView startTime = common.findViewById(R.id.start_time);
        TextView endTime = common.findViewById(R.id.end_time);
        TextView lessonType = common.findViewById(R.id.lesson_type);
        TextView lessonDiscipline = common.findViewById(R.id.lesson_discipline);
        TextView lessonTeachers = common.findViewById(R.id.lesson_teachers);
        TextView auditorium = common.findViewById(R.id.auditorium);

        LessonDateService service = new LessonDateService();
        RawLessonDate raw = service.wet(lessonDate);
        Resources res = requireContext().getResources();

        Drawable d0 = createDrawable(0, res.getColor(R.color.colorPrimary),
                res.getColor(R.color.colorPrimary));
        Drawable d1 = createDrawable(2, res.getColor(R.color.colorPrimary),
                lessonDate.getLesson().getType().getColor());

        lessonTime.setText(raw.getLessonTime());
        lessonTime.setBackgroundDrawable(d0);
        lessonTime.setTextColor(Color.rgb(240, 240, 240));
        condition.setText(raw.getCondition());
        condition.setBackgroundDrawable(d1);
        startTime.setText(raw.getStartTime());
        startTime.setBackgroundDrawable(d1);
        endTime.setText(raw.getEndTime());
        endTime.setBackgroundDrawable(d1);
        lessonType.setText(raw.getLessonType());
        lessonType.setBackgroundDrawable(d1);
        lessonDiscipline.setText(raw.getLessonDiscipline());
        lessonDiscipline.setBackgroundDrawable(d1);
        lessonTeachers.setText(raw.getTeachers());
        lessonTeachers.setBackgroundDrawable(d1);
        auditorium.setText(raw.getAuditorium());
        auditorium.setBackgroundDrawable(d1);

        return common;
    }


    private Drawable createDrawable(int strokeWidth, int strokeColor, int backColor) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(backColor);
        int borderWidth = (int) (strokeWidth * requireContext().getResources().getDisplayMetrics().density);
        gd.setStroke(borderWidth, strokeColor);
        return gd;
    }

    public List<LessonDate> getPairsForWeek(int weekNumber) {
        List<LessonDate> list = new ArrayList<>();
        for (LessonDate ld : lessonDates) {
            if (Dates.weeksDiff(schedule.getStartDate(), ld.getDate()) == weekNumber) {
                list.add(ld);
            }
        }
        return list;
    }
}
