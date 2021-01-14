package sey.a.rasp3.ui.schedule;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Discipline;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.raw.RawDiscipline;
import sey.a.rasp3.raw.RawSchedule;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class ScheduleCreate implements DefaultCreate<Schedule> {
    private View root;
    private boolean update = false;
    private Schedule schedule = null;

    public View createForm(Context context, Schedule schedule){
        this.schedule = schedule;
        RawSchedule raw = General.wet(schedule);
        root = View.inflate(context, R.layout.fragment_schedule_create, null);
        EditText name = root.findViewById(R.id.name);
        EditText startDate = root.findViewById(R.id.start_date);
        EditText endDate = root.findViewById(R.id.end_date);
        name.setText(raw.getName());
        startDate.setText(Dates.dateToString(raw.getStart()));
        endDate.setText(Dates.dateToString(raw.getEnd()));
        update = true;
        return root;
    }

    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_schedule_create, null);
        update = false;
        return root;
    }

    public boolean positiveClick() {
        EditText name = root.findViewById(R.id.name);
        EditText startDate = root.findViewById(R.id.start_date);
        EditText endDate = root.findViewById(R.id.end_date);
        Calendar sDate = Dates.parseDate(startDate.getText().toString(), "\\.");
        Calendar eDate = Dates.parseDate(endDate.getText().toString(), "\\.");
        if (!name.getText().toString().equals("") && sDate != null && eDate != null) {
            if(update){
                General.update(schedule, new RawSchedule(name.getText().toString(), sDate, eDate));
            }else {
                General.createSchedule(new RawSchedule(name.getText().toString(), sDate, eDate));
            }
            return true;
        } else {
            return false;
        }
    }
}
