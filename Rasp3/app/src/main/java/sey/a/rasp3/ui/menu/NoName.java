package sey.a.rasp3.ui.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import sey.a.rasp3.model.Default;
import sey.a.rasp3.raw.RawDefault;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class NoName {
    AlertDialog dialog = null;

    public AlertDialog createDialog(Context context, Default o) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(createView(context, o));
        this.dialog = dialog;
        return dialog;
    }

    public View createView(Context context, Default o) {
        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ///////
        LL.addView(elementUpdate(context, o), params);
        LL.addView(elementDelete(context, o), params);
        ///////
        return LL;
    }

    private View elementUpdate(final Context context, final Default o) {
        Button button = new Button(context);
        button.setText("Изменить");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawDefault raw = General.wet(o);
                //////////
                DefaultCreate create = General.findCreate(o);
                create.createForm(context, raw);

                //////////
                General.update(o, raw);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        return button;
    }

    private View elementDelete(Context context, final Default o) {
        Button button = new Button(context);
        button.setText("Удалить");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                General.delete(o);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        return button;
    }
}
