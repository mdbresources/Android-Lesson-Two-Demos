package dev.mdb.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

public class NoteTakingActivity extends AppCompatActivity {

    EditText txtNote;
    Button btnSave;
    boolean newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking_activity);

        // Initialize UI elements using their ID's set in the editor layout.
        txtNote = findViewById(R.id.txtNote);
        btnSave = findViewById(R.id.btnSave);

        // Determine if we should use the existing saved note,
        // or start from scratch.
        newNote = getIntent().getBooleanExtra("new_note", false);
        String noteTitle = null;
        final String noteContent;

        // If this should be a new note, then keep the
        // txtNote field empty, otherwise fill it with
        // the text saved in the current note file.
        if (!newNote) {
            // If there is no existing, then tell this to the user.
            noteTitle = getIntent().getStringExtra("note_title");
            noteContent = getIntent().getStringExtra("note_content");
            txtNote.setText(noteContent);
        }

        // Set OnClickListener Behavior for buttons.
        // Implements the OnClickListener methods in-line.
        final String finalNoteTitle = noteTitle;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the current text in the txtNote field into the note.txt file.
                String newNoteData = txtNote.getText().toString();
                try {
                    if (newNote) {
                        // If this note is a new note, this means no field exists in the json
                        // for this note. Add this note and make this no longer a new note.
                        FileUtils.addNote(NoteTakingActivity.this, newNoteData);
                        newNote = false;
                    } else {
                        // Otherwise, save the note with the same name it already has.
                        FileUtils.saveNote(NoteTakingActivity.this, finalNoteTitle, newNoteData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Show a toast that the file was saved.
                Toast fileSaved = Toast.makeText(getApplicationContext(), "File Saved.", Toast.LENGTH_SHORT);
                fileSaved.show();
            }
        });
    }

}
