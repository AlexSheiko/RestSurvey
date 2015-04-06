package sheyko.aleksey.restsurvey.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

import sheyko.aleksey.restsurvey.R;

public class ConfirmExitFragment extends DialogFragment {

    public ConfirmExitFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_confirm_exit, null);

        final ParseUser user = ParseUser.getCurrentUser();

        final EditText passwordField = (EditText)
                view.findViewById(R.id.password);
        passwordField.setHint(String.format(
                "%s\'s pin code", user.getUsername()));

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (passwordField.getText().toString().equals(user.getString("pin"))) {
                            ((CustomerStartActivity) getActivity()).confirmExit();
                        } else {
                            Toast.makeText(getActivity(), "Pin code doesn't match",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        builder.setView(view);
        return builder.create();
    }
}
