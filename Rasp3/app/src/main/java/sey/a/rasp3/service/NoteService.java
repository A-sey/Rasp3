package sey.a.rasp3.service;

import java.util.Calendar;

import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.raw.RawNote;
import sey.a.rasp3.shell.Xmls;

public class NoteService {
    private static Long maxId = 0L;

    public Note create(LessonDate lessonDate, RawNote raw) {
        Note note = new Note();
        note.setId(++maxId);
        note.setActivity(raw.getActivity());
        note.setLessonDate(lessonDate);
        note.setValue(raw.getValue());
        note.setText(raw.getText());
        note.setHide(0);
        note.setDateTime(Calendar.getInstance());
        return note;
    }

    /*public Note fastCreate(){
        return null;
    }

    public Note update(){
        return null;
    }

    public boolean hide(){
        return false;
    }

    public void delete(){
    }*/

    public String toXML(Note note) {
        StringBuilder xml = new StringBuilder();
        xml.append(Xmls.stringToXml("id", note.getId().toString()));
//        xml.append(Xmls.stringToXml("lessonDate", note.getLessonDate().getId().toString()));
        xml.append(Xmls.dateToXml("dateTime", note.getDateTime()));
        xml.append(Xmls.stringToXml("activity", Integer.toString(note.getActivity())));
        xml.append(Xmls.stringToXml("value", note.getValue()));
        xml.append(Xmls.stringToXml("text", note.getText()));
        xml.append(Xmls.stringToXml("hide", note.getHide().toString()));
        return xml.toString();
    }

    public Note fromXML(String text, LessonDate parent) {
        Note note = new Note();
        note.setLessonDate(parent);
        note.setId(Xmls.extractLong("id", text));
        if(note.getId()<=maxId){
            note.setId(++maxId);
        }else {
            maxId = note.getId();
        }
        note.setDateTime(Xmls.extractDate("dateTime", text));
        note.setActivity(Xmls.extractInteger("activity", text));
        note.setValue(Xmls.extractString("value", text));
        note.setText(Xmls.extractString("text", text));
        note.setHide(Xmls.extractInteger("hide", text));
        return note;
    }
}
