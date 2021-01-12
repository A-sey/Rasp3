package sey.a.rasp3.ui.time;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import sey.a.rasp3.R;
import sey.a.rasp3.raw.RawTime;
import sey.a.rasp3.shell.Clocks;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.DefaultCreate;

public class TimeCreate implements DefaultCreate {

    private View root;

    public View createForm(Context context) {
        root = View.inflate(context, R.layout.fragment_time_create, null);
        final EditText start = root.findViewById(R.id.startText);
        final EditText end = root.findViewById(R.id.endText);
        /*final TimePicker stp = root.findViewById(R.id.startTP);
        final TimePicker etp = root.findViewById(R.id.endTP);
        stp.setIs24HourView(true);
        stp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                String text = Clocks.twoDigits(i) + ":" + Clocks.twoDigits(i1);
                start.setText(text);
            }
        });

        etp.setIs24HourView(true);
        etp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                String text = Clocks.twoDigits(i) + ":" + Clocks.twoDigits(i1);
                end.setText(text);
            }
        });*/
        return root;
    }

    public boolean positiveClick() {
        final EditText name = root.findViewById(R.id.name);
        final EditText start = root.findViewById(R.id.startText);
        final EditText end = root.findViewById(R.id.endText);

        String fN = name.getText().toString();
        Clocks sClock = new Clocks(start.getText().toString());
        Clocks eClock = new Clocks(end.getText().toString());
        if (!fN.equals("") && !start.getText().toString().equals("") && !end.getText().toString().equals("")) {
            General.create(new RawTime(fN, sClock, eClock));
            return true;
        } else {
            return false;
        }
    }
}
