package sey.a.rasp3.ui.type;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import sey.a.rasp3.R;
import sey.a.rasp3.raw.RawType;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class TypeCreate implements DefaultCreate {
    private View root;

    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_type_create, null);
        return root;
    }

    public boolean positiveClick() {
        final EditText name = root.findViewById(R.id.fullName);

        String fN = name.getText().toString();
        if (!fN.equals("")) {
            General.create(new RawType(fN));
            return true;
        } else {
            return false;
        }
    }
}
