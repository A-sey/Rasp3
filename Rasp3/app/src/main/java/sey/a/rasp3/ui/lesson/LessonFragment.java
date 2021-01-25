package sey.a.rasp3.ui.lesson;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.menu.PopUpMenu;
import sey.a.rasp3.ui.schedule.ScheduleFragment;

public class LessonFragment extends Fragment {
    View root;
    Calendar date = Calendar.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_schedule, container, false);

//        date.set(2020, 8, 29);

        drawAll();
        return root;
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
//        weekNumber.setText(Dates.weeksDiff(General.getSchedule().getStartDate(), date) + "неделя");
        weekNumber.setText(String.format("%d неделя", Dates.weeksDiff(General.getSchedule().getStartDate(), date)));
//        weekNumber.setText((date.get(Calendar.WEEK_OF_YEAR) - General.getSchedule().getStartDate().get(Calendar.WEEK_OF_YEAR) + 1) + " неделя");
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
                    Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
                }
            });
            LL.addView(lessonView);
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