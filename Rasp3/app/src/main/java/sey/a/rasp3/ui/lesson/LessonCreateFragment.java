package sey.a.rasp3.ui.lesson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sey.a.rasp3.R;

public class LessonCreateFragment extends Fragment {
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final LessonCreate lessonCreate = new LessonCreate();
        root = lessonCreate.createForm(getContext());
        LinearLayout buttons = root.findViewById(R.id.buttons);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
        );
        Button cancel = new Button(getContext());
        cancel.setText("Отмена");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
//                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        Button ok = new Button(getContext());
        buttons.addView(cancel, param);
        ok.setText("Ок");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lessonCreate.positiveClick()) {
                    Toast.makeText(getContext(), "Некорректный ввод", Toast.LENGTH_SHORT).show();
                } else {
                    requireActivity().onBackPressed();
//                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        buttons.addView(ok, param);
        return root;
    }
}
