package sey.a.rasp3.ui.type;

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

import sey.a.rasp3.R;
import sey.a.rasp3.model.Teacher;
import sey.a.rasp3.model.Type;
import sey.a.rasp3.shell.General;

public class TypeFragment extends Fragment {
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_type_list, container, false);
        Button addButton = root.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TypeFragment.super.getContext(), TypeCreate.class);
                startActivityForResult(intent, 0);
            }
        });
        showList();
        return root;
    }

    private void showList() {
        LinearLayout LL = root.findViewById(R.id.layout);
        LL.removeAllViews();
        if(General.getSchedule()==null){
            return;
        }
        for (Type t : General.getSchedule().getTypes()) {
            Button b = new Button(getContext());
            b.setText(t.getName());
            final String name = t.getName().toLowerCase();
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                }
            });
            LL.addView(b);
        }
    }

    @Override
    public void onActivityResult(int RequestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            showList();
        }
    }
}