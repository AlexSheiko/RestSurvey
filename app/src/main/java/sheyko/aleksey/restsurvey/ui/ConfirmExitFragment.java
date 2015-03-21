package sheyko.aleksey.restsurvey.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseUser;

import sheyko.aleksey.restsurvey.R;

public class ConfirmExitFragment extends DialogFragment {

    public ConfirmExitFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        ((CustomerStartActivity) getActivity()).confirmExit();
                    }
                }
        );
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_confirm_exit, null);

        String username = ParseUser.getCurrentUser()
                .getUsername();

        EditText passwordField = (EditText)
                view.findViewById(R.id.password);
        passwordField.setHint(String.format(
                "%s\'s pin code", username));

        builder.setView(view);
        return builder.create();
    }
}
