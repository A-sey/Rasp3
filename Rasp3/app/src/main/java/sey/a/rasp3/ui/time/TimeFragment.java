package sey.a.rasp3.ui.time;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Time;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.disciplines.DisciplineCreate;

public class TimeFragment extends Fragment {
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);
        setAddButtonAction((Button) root.findViewById(R.id.add));
        showList((LinearLayout) root.findViewById(R.id.layout));
        return root;
    }

    private void showList(ViewGroup group) {
        group.removeAllViews();
        List<Time> times = General.getSchedule().getTimes();
        Collections.sort(times, Time.startTimeComparator);
        for (Time t : General.getSchedule().getTimes()) {
            Button b = new Button(getContext());
            b.setText(t.getName());
            final String name = t.getStartTime().toString();
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
                final TimeCreate timeCreate = new TimeCreate();
                final AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setView(timeCreate.createForm(getContext()))
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
                        if (timeCreate.positiveClick()) {
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