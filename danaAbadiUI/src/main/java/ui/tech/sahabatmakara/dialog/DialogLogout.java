package ui.tech.sahabatmakara.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DialogLogout extends DialogFragment {
	Context context;

	public DialogLogout(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Yakin Keluar ?");
		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				// Intent load = new Intent(getActivity(),
				// Login_Activity.class);
				// getActivity().startActivity(load);
				getActivity().finish();
			}
		});
		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});

		return builder.create();
	}

}
