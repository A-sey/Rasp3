package sey.a.rasp3.ui.schedule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import sey.a.rasp3.R;
import sey.a.rasp3.model.Schedule;
import sey.a.rasp3.service.GeneralXml;
import sey.a.rasp3.shell.Files;
import sey.a.rasp3.shell.General;

public class SelectScheduleFragment extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_discipline_list);

        Button add = findViewById((R.id.add));

        showList();
    }

    private void showList() {
        LinearLayout LL = findViewById(R.id.layout);
        final Files files = new Files(getBaseContext());
        for (final String s : files.getFilesList()) {
            Button b = new Button(this);
            b.setText(s);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String xml = files.readFile(s);
                    Schedule schedule = GeneralXml.scheduleXmlUnpacking(xml);
                    General.setSchedule(schedule);
                    finish();
                }
            });
            LL.addView(b);
        }
    }
}
