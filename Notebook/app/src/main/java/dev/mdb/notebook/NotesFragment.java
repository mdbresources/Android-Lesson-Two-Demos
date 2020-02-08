package dev.mdb.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    RecyclerView recycleNotes;
    NoteAdapter notesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_notes, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Initialize the recycler view.
        recycleNotes = view.findViewById(R.id.recycleNotes);
        // Initialize the recycler view adapter. If this causes a JSONException, then there
        // either no json file stored right now, or it is malformed-formed. Either way, it will
        // create a brand new .json file.
        try {
            notesAdapter = new NoteAdapter(FileUtils.getNotes(getContext()));
        } catch (JSONException e) {
            FileUtils.makeNoteFile(getContext());
            notesAdapter = new NoteAdapter(new ArrayList<Note>());
        }
        // Set the adapter for the recycler view.
        recycleNotes.setAdapter(notesAdapter);
        // Set the layout of the recycler manager. This will determine how the
        // rows will be displayed. LinearLayout will set them to be vertically
        // linear (i.e one after the other, on top of each other).
        recycleNotes.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        // When the user comes back to this activity, this onResume function will be called.
        // The notes adapter will be updated with any new data. This will update our recycler view.
        try {
            notesAdapter.setData(FileUtils.getNotes(getContext()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}