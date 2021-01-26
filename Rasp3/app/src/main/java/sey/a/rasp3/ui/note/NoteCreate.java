package sey.a.rasp3.ui.note;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class NoteCreate implements DefaultCreate<Note> {
    private View root;
    private Note note;

    @Override
    public View createForm(Context context, Note note) {
        this.note = note;
        root = View.inflate(context, R.layout.fragment_note_create, null);
        TextView fieldName = root.findViewById(R.id.value_label);
//        EditText value = root.findViewById(R.id.value_text);
//        EditText text = root.findViewById(R.id.text_text);
        return root;
    }

    @Override
    public View createForm(Context context) {
        return null;
    }

    @Override
    public boolean positiveClick() {
        EditText value = root.findViewById(R.id.value_text);
        EditText text = root.findViewById(R.id.text_text);
        if (!value.getText().toString().equals("")) {
            note.setValue(value.getText().toString());
            note.setText(text.getText().toString());
            return true;
        }
        return false;
    }
}
