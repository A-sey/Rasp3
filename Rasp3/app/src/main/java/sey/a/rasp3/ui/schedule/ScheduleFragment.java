package sey.a.rasp3.ui.schedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.service.GeneralXml;
import sey.a.rasp3.shell.Files;
import sey.a.rasp3.shell.General;

public class ScheduleFragment extends Fragment {
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);
        setAddButtonAction((Button) root.findViewById(R.id.add));
        showList((LinearLayout)root.findViewById(R.id.layout));
        return root;
    }

    private void showList(ViewGroup group) {
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
                    requireActivity().getSupportFragmentManager().popBackStack();
//                    finish();
                }
            });
            group.addView(b);
        }
    }

    private void setAddButtonAction(Button add){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ScheduleCreate scheduleCreate = new ScheduleCreate();
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setView(scheduleCreate.createForm(getContext()));
                adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        scheduleCreate.positiveClick();
                    }
                });
                adb.create().show();
            }
        });
    }
}
