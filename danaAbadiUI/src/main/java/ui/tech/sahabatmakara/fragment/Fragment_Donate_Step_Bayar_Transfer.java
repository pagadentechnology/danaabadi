package ui.tech.sahabatmakara.fragment;

import ui.tech.sahabatmakara.activity.Activity_Selesai_Transfer;
import ui.tech.sahabatmakara.activity.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Fragment_Donate_Step_Bayar_Transfer extends Fragment{
	private SharedPreferences sp;
	private TextView tv_nama_lengkap, tv_tujuan_donasi, tv_email;
	EditText ed_donatur_no_tlpn;
	Button btn;
	private String cUserId, cNamaDonatur,cEmail, cUrlImg, cTujuanTransfer, cNamaFakultas, cJumlahDonasi,
			cProgramId, cDonasi_NamaRek, cDonasi_NoRek, cTlp;

	public static Fragment_Donate_Step_Bayar_Transfer create(String program_id,String nama_donatur, String jumlah_donasi,
															 String tujuan_transfer, String nama_fakultas,
															 String email){
		Fragment_Donate_Step_Bayar_Transfer fragment = new Fragment_Donate_Step_Bayar_Transfer();
		Bundle b = new Bundle();
		b.putString(ParameterCollections.SH_PROGRAM_ID, program_id);
		b.putString(ParameterCollections.ARGS_DONATUR_NAME, nama_donatur);
		b.putString(ParameterCollections.ARGS_DONATUR_JUMLAH_DONASI, jumlah_donasi);
		b.putString(ParameterCollections.SH_TUJUAN_FAKULTAS, tujuan_transfer);
		b.putString(ParameterCollections.SH_NAMA_FAKULTAS, nama_fakultas);
		b.putString(ParameterCollections.ARGS_EMAIL, email);
		fragment.setArguments(b);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_donasi_transfer, null);

		sp = getActivity().getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
		cUserId = sp.getString(ParameterCollections.SH_USER_ID, "1");
		cUrlImg = sp.getString(ParameterCollections.SH_USER_PHOTO, ParameterCollections.URL_IMG_PROFILE);

		cNamaDonatur = getArguments().getString(ParameterCollections.ARGS_DONATUR_NAME);
		cEmail = getArguments().getString(ParameterCollections.ARGS_EMAIL);
		cTujuanTransfer = getArguments().getString(ParameterCollections.SH_TUJUAN_FAKULTAS);
		cNamaFakultas = getArguments().getString(ParameterCollections.SH_NAMA_FAKULTAS);
		cJumlahDonasi = getArguments().getString(ParameterCollections.ARGS_DONATUR_JUMLAH_DONASI);
		cProgramId = getArguments().getString(ParameterCollections.SH_PROGRAM_ID);
		cEmail= getArguments().getString(ParameterCollections.ARGS_EMAIL);

		TextView label_nama_lengkap = (TextView)v.findViewById(R.id.label_nama_lengkap);
		TextView label_tujuan_donasi = (TextView)v.findViewById(R.id.label_tujuan_donasi);
		TextView label_email = (TextView)v.findViewById(R.id.label_email);
		TextView label_no_tlp = (TextView)v.findViewById(R.id.label_no_tlp);

		label_nama_lengkap.setTypeface(Font.setLato(getActivity()));
		label_tujuan_donasi.setTypeface(Font.setLato(getActivity()));
		label_email.setTypeface(Font.setLato(getActivity()));
		label_no_tlp.setTypeface(Font.setLato(getActivity()));

		tv_nama_lengkap = (TextView)v.findViewById(R.id.tv_nama_lengkap);
		tv_tujuan_donasi = (TextView)v.findViewById(R.id.tv_tujuan_donasi);
		tv_email = (TextView)v.findViewById(R.id.tv_email);
		ed_donatur_no_tlpn = (EditText)v.findViewById(R.id.ed_donatur_no_tlpn);



		tv_nama_lengkap.setTypeface(Font.setLato(getActivity()));
		tv_tujuan_donasi.setTypeface(Font.setLato(getActivity()));
		tv_email.setTypeface(Font.setLato(getActivity()));
		ed_donatur_no_tlpn.setTypeface(Font.setLato(getActivity()));

		btn = (Button)v.findViewById(R.id.btn);

		tv_nama_lengkap.setText(cNamaDonatur);
		tv_tujuan_donasi.setText(cTujuanTransfer);
		tv_email.setText(cEmail);

		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cTlp = ed_donatur_no_tlpn.getText().toString();
				if(!cTlp.equals("")){
					new AsyncTask_Get_MID_and_PROSES().execute();
				}
			}
		});

		return v;
	}

	private class AsyncTask_Get_MID_and_PROSES extends AsyncTask<Void, Void,Void>{
		private String cCode;
		private Dialog_Progress pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new Dialog_Progress();
			pDialog.show(getFragmentManager(),"");
		}

		@Override
		protected Void doInBackground(Void... params) {
			ServiceHandlerJSON sh = new ServiceHandlerJSON();

			try{
				JSONObject jObj = sh.get_VA(cTujuanTransfer);
				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONArray jData = jObj.getJSONArray(ParameterCollections.TAG_DATA);
					cDonasi_NamaRek = jData.getString(0);
					cDonasi_NoRek = jData.getString(1);

					JSONObject json_insertDonasi = sh.insert_donasi_transfer(cProgramId,cUserId,cTujuanTransfer,"Yes",
							cNamaDonatur,cJumlahDonasi, "",cEmail, cTlp, cUrlImg);
				}
			}catch (JSONException e){

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			pDialog.dismiss();

			if (cCode.equals("1")) {
				Intent intent = new Intent(getActivity(), Activity_Selesai_Transfer.class);
				intent.putExtra(ParameterCollections.ARGS_NAMA_REK, cDonasi_NamaRek);
				intent.putExtra(ParameterCollections.ARGS_NO_REK, cDonasi_NoRek);
				startActivity(intent);
				getActivity().finish();
			}else{
				Toast.makeText(getActivity(), "Terjadi Kesalahan pada Donasi", Toast.LENGTH_SHORT).show();
			}

		}
	}



}
