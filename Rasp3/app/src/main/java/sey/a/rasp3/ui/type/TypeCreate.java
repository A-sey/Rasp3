package sey.a.rasp3.ui.type;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sey.a.rasp3.R;
import sey.a.rasp3.shell.General;

public class TypeCreate extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_type_create);

        final EditText name = findViewById(R.id.fullName);
        Button cancel = findViewById(R.id.cancel);
        Button ok = findViewById(R.id.ok);

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
                if (!fN.equals("")) {
                    General.createType(fN);
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
