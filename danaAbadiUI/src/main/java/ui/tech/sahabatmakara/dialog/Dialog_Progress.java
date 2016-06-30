package ui.tech.sahabatmakara.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Anoa 34 on 12/10/2015.
 */
public class Dialog_Progress extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ProgressDialog pg = new ProgressDialog(getActivity());
        pg.setMessage("Loading");
        pg.setCancelable(false);
        return pg;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
