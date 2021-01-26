package sey.a.rasp3.ui.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.raw.RawNote;
import sey.a.rasp3.service.NoteService;
import sey.a.rasp3.shell.General;
import sey.a.rasp3.ui.defaults.CreateDialog;
import sey.a.rasp3.ui.note.NoteCreate;

public class NoteMenu {
    private NoteService service = General.getNoteService();
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

        LL.addView(canceled(context, ld), params);
        LL.addView(planned(context, ld), params);
        LL.addView(type(context, ld), params);

        return LL;
    }

    private View canceled(final Context context, final LessonDate ld) {
        Button button = new Button(context);
        button.setText("Отменить");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RawNote raw = new RawNote();
                raw.setActivity(Note.CANCELED);
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
                printDialog(context, ld, raw);
            }
        });
        return button;
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
}