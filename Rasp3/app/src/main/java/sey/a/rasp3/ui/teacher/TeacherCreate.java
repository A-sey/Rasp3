package sey.a.rasp3.ui.teacher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sey.a.rasp3.R;
import sey.a.rasp3.raw.RawTeacher;
import sey.a.rasp3.shell.General;

public class TeacherCreate extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_teacher_create);

        final EditText fullName = findViewById(R.id.fullName);
        final EditText shortName = findViewById(R.id.shortName);
        final EditText comment = findViewById(R.id.comment);
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button ok = findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fN = fullName.getText().toString();
                String sN = shortName.getText().toString();
                String c = comment.getText().toString();
                if (!fN.equals("") && !sN.equals("")) {
//                    General.createTeacher(new RawTeacher(fN, sN, c));
                    General.create(new RawTeacher(fN, sN, c));
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
