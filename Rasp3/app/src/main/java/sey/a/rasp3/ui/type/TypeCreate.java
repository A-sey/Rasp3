package sey.a.rasp3.ui.type;

import android.content.Context;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        colorAction();
        final EditText name = root.findViewById(R.id.fullName);
        name.setText(raw.getName());
        final EditText color = root.findViewById(R.id.type_color);
        color.setText(String.format("#%08X", (raw.getColor())));
        update = true;
        return root;
    }

    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_type_create, null);
        colorAction();
        update = false;
        return root;
    }

    private void colorAction(){
        final EditText colorText = root.findViewById(R.id.type_color);
        colorText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String text = colorText.getText().toString();
                colorText.setBackgroundColor(textToColor(text));
                return true;
            }
        });
    }

    private int textToColor(String text){
        Pattern pattern = Pattern.compile("#[0-9a-fA-F]{8}");
        Matcher matcher = pattern.matcher(text);
        if(matcher.find() && text.length()==9){
            return Color.parseColor(text);
        }
        return 0;
    }

    public boolean positiveClick() {
        final EditText name = root.findViewById(R.id.fullName);
        final EditText color = root.findViewById(R.id.type_color);

        String fN = name.getText().toString();
        int col = textToColor(color.getText().toString());
        if (!fN.equals("")) {
            if (update) {
                General.update(type, new RawType(fN, col));
            } else {
                General.create(new RawType(fN, col));
            }
            return true;
        } else {
            return false;
        }
    }
}