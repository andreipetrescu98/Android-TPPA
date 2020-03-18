package tppa.lab2.onlineshop;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class SignInDialog extends AppCompatDialogFragment {

    public static final String FILE_NAME = "credentials.txt";

    private EditText editTextUsername;
    private EditText editTextPassword;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_signin, null);
        builder.setView(view)
                .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();

                        // Save to internal storage
                        saveUsernameToFile(username);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SignInDialog.this.getDialog().cancel();
                    }
                });

        this.editTextUsername = view.findViewById(R.id.edit_username);
        this.editTextPassword = view.findViewById(R.id.edit_password);

        return builder.create();
    }

    private void saveUsernameToFile(String username) {
        FileOutputStream fos = null;

        try {
            fos = getContext().openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(username.getBytes());
            Toast.makeText(getContext(), "Username saved successfully!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Saving username failed!", Toast.LENGTH_LONG).show();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
