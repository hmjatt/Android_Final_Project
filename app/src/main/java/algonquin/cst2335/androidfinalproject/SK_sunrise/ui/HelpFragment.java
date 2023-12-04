package algonquin.cst2335.androidfinalproject.SK_sunrise.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.androidfinalproject.R;

public class HelpFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sk_fragment_help, container, false);

        // Set up help information
        view.findViewById(R.id.btnShowHelp).setOnClickListener(v -> showHelpDialog());

        return view;
    }

    private void showHelpDialog() {
        // Implement the logic to show a dialog with instructions for using the interface
        new AlertDialog.Builder(requireContext())
                .setTitle("Help")
                .setMessage("This is where you put your help information.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Implement other methods and help information setup here...
}
