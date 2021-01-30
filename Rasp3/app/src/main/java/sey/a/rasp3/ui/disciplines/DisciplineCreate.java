package sey.a.rasp3.ui.disciplines;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.raw.RawDiscipline;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class DisciplineCreate implements DefaultCreate<Discipline> {
    private View root;
    private boolean update = false;
    private Discipline discipline = null;

    public View createForm(final Context context, Discipline discipline) {
        this.discipline = discipline;
        RawDiscipline raw = General.wet(discipline);
        root = View.inflate(context, R.layout.fragment_discipline_create, null);
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
        root = View.inflate(context, R.layout.fragment_discipline_create, null);
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
                    shortName.setHint("Сокращённое название");
                } else {
                    shortName.setHint(trimName(fullName.getText().toString()));
                }
            }
        });
    }

    private String trimName(String fullName) {
        if (fullName.length() < 1) {
            return "";
        }
        StringBuilder shortName = new StringBuilder();
        String[] words = fullName.split(" ");
        for (String word : words) {
            shortName.append(word.substring(0, 1));
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
                General.update(discipline, new RawDiscipline(fN, sN, c));
            } else {
                General.create(new RawDiscipline(fN, sN, c));
            }
            return true;
        } else {
            return false;
        }
    }
}
