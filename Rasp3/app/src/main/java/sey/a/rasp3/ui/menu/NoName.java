package sey.a.rasp3.ui.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import sey.a.rasp3.model.Default;
import sey.a.rasp3.raw.RawDefault;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.CreateDialog;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class NoName<T extends Default, D extends RawDefault> {
    AlertDialog dialog = null;

    public AlertDialog createDialog(Context context, T o) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setView(createView(context, o));
        this.dialog = dialog;
        return dialog;
    }

    public View createView(Context context, T o) {
        LinearLayout LL = new LinearLayout(context);
        LL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ///////
        LL.addView(elementUpdate(context, o), params);
        LL.addView(elementDelete(context, o), params);
        ///////
        return LL;
    }

    private View elementUpdate(final Context context, final T o) {
        Button button = new Button(context);
        button.setText("Изменить");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //////////
                final DefaultCreate<T> create = General.findCreate(o);
                final CreateDialog<DefaultCreate<T>, T> createDialog = new CreateDialog<>();
                final AlertDialog dialog = createDialog.show(context, create, o);
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (create.positiveClick()) {
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Ошибка ввода данных", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //////////
            }
        });
        return button;
    }

    private View elementDelete(Context context, final T o) {
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
