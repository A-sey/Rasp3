package sey.a.rasp3.shell;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Files {
    public static final int SCHEDULE = 0;
    public static final int SETTING = 1;
    private String name;
    private Context context;

    private String getFolderByParam(int param){
        switch (param){
            case SCHEDULE: return "rasp";
            case SETTING: return "settings";
            default: return null;
        }
    }

    public Files(Context context) {
        this.context = context;
    }

    public void writeFile(String name, String text, int folder) {
        try {
            File dir = context.getExternalFilesDir(getFolderByParam(folder));
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

    public String readFile(String name, int folder) {
        StringBuilder text = new StringBuilder();
        try {
            File dir = context.getExternalFilesDir(getFolderByParam(folder));
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

    public List<String> getFilesList(int folder) {
        File dir = context.getExternalFilesDir(getFolderByParam(folder));
        List<String> list = new ArrayList<>();
        for (File f : dir.listFiles()) {
            list.add(f.getName());
        }
        return list;
    }

    public void removeFile(String name, int folder){
        File dir = context.getExternalFilesDir(getFolderByParam(folder));
        File file = new File(dir, name);
        if(file.delete()){
            Toast.makeText(context, "Файл удалён", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Удаление не получилось", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveImage(Bitmap bitmap){
        File dir = context.getExternalFilesDir(null);
        File file = new File(dir, "image.png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, bytes);
            fos.write(bytes.toByteArray());
            fos.flush();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}