package sey.a.rasp3.ui.type;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.raw.RawType;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class TypeCreate implements DefaultCreate<Type> {
    private View root;
    private boolean update = false;
    private Type type = null;

    public View createForm(Context context, Type type) {
        this.type = type;
        RawType raw = General.wet(type);
        root = View.inflate(context, R.layout.fragment_type_create, null);
        final EditText name = root.findViewById(R.id.fullName);
        name.setText(raw.getName());
        update = true;
        return root;
    }

    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_type_create, null);
        update = false;
        return root;
    }

    public boolean positiveClick() {
        final EditText name = root.findViewById(R.id.fullName);

        String fN = name.getText().toString();
        if (!fN.equals("")) {
            if (update) {
                General.update(type, new RawType(fN));
            } else {
                General.create(new RawType(fN));
            }
            return true;
        } else {
            return false;
        }
    }
}