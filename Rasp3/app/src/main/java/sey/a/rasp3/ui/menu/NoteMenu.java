package sey.a.rasp3.ui.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.raw.RawNote;
import sey.a.rasp3.service.NoteService;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.CreateDetails;
import sey.a.rasp3.ui.defaults.CreateDialog;
import sey.a.rasp3.ui.note.NoteCreate;

public class NoteMenu {
    private NoteService service = General.getNoteService();
    private FragmentManager fragmentManager = null;
    AlertDialog dialog = null;

    public AlertDialog createDialog(Context context, LessonDate ld) {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setView(createView(context, ld));
        return dialog;
    }

    public View createView(Context context, LessonDate ld) {
        LinearLayout LL = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LL.setOrientation(LinearLayout.VERTICAL);

        LL.addView(details(context, ld), params);
        if(ld.getLastStatusNote()==null || ld.getLastStatusNote().getActivity()==Note.PLANNED) {
            LL.addView(canceled(context, ld), params);
        }else if(ld.getLastStatusNote().getActivity()==Note.CANCELED) {
            LL.addView(planned(context, ld), params);
        }
        LL.addView(type(context, ld), params);
        LL.addView(discipline(context, ld));
        LL.addView(teachers(context, ld));
        LL.addView(auditorium(context, ld));

        return LL;
    }

    private View details(final Context context, final LessonDate ld) {
        final Button button = new Button(context);
        button.setText("Подробнее");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateDetails<LessonDate> createDetails = new CreateDetails<>();
                createDetails.show(context, fragmentManager, ld);
                dialog.dismiss();
            }
        });
        return button;
    }

    private View canceled(final Context context, final LessonDate ld) {
        Button button = new Button(context);
        button.setText("Отменить");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawNote raw = new RawNote();
                raw.setActivity(Note.CANCELED);
                raw.setValue("Пара отменена");
                ld.getNotes().add(service.create(ld, raw));
                General.saveSchedule();
                dialog.dismiss();
            }
        });
        return button;
    }

    private View planned(Context context, final LessonDate ld) {
        Button button = new Button(context);
        button.setText("Запланировать");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawNote raw = new RawNote();
                raw.setActivity(Note.PLANNED);
                ld.getNotes().add(service.create(ld, raw));
                General.saveSchedule();
                dialog.dismiss();
            }
        });
        return button;
    }

    private View type(final Context context, final LessonDate ld) {
        Button button = new Button(context);
        button.setText("Изменить тип пары");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawNote raw = new RawNote();
                raw.setActivity(Note.TYPE);
                raw.setValue("Пара запланирована");
                printDialog(context, ld, raw);
            }
        });
        return button;
    }

    private View discipline(final Context context, final LessonDate ld){
        Button view = new Button(context);
        view.setText("Изменить дисциплину");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawNote raw = new RawNote();
                raw.setActivity(Note.DISCIPLINE);
                printDialog(context, ld, raw);
            }
        });
        return view;
    }

    private View teachers(final Context context, final LessonDate ld){
        Button view = new Button(context);
        view.setText("Изменить преподавателей");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawNote raw = new RawNote();
                raw.setActivity(Note.TEACHER);
                printDialog(context, ld, raw);
            }
        });
        return view;
    }

    private View auditorium(final Context context, final LessonDate ld){
        Button view = new Button(context);
        view.setText("Изменить аудиторию");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawNote raw = new RawNote();
                raw.setActivity(Note.AUDITORIUM);
                printDialog(context, ld, raw);
            }
        });
        return view;
    }

    private void printDialog(final Context context, final LessonDate ld, RawNote raw) {
        final Note note = service.create(ld, raw);
        final NoteCreate create = new NoteCreate();
        CreateDialog<NoteCreate, Note> createDialog = new CreateDialog<>();
        final AlertDialog menuDialog = createDialog.show(context, create, note);
        menuDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (create.positiveClick()) {
                    note.setDateTime(Calendar.getInstance());
                    ld.getNotes().add(note);
                    General.saveSchedule();
                    menuDialog.dismiss();
                    dialog.dismiss();
                }else {
                    Toast.makeText(context, "Некорректный ввод", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}