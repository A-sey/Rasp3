package sey.a.rasp3.service;

import sey.a.rasp3.model.LessonDate;
import sey.a.rasp3.model.Note;
import sey.a.rasp3.shell.Xmls;

public class NoteService {
    public Note create(LessonDate lessonDate, int activity, String value, String text){
        return null;
    }

    public Note fastCreate(){
        return null;
    }

    public Note update(){
        return null;
    }

    public boolean hide(){
        return false;
    }

    public void delete(){

    }

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

    public Note fromXML(String text, LessonDate parent){
        Note note = new Note();
        note.setId(Long.getLong(Xmls.extractString("id", text)));
        note.setLessonDate(parent);
        note.setDateTime(Xmls.extractDate("dateTime",text));
        return note;
    }
}
