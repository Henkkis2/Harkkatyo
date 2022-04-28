package com.example.harkkatyo;
// SaveFile was difficult to build, because I had to get access to write internal memory. https://www.tabnine.com/code/java/methods/android.content.Context/openFileOutput
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileReadSave {

    public ArrayList<String> openFile(String fileaddress, Context context) {
        ArrayList<String> movies = new ArrayList<>();
        try {
            String fileAddressWithTXT = fileaddress + ".txt";
            InputStream in = context.openFileInput(fileAddressWithTXT);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";
            while ((s = br.readLine()) != null) {
                movies.add(s);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public void saveFile(String fileAddress, String text, Context context) {
        try {
            String fileAddressWithTXT = fileAddress + ".txt";
            OutputStreamWriter file = new OutputStreamWriter(context.openFileOutput(fileAddressWithTXT, Context.MODE_APPEND));
            file.write(text + "\n");
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
