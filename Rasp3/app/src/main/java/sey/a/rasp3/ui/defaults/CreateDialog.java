package sey.a.rasp3.ui.defaults;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import sey.a.rasp3.model.Default;

public class CreateDialog<C extends DefaultCreate<T>, T extends Default> {
    public AlertDialog show(Context context, C create, T t) {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(create.createForm(context, t))
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("OK", null)
                .create();
        dialog.show();
        return dialog;
    }

    public AlertDialog show(Context context, C create) {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(create.createForm(context))
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("OK", null)
                .create();
        dialog.show();
        return dialog;
    }
}
