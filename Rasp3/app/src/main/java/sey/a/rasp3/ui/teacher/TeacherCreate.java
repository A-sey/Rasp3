package sey.a.rasp3.ui.teacher;

import android.content.Context;
import android.view.KeyEvent;
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
        autoFillShortName();
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
        autoFillShortName();
        update = false;
        return root;
    }

    private void autoFillShortName() {
        final EditText fullName = root.findViewById(R.id.fullName);
        final EditText shortName = root.findViewById(R.id.shortName);
        fullName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                shortName.setHint(trimName(fullName.getText().toString()));
                return true;
            }
        });
        fullName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (fullName.getText().toString().equals("")) {
                    shortName.setHint("Сокращённое имя");
                } else {
                    shortName.setHint(trimName(fullName.getText().toString()));
                }
            }
        });
    }

    private String trimName(String fullName) {
        if (fullName.length() == 0) {
            return "";
        }
        StringBuilder shortName;
        String[] words = fullName.split(" ");
        shortName = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            shortName.append(" ").append(words[i].substring(0, 1)).append(".");
        }
        return shortName.toString();
    }

    public boolean positiveClick() {
        final EditText fullName = root.findViewById(R.id.fullName);
        final EditText shortName = root.findViewById(R.id.shortName);
        final EditText comment = root.findViewById(R.id.comment);
        String fN = fullName.getText().toString();
        String sN = shortName.getText().toString();
        if (sN.equals("")) {
            sN = trimName(fN);
        }
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
