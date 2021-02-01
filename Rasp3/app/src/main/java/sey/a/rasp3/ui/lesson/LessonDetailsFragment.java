package sey.a.rasp3.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Lesson;
import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawLesson;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;

public class LessonDetailsFragment extends Fragment {
    private View root;
    private LessonDate lessonDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        root = inflater.inflate(R.layout.fragment_lesson_details, null);
        fillFields();
        return root;
    }

    private void fillFields() {
        Lesson lesson = lessonDate.getLesson();
        RawLesson rawLesson = General.wet(lesson);
        Discipline discipline = lesson.getDiscipline();
        Type type = lesson.getType();
        Time time = lesson.getTime();
        List<Teacher> teachers = lesson.getTeachers();
        List<Note> notes = lessonDate.getNotes();


        if (rawLesson.getDayOfWeek() == 0) {
            ConstraintLayout datesConstraint = root.findViewById(R.id.dates_constraint);
            datesConstraint.removeAllViews();

            LinearLayout datesLayout = new LinearLayout(getContext());
            datesLayout.setOrientation(LinearLayout.VERTICAL);
            datesConstraint.addView(datesLayout);

            TextView datesTV = new TextView(getContext());
            datesTV.setText("Даты пары:");
            datesLayout.addView(datesTV);

            ScrollView scrollView = new ScrollView(getContext());
            datesLayout.addView(scrollView);

            TextView dates = new TextView(getContext());
            scrollView.addView(dates);
            StringBuilder dateText = new StringBuilder();
            for (LessonDate ld : lesson.getLessonDates()) {
                dateText.append(Dates.dateToString(ld.getDate())).append("\t");
            }
            dates.setText(dateText);
        } else {
            TextView lessonStart = root.findViewById(R.id.lesson_start);
            TextView lessonEnd = root.findViewById(R.id.lesson_end);
            TextView weekType = root.findViewById(R.id.week_type);
            TextView dayOfWeek = root.findViewById(R.id.day_of_week);

            lessonStart.setText(Dates.dateToString(rawLesson.getDateFrom()));
            lessonEnd.setText(Dates.dateToString(rawLesson.getDateTo()));
            String[] weekTypeArray = new String[]{"Каждая", "Нечётная", "Чётная"};
            weekType.setText(weekTypeArray[rawLesson.getWeekType()]);
            String[] dayOfWeekArray = new String[]{"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
            dayOfWeek.setText(dayOfWeekArray[rawLesson.getDayOfWeek()-1]);
        }

        TextView timeName = root.findViewById(R.id.time_name);
        TextView timeStart = root.findViewById(R.id.time_start);
        TextView timeEnd = root.findViewById(R.id.time_end);
        TextView typeName = root.findViewById(R.id.type_name);
        TextView disciplineName = root.findViewById(R.id.discipine_full_name);
        TextView teacherName = root.findViewById(R.id.teacher_full_name);
        TextView auditoriumName = root.findViewById(R.id.auditorium);

        timeName.setText(time.getName());
        timeStart.setText(time.getStartTime().toString());
        timeEnd.setText(time.getEndTime().toString());
        typeName.setText(type.getName());
        disciplineName.setText(discipline.getName());
        StringBuilder teacherText = new StringBuilder();
        for (Teacher t : teachers) {
            teacherText.append(t.getName()).append("\n");
        }
        teacherName.setText(teacherText.toString().trim());
        auditoriumName.setText(lesson.getAuditorium());

        if(notes.size()>0){
            LinearLayout notesLayout = root.findViewById(R.id.notes_layout);
            for(Note n : notes){
                TextView tv = new TextView(getContext());
                tv.setText(n.toString());
                notesLayout.addView(tv);
            }
        }

    }

    public void setLessonDate(LessonDate lessonDate) {
        this.lessonDate = lessonDate;
    }
}
