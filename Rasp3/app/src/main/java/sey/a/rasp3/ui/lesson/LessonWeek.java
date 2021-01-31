package sey.a.rasp3.ui.lesson;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.raw.RawLessonDate;
import sey.a.rasp3.service.LessonDateService;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;

public class LessonWeek extends Fragment {
    private Schedule schedule;
    private List<LessonDate> lessonDates;
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_lesson_week, null);
        int week = Dates.weeksDiff(General.getSchedule().getStartDate(), Calendar.getInstance());
        drawTwoWeeksSchedule(week);
        return root;
    }

    public LessonWeek() {
        schedule = General.getSchedule();
        lessonDates = new ArrayList<>();
        for (Lesson l : schedule.getLessons()) {
            lessonDates.addAll(l.getLessonDates());
        }
        Collections.sort(lessonDates, LessonDate.dateComparator);
    }

    public void drawTwoWeeksSchedule(final int weekNumber) {
        List<LessonDate> lessonDates1 = getPairsForWeek(weekNumber);
        List<LessonDate> lessonDates2 = getPairsForWeek(weekNumber + 1);
        LinearLayout field = root.findViewById(R.id.lessons_field);
        field.removeAllViews();
        TextView weekName1 = root.findViewById(R.id.week_name1);
        weekName1.setText("◄  " + weekNumber + " неделя");
        weekName1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawTwoWeeksSchedule(weekNumber - 1);
            }
        });
        TextView weekName2 = root.findViewById(R.id.week_name2);
        weekName2.setText(weekNumber + 1 + " неделя  ►");
        weekName2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawTwoWeeksSchedule(weekNumber + 1);
            }
        });
        List<String> days = new ArrayList<>(Arrays.asList("Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"));
        for (int i = 0; i < 7; i++) {
            int numb = (i + 1) % 7 + 1;
            LinearLayout FL = new LinearLayout(getContext());
            FL.setOrientation(LinearLayout.VERTICAL);

            TextView day = new TextView(getContext());
            day.setText(days.get(i));
            day.setGravity(Gravity.CENTER);
            day.setTextSize(3 * requireContext().getResources().getDisplayMetrics().density);
            FL.addView(day);

            LinearLayout dayLayout = new LinearLayout(getContext());
            dayLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout VL1 = new LinearLayout(getContext());
            VL1.setOrientation(LinearLayout.VERTICAL);
            LinearLayout VL2 = new LinearLayout(getContext());
            VL2.setOrientation(LinearLayout.VERTICAL);
            dayLayout.setWeightSum(2);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            );
            dayLayout.addView(VL1, params);
            dayLayout.addView(VL2, params);

            List<LessonDate> oneDay1 = new ArrayList<>();
            List<LessonDate> oneDay2 = new ArrayList<>();
            for (LessonDate ld : lessonDates1) {
                if (ld.getDate().get(Calendar.DAY_OF_WEEK) == numb) {
                    oneDay1.add(ld);
                }
            }
            for (LessonDate ld : lessonDates2) {
                if (ld.getDate().get(Calendar.DAY_OF_WEEK) == numb) {
                    oneDay2.add(ld);
                }
            }
            Collections.sort(oneDay1, LessonDate.startTimeComparator);
            Collections.sort(oneDay2, LessonDate.startTimeComparator);
            for (LessonDate ld : oneDay1) {
                VL1.addView(drawLesson(getContext(), ld));
            }
            if (oneDay1.size() == 0 && oneDay2.size() != 0) {
                View none = View.inflate(getContext(), R.layout.fragment_lesson1, null);
                none.setVisibility(View.INVISIBLE);
                VL1.addView(none);
            }
            for (LessonDate ld : oneDay2) {
                VL2.addView(drawLesson(getContext(), ld));
            }
            if (oneDay1.size() != 0 && oneDay2.size() == 0) {
                View none = View.inflate(getContext(), R.layout.fragment_lesson1, null);
                none.setVisibility(View.INVISIBLE);
                VL2.addView(none);
            }
            FL.addView(dayLayout);
            field.addView(FL);
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
                res.getColor(R.color.design_default_color_background));

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


    private Drawable createDrawable(int strokeWidth, int strokeColor, int backColor){
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
