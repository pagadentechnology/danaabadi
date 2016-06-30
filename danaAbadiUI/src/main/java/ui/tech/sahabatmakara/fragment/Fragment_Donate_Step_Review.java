package ui.tech.sahabatmakara.fragment;

import ui.tech.sahabatmakara.activity.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;

public class Fragment_Donate_Step_Review extends Fragment {
	Spinner spinner_Payment;
	TextView label_TotalDonasi, label_NamaDonatur, label_PembayaranVia;
	
	TextView tv_TotalDonasi, tv_NamaDonatur;
	String cTotalDonasi, cNamaDonator;
	SharedPreferences sp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getActivity().getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
	}

	public static Fragment_Donate_Step_Review create(String nama_donatur, String jumlah_donasi) {
		Fragment_Donate_Step_Review fragment = new Fragment_Donate_Step_Review();
		Bundle b = new Bundle();
		b.putString(ParameterCollections.ARGS_DONATUR_NAME, nama_donatur);
		b.putString(ParameterCollections.ARGS_DONATUR_JUMLAH_DONASI, jumlah_donasi);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_donate_step_review, null);

		tv_TotalDonasi = (TextView) v.findViewById(R.id.tv_donate_total);
		tv_NamaDonatur = (TextView) v.findViewById(R.id.tv_donate_atasnama);
		
		label_TotalDonasi = (TextView) v.findViewById(R.id.label_total_donasi);
		label_NamaDonatur = (TextView) v.findViewById(R.id.label_donate_atasnama);
		label_PembayaranVia = (TextView) v.findViewById(R.id.label_donate_spinner);
		
		tv_TotalDonasi.setTypeface(Font.setFontGaramond(getActivity()));
		tv_NamaDonatur.setTypeface(Font.setFontGaramond(getActivity()));
		label_TotalDonasi.setTypeface(Font.setFontGaramond(getActivity()));
		label_NamaDonatur.setTypeface(Font.setFontGaramond(getActivity()));
		label_PembayaranVia.setTypeface(Font.setFontGaramond(getActivity()));
		

//		cTotalDonasi = getArguments().getString(ParameterCollections.ARGS_DONATUR_JUMLAH_DONASI);
//		cNamaDonator = getArguments().getString(ParameterCollections.ARGS_DONATUR_NAME);
		cTotalDonasi = sp.getString(ParameterCollections.SH_JUMLAH_DONASI, "Rp 000.000");
		cNamaDonator = sp.getString(ParameterCollections.SH_NAMA_DONATUR, "");
		
		tv_TotalDonasi.setText("Rp. " + cTotalDonasi);
		tv_NamaDonatur.setText(cNamaDonator);

		spinner_Payment = (Spinner) v.findViewById(R.id.spinner_payment);

		Button btn_Next = (Button) v.findViewById(R.id.btn_next);
		btn_Next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (spinner_Payment.getSelectedItemPosition()) {
				case 0:
//					getFragmentManager().beginTransaction()
//							.replace(R.id.frame_container,
//									new Fragment_Donate_Step_Bayar_Transfer().create(cNamaDonator, cTotalDonasi))
//							.addToBackStack("").commit();
					break;
				case 1:
					getFragmentManager().beginTransaction()
							.replace(R.id.frame_container,
									new Fragment_Donate_Step_Bayar_KartuKredit().create(cNamaDonator, cTotalDonasi))
							.addToBackStack("").commit();
					break;


				default:
					break;
				}

			}
		});

		return v;
	}

}
