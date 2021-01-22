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

public class PopUpMenu<T extends Default, D extends RawDefault> {
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
        MenuItems items = General.getMenuItems(o);
        if (items == null) {
            return null;
        }
        ///////
        if (items.getUpdate() == MenuItems.UPDATE_ON)
            LL.addView(elementUpdate(context, o), params);
        if (items.getDelete() == MenuItems.DELETE_ON)
            LL.addView(elementDelete(context, o), params);
        if (items.getHide() == MenuItems.HIDE_ON)
            LL.addView(elementHide(context, o), params);
        if (items.getShow() == MenuItems.SHOW_ON)
            LL.addView(elementShow(context, o));
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
                final AlertDialog updateDialog = createDialog.show(context, create, o);
                updateDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (create.positiveClick()) {
                            updateDialog.dismiss();
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

    private View elementHide(final Context context, final T o) {
        Button button = new Button(context);
        button.setText("Скрыть");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog warning = new AlertDialog.Builder(context)
                        .setPositiveButton("Скрыть", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                General.hide(o, true);
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        }).create();
                warning.setTitle("Внимание! Объект будет скрыт!");
                warning.show();
            }
        });
        return button;
    }

    private View elementShow(final Context context, final T o) {
        Button button = new Button(context);
        button.setText("Показать");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog warning = new AlertDialog.Builder(context)
                        .setPositiveButton("Показать", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                General.hide(o, false);
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        }).create();
                warning.setTitle("Объект будет возвращён в общий список");
                warning.show();
            }
        });
        return button;
    }

    private View elementDelete(final Context context, final T o) {
        Button button = new Button(context);
        button.setText("Удалить");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog warning = new AlertDialog.Builder(context)
                        .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                General.delete(o);
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        })
                        .create();
                warning.setTitle("Внимание! Объект и вся связанная с ним информация будут безвозвратно удалены!");
                warning.show();
            }
        });
        return button;
    }
}
