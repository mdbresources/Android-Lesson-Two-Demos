package dev.mdb.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    Button btnNewNote;
    Button btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the new note button.
        btnNewNote = findViewById(R.id.btnNewNote);
        // Set the OnClickListener for the new note button.
        btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new intent for NoteTakingActivity.
                Intent newNoteIntent = new Intent(MainActivity.this, NoteTakingActivity.class);
                // Tell the intent that this should be a brand new.
                newNoteIntent.putExtra("new_note", true);
                // Start the new note activity.
                startActivity(newNoteIntent);
            }
        });

        //Initialized the info button.
        btnInfo = findViewById(R.id.btnInfoTab);
        // Set the OnClickListener for the info button.
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnInfo.getText().equals("Info")) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    ft.add(R.id.mainFragment, new InfoFragment());
                    // Complete the changes added above
                    ft.commit();
                    btnInfo.setText("Notes");
                } else {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    // Replace the contents of the container with the new fragment
                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    }
                    ft.add(R.id.mainFragment, new NotesFragment());
                    // Complete the changes added above
                    ft.commit();
                    btnInfo.setText("Info");
                }
            }
        });

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.add(R.id.mainFragment, new NotesFragment());
        // Complete the changes added above
        ft.commit();
    }
}
