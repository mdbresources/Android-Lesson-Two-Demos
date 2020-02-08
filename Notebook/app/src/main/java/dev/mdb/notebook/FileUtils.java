package dev.mdb.notebook;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileUtils {

    public static void writeToFile(String fileName, String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromFile(String fileName, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("file utils", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("file utils", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static boolean fileExists(String fileName, Context context) {
        try {
            context.openFileInput(fileName);
        }
        catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    public static ArrayList<Note> getNotes(Context context) throws JSONException {
        JSONObject notesObject = new JSONObject(FileUtils.readFromFile("notes.json", context));
        ArrayList<Note> notes = new ArrayList<>();
        JSONArray notesArray = notesObject.getJSONArray("notes");
        for (int i = 0; i < notesArray.length(); i++) {
            JSONObject note = notesArray.getJSONObject(i);
            Note properNote = new Note(note.getString("title"), note.getString("content"));
            notes.add(properNote);
        }
        return notes;
    }

    public static void addNote(Context context, String newNote) throws JSONException {
        JSONObject notesObject = new JSONObject(FileUtils.readFromFile("notes.json", context));
        JSONArray notesArray = notesObject.getJSONArray("notes");
        JSONObject noteObject = new JSONObject();
        noteObject.put("title", "Note " + (notesArray.length() + 1));
        noteObject.put("content", newNote);
        notesArray.put(noteObject);
        notesObject.remove("notes");
        notesObject.put("notes", notesArray);
        FileUtils.writeToFile("notes.json", notesObject.toString(1), context);
    }

    public static void saveNote(Context context, String title, String newNote) throws JSONException {
        JSONObject notesObject = new JSONObject(FileUtils.readFromFile("notes.json", context));
        JSONArray notesArray = notesObject.getJSONArray("notes");
        for (int i = 0; i < notesArray.length(); i++) {
            JSONObject note = notesArray.getJSONObject(i);
            if (note.getString("title").equals(title)) {
                note.remove("content");
                note.put("content", newNote);
            }
        }
        FileUtils.writeToFile("notes.json", notesObject.toString(), context);
    }

    public static void makeNoteFile(Context context) {
        System.out.println("Making new notes.json");
        FileUtils.writeToFile("notes.json", "{notes: []}", context);
    }

    public static int getNoteWordCount(Context context) {
        ArrayList<Note> notes = null;
        try {
            notes = getNotes(context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int count = 0;
        for (Note n: notes) {
            count += countWords(n.getContents());
        }
        return count;
    }

    public static int countWords(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }
}
