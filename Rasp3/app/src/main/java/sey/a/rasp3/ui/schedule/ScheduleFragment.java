package sey.a.rasp3.ui.schedule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.raw.RawSchedule;
import sey.a.rasp3.service.GeneralXml;
import sey.a.rasp3.shell.Files;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.menu.MenuItems;
import sey.a.rasp3.ui.menu.PopUpMenu;

public class ScheduleFragment extends Fragment {
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);
        setAddButtonAction((Button) root.findViewById(R.id.add));
        showList((LinearLayout) root.findViewById(R.id.layout));
        return root;
    }

    private void showList(final ViewGroup group) {
        group.removeAllViews();
        final Files files = new Files(getContext());
        for (final String s : files.getFilesList()) {
            Button b = new Button(getContext());
            b.setText(s);
            b.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View view) {
                    String xml = files.readFile(s);
                    Schedule schedule = GeneralXml.scheduleXmlUnpacking(xml);
                    General.setSchedule(schedule);
                    requireActivity().onBackPressed();
//                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopUpMenu<Schedule, RawSchedule> popUpMenu = new PopUpMenu<>();
                    String xml = files.readFile(s);
                    AlertDialog dialog = popUpMenu.createDialog(getContext(), GeneralXml.scheduleXmlUnpacking(xml));
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            showList(group);
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
            group.addView(b);
        }
    }

    private void setAddButtonAction(Button add) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ScheduleCreate scheduleCreate = new ScheduleCreate();
                final AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setView(scheduleCreate.createForm(getContext()))
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("OK", null)
                        .create();
                dialog.show();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (scheduleCreate.positiveClick()) {
                            showList((LinearLayout) root.findViewById(R.id.layout));
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Ошибка ввода данных", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
