package sey.a.rasp3.ui.lesson;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.raw.RawLesson;
import sey.a.rasp3.raw.RawLessonDate;
import sey.a.rasp3.service.LessonDateService;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.menu.NoteMenu;
import sey.a.rasp3.ui.menu.PopUpMenu;
import sey.a.rasp3.ui.schedule.ScheduleFragment;

public class LessonFragment extends Fragment {
    View root;
    Calendar date = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_schedule, container, false);
        setRetainInstance(true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        drawAll();
    }

    private void drawAll() {
        if (General.getSchedule() == null) {
            ScheduleFragment fragment = new ScheduleFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction ftr = fragmentManager.beginTransaction();
            ftr.replace(R.id.nav_host_fragment, fragment);
            ftr.addToBackStack(null);
            ftr.commit();
            return;
        }
        drawNavigate();
        drawLessons();
    }

    private void drawNavigate() {
        final LinearLayout layout = root.findViewById(R.id.date_navigation);
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
        dateLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                DatePickerDialog dpd = new DatePickerDialog(getContext(), dateListener,
                        date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                return true;
            }
        });
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            date.set(year, month, day);
            drawAll();
        }
    };

    private void writeDate(TextView weekNumber, TextView textDate) {
        textDate.setText(Dates.dateToString(date));
        weekNumber.setText(String.format("%d неделя", Dates.weeksDiff(General.getSchedule().getStartDate(), date)));
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
        for(final LessonDate ld: lessonDates){
            View lessonView = drawLesson(ld);
            lessonView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopUpMenu<Lesson, RawLesson> menu = new PopUpMenu<>();
                    AlertDialog dialog = menu.createDialog(getContext(), ld.getLesson());
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            drawLessons();
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
            lessonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteMenu menu = new NoteMenu();
                    menu.setFragmentManager(getParentFragmentManager());
                    AlertDialog dialog = menu.createDialog(getContext(), ld);
                    dialog.show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            drawLessons();
                        }
                    });
                }
            });
            LL.addView(lessonView);
        }
    }

    private View drawLesson(LessonDate lessonDate) {
        LinearLayout common = (LinearLayout) getLayoutInflater().inflate(R.layout.fragment_lesson, null, false);
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

        int borderColor;
        int backgroundColor;
        if(raw.getCondition().equals("Идёт")){
            borderColor = res.getColor(R.color.colorAccent);
        }else if(raw.getCondition().equals("Не будет")){
            borderColor = res.getColor(R.color.colorDisabled);
        } else{
            borderColor = res.getColor(R.color.colorPrimary);
        }
        backgroundColor = lessonDate.getLesson().getType().getColor();

        Drawable d0 = createDrawable(0, borderColor,
                borderColor);
        Drawable d1 = createDrawable(3, borderColor,
                backgroundColor);

        lessonTime.setText(raw.getLessonTime());
        lessonTime.setBackgroundDrawable(d0);
        lessonTime.setTextColor(Color.rgb(240,240,240));
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

    @Override
    public void onActivityResult(int RequestCode, int resultCode, Intent data) {
        if (RequestCode == 0) {
            drawAll();
        }
    }


}