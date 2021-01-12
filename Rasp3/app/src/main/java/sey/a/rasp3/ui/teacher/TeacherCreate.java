package sey.a.rasp3.ui.teacher;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import sey.a.rasp3.R;
import sey.a.rasp3.raw.RawTeacher;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class TeacherCreate implements DefaultCreate {

    private View root;

    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_teacher_create, null);
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
            General.create(new RawTeacher(fN, sN, c));
            return true;
        } else {
            return false;
        }
    }
}
