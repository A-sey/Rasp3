package sey.a.rasp3.shell;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Files {
    private final String path = "rasp";
    private String name;
    private Context context;

    public Files(Context context) {
        this.context = context;
    }

    public void writeFile(String name, String text) {
        try {
            File dir = context.getExternalFilesDir(path);
            File file = new File(dir, name);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(text.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Text cannot be written", Toast.LENGTH_SHORT).show();
        }
    }

    public String readFile(String name) {
        StringBuilder text = new StringBuilder();
        try {
            File dir = context.getExternalFilesDir(path);
            File file = new File(dir, name);
            FileInputStream fis = new FileInputStream(file);
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNextLine()) {
                text.append(scanner.nextLine()).append('\n');
            }
            fis.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Unable to read the file", Toast.LENGTH_SHORT).show();
        }
        return text.toString().trim().replace("\n", "").replace("\t", "");
    }

    public List<String> getFilesList() {
        File dir = context.getExternalFilesDir(path);
        List<String> list = new ArrayList<>();
        for (File f : dir.listFiles()) {
            list.add(f.getName());
        }
        return list;
    }
}