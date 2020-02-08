package dev.mdb.notebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class InfoFragment extends Fragment {

    TextView txtWordCount;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_information, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set the word count to the text view.
        txtWordCount = view.findViewById(R.id.txtWordCount);
        int wordCount = FileUtils.getNoteWordCount(view.getContext());
        txtWordCount.setText("Word Count: " + wordCount);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update the word count on coming back to this screen.
        int wordCount = FileUtils.getNoteWordCount(getContext());
        txtWordCount.setText("Word Count: " + wordCount);
    }
}