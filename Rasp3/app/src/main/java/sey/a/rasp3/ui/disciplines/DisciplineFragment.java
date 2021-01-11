package sey.a.rasp3.ui.disciplines;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class DisciplineFragment extends Fragment {
    View root;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);
        Button addButton = root.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisciplineFragment.super.getContext(), DisciplineCreate.class);
                startActivityForResult(intent, 0);
            }
        });

        setAddButtonAction((Button) root.findViewById(R.id.add));
        showList((LinearLayout) root.findViewById(R.id.layout));
        return root;
    }

    private void showList(ViewGroup group) {
        group.removeAllViews();
        if (General.getSchedule() == null) {
            return;
        }
        List<Discipline> disciplines = General.getSchedule().getDisciplines();
        Collections.sort(disciplines, Discipline.nameComparator);
        for (Discipline d : disciplines) {
            Button b = new Button(getContext());
            b.setText(d.getName());
            final String name = d.getShortName();
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });
            group.addView(b);
        }
    }

    private void setAddButtonAction(Button add) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DisciplineCreate disciplineCreate = new DisciplineCreate();
                final AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setView(disciplineCreate.createForm(getContext()))
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
                        if (disciplineCreate.positiveClick()) {
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