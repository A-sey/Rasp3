package sey.a.rasp3.ui.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.shell.General;

public class ScheduleFragment extends Fragment {
    View root;
    Calendar date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_schedule, container, false);

        date = Calendar.getInstance();
        showList();
        return root;
    }

    private void showList() {
        if (General.getSchedule() == null) {
            Intent intent = new Intent(getContext(), SelectScheduleFragment.class);
            startActivityForResult(intent, 0);
            return;
        }
        LinearLayout LL = root.findViewById(R.id.layout);
        LL.removeAllViews();

//        date.set(2020, 8, 29);

        List<Lesson> lessons = General.getSchedule().getLessons();

        for (Lesson l : lessons) {
            for (LessonDate ld : l.getLessonDates()) {
                if (date.get(Calendar.YEAR) == ld.getDate().get(Calendar.YEAR) &&
                        date.get(Calendar.DAY_OF_YEAR) == ld.getDate().get(Calendar.DAY_OF_YEAR)) {
                    LL.addView(drawLesson(ld)/*, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)*/);
                }
            }
        }

    }

    private View drawLesson(LessonDate lessonDate){
        Lesson l = lessonDate.getLesson();
        LinearLayout common = (LinearLayout) getLayoutInflater().inflate(R.layout.fragment_lesson, null, false);
        TextView condition = common.findViewById(R.id.condition);
        TextView startTime = common.findViewById(R.id.start_time);
        TextView endTime = common.findViewById(R.id.end_time);
        TextView lessonType = common.findViewById(R.id.lesson_type);
        TextView lessonDiscipline = common.findViewById(R.id.lesson_discipline);
        TextView lessonTeachers = common.findViewById(R.id.lesson_teachers);
        TextView auditorium = common.findViewById(R.id.auditorium);

        startTime.setText(l.getTime().getStartTime().toString());
        endTime.setText(l.getTime().getEndTime().toString());
        lessonType.setText(l.getType().getName());
        lessonDiscipline.setText(l.getDiscipline().getShortName());
        StringBuilder teachers = new StringBuilder();
        for (Teacher t: l.getTeachers()){
            teachers.append(t.getShortName()).append("\n");
        }
        lessonTeachers.setText(teachers.toString().trim());
        auditorium.setText(l.getAuditorium());
        return common;
    }
    private View drawLesson1(LessonDate lessonDate) {
        LinearLayout common = new LinearLayout(getContext());
        common.setOrientation(LinearLayout.HORIZONTAL);
        common.setGravity(Gravity.CENTER);
//        common.setWeightSum(1f);

        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout c1 = new LinearLayout(getContext());
        c1.setOrientation(LinearLayout.VERTICAL);
        LinearLayout c2 = new LinearLayout(getContext());
        c2.setOrientation(LinearLayout.VERTICAL);

        ViewGroup.LayoutParams pc1 = new LinearLayout.LayoutParams(-2,-2);
        ViewGroup.LayoutParams pc2 = new LinearLayout.LayoutParams(-2,-2);
        common.addView(c1, pc1);
        common.addView(c2, pc2);

//        c1.setWeightSum(0.3f);
//        c2.setWeightSum(0.7f);

        TextView condition = new TextView(getContext());
        condition.setText("TODO...");
        condition.setGravity(Gravity.CENTER);
        c1.addView(condition/*, params*/);

        TextView startTime = new TextView(getContext());
        startTime.setText(lessonDate.getLesson().getTime().getStartTime().toString());
        startTime.setGravity(Gravity.CENTER);
        c1.addView(startTime/*, params*/);

        TextView endTime = new TextView(getContext());
        endTime.setText(lessonDate.getLesson().getTime().getEndTime().toString());
        endTime.setGravity(Gravity.CENTER);
        c1.addView(endTime/*, params*/);

        TextView type = new TextView(getContext());
        type.setText(lessonDate.getLesson().getType().getName());
        type.setGravity(Gravity.CENTER);
        c2.addView(type/*, params*/);

        TextView discipline = new TextView(getContext());
        discipline.setText(lessonDate.getLesson().getDiscipline().getShortName());
        discipline.setGravity(Gravity.CENTER);
        c2.addView(discipline/*, params*/);

        TextView teachers = new TextView(getContext());
        StringBuilder teachersList = new StringBuilder();
        for(Teacher t: lessonDate.getLesson().getTeachers()){
            teachersList.append(t.getShortName()).append("\n");
        }
        teachers.setText(teachersList.toString().trim());
        teachers.setGravity(Gravity.CENTER);
        c2.addView(teachers/*, params*/);



/*        TextView t1 = new TextView(getContext());
        TextView t2 = new TextView(getContext());
        common.addView(t1, params);
        common.addView(t2, params);
        t1.setText(lessonDate.getLesson().getDiscipline().getFullName());
        t2.setText(lessonDate.getLesson().getType().getName());*/
        return common;
    }

    @Override
    public void onActivityResult(int RequestCode, int resultCode, Intent data) {
        if (RequestCode == 0) {
            showList();
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