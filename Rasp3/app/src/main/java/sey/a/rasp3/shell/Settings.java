package sey.a.rasp3.shell;

public class Settings {
    private static Files files;
    private static String selectedSchedule;

    public static void setFiles(Files files) {
        Settings.files = files;
    }

    public static String getSelectedSchedule() {
        load();
        return selectedSchedule;
    }

    public static void setSelectedSchedule(String selectedSchedule) {
        Settings.selectedSchedule = selectedSchedule;
        save();
    }

    private static void save(){
        String text = "";
        text += Xmls.stringToXml("selectedSchedule", selectedSchedule);
        files.writeFile("settings", text, Files.SETTING);
    }

    private static void load(){
        String text = files.readFile("settings", Files.SETTING);
        selectedSchedule = Xmls.extractString("selectedSchedule", text);
    }
}
