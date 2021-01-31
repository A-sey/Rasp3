package sey.a.rasp3.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sey.a.rasp3.R;
import sey.a.rasp3.model.LessonDate;

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

    private void fillFields(){
        TextView timeName = root.findViewById(R.id.time_name);
        timeName.setText(lessonDate.getLesson().getTime().getName());
    }

    public void setLessonDate(LessonDate lessonDate) {
        this.lessonDate = lessonDate;
    }
}
