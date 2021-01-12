package sey.a.rasp3.ui.schedule;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import sey.a.rasp3.R;
import sey.a.rasp3.raw.RawSchedule;
import sey.a.rasp3.shell.Dates;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class ScheduleCreate implements DefaultCreate {
    private View root;

    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_schedule_create, null);
        return root;
    }

    public boolean positiveClick() {
        EditText name = root.findViewById(R.id.name);
        EditText startDate = root.findViewById(R.id.start_date);
        EditText endDate = root.findViewById(R.id.end_date);
        Calendar sDate = Dates.parseDate(startDate.getText().toString(), "\\.");
        Calendar eDate = Dates.parseDate(endDate.getText().toString(), "\\.");
        if (!name.getText().toString().equals("") && sDate != null && eDate != null) {
            General.createSchedule(new RawSchedule(name.getText().toString(), sDate, eDate));
            return true;
        } else {
            return false;
        }
    }
}
