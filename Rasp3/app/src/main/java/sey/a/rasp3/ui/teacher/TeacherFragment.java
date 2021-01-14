package sey.a.rasp3.ui.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.raw.RawTeacher;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.menu.PopUpMenu;

public class TeacherFragment extends Fragment {
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);
        setAddButtonAction((Button) root.findViewById(R.id.add));
        showList((LinearLayout) root.findViewById(R.id.layout));
        return root;
    }

    private void showList(final ViewGroup group) {
        group.removeAllViews();
        if(General.getSchedule()==null){
            return;
        }
        List<Teacher> teachers = General.getSchedule().getTeachers();
        Collections.sort(teachers, Teacher.nameComparator);
        for (final Teacher t : teachers) {
            if(t.getHide()!=0){
                continue;
            }
            Button b = new Button(getContext());
            b.setText(t.getName());
            final String name = t.getShortName();
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopUpMenu<Teacher, RawTeacher> popUpMenu = new PopUpMenu<>();
                    AlertDialog dialog = popUpMenu.createDialog(getContext(), t);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            showList(group);
                        }
                    });
                    dialog.show();
                }
            });
            group.addView(b);
        }
    }

    private void setAddButtonAction(Button add) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TeacherCreate teacherCreate = new TeacherCreate();
                final AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setView(teacherCreate.createForm(getContext()))
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
                        if (teacherCreate.positiveClick()) {
                            showList((LinearLayout)root.findViewById(R.id.layout));
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