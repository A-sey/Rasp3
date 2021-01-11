package sey.a.rasp3.ui.time;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import sey.a.rasp3.R;
import sey.a.rasp3.raw.RawTime;
import sey.a.rasp3.shell.Clocks;
import sey.a.rasp3.shell.General;

public class TimeCreate extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_time_create);

        final EditText name = findViewById(R.id.name);
        final EditText start = findViewById(R.id.startText);
        final TimePicker stp = findViewById(R.id.startTP);
        final EditText end = findViewById(R.id.endText);
        final TimePicker etp = findViewById(R.id.endTP);
        Button cancel = findViewById(R.id.cancel);
        Button ok = findViewById(R.id.ok);

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
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fN = name.getText().toString();
                Clocks sClock = new Clocks(start.getText().toString());
                Clocks eClock = new Clocks(end.getText().toString());
                if (!fN.equals("") && !start.getText().toString().equals("") && !end.getText().toString().equals("")) {
                    General.create(new RawTime(fN, sClock, eClock));
                    setResult(0);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка ввода данных", Toast.LENGTH_LONG).show();
                    setResult(1);
                }
            }
        });
    }
}
