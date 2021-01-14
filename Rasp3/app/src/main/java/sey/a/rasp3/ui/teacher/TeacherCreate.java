package sey.a.rasp3.ui.teacher;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.raw.RawTeacher;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class TeacherCreate implements DefaultCreate<Teacher> {

    private View root;
    private boolean update = false;
    private Teacher teacher = null;

    @Override
    public View createForm(Context context, Teacher teacher) {
        this.teacher = teacher;
        RawTeacher raw = General.wet(teacher);
        root = View.inflate(context, R.layout.fragment_teacher_create, null);
        final EditText fullName = root.findViewById(R.id.fullName);
        final EditText shortName = root.findViewById(R.id.shortName);
        final EditText comment = root.findViewById(R.id.comment);
        fullName.setText(raw.getName());
        shortName.setText(raw.getShortName());
        comment.setText(raw.getComment());
        update = true;
        return root;
    }

    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_teacher_create, null);
        update = false;
        return root;
    }

    public boolean positiveClick() {
        final EditText fullName = root.findViewById(R.id.fullName);
        final EditText shortName = root.findViewById(R.id.shortName);
        final EditText comment = root.findViewById(R.id.comment);
        String fN = fullName.getText().toString();
        String sN = shortName.getText().toString();
        String c = comment.getText().toString();
        if (!fN.equals("") && !sN.equals("")) {
            if (update) {
                General.update(teacher, new RawTeacher(fN, sN, c));
            } else {
                General.create(new RawTeacher(fN, sN, c));
            }
            return true;
        } else {
            return false;
        }
    }
}
