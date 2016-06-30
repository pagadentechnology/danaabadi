package ui.tech.sahabatmakara.fragment;

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

import ui.tech.sahabatmakara.activity.Activity_Selesai_Transfer;
import ui.tech.sahabatmakara.activity.R;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

/**
 * Created by RebelCreative-A1 on 20/10/2015.
 */
public class Fragment_Program_Step_Bayar_Transfer extends Fragment {
    private String cNamaDonatur, cEmail, cNamaProgram,cNamaFakultas,cJumlahDonasi, cDesc, cProgramId, cUserId, cUrlImg,
            cTlp;
    private EditText ed_NoHp;
    private SharedPreferences sp;
    private Button btn;
    private ImageView btn_Back;
    private TextView tv_nama_lengkap, tv_nama_program, tv_email;

    private String cDonasi_NamaRek, cDonasi_NoRek;

    public static Fragment_Program_Step_Bayar_Transfer create(String nama_donatur, String jumlah_donasi,
                                                             String nama_program, String nama_fakultas,
                                                             String email){
        Fragment_Program_Step_Bayar_Transfer fragment = new Fragment_Program_Step_Bayar_Transfer();
        Bundle b = new Bundle();
        b.putString(ParameterCollections.ARGS_DONATUR_NAME, nama_donatur);
        b.putString(ParameterCollections.ARGS_DONATUR_JUMLAH_DONASI, jumlah_donasi);
        b.putString(ParameterCollections.SH_NAMA_PROGRAM, nama_program);
        b.putString(ParameterCollections.SH_NAMA_FAKULTAS, nama_fakultas);
        b.putString(ParameterCollections.ARGS_EMAIL, email);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.layout_program_transfer, null);

        sp = getActivity().getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
        cUserId = sp.getString(ParameterCollections.SH_USER_ID, "1");
        cUrlImg = sp.getString(ParameterCollections.SH_USER_PHOTO, ParameterCollections.URL_IMG_PROFILE);
        cProgramId = sp.getString(ParameterCollections.SH_PROGRAM_ID, "1");

        cNamaDonatur = getArguments().getString(ParameterCollections.ARGS_DONATUR_NAME);
        cEmail = getArguments().getString(ParameterCollections.ARGS_EMAIL);
        cNamaProgram = getArguments().getString(ParameterCollections.SH_NAMA_PROGRAM);
        cNamaFakultas = getArguments().getString(ParameterCollections.SH_NAMA_FAKULTAS);
        cJumlahDonasi = getArguments().getString(ParameterCollections.ARGS_DONATUR_JUMLAH_DONASI);
        cEmail= getArguments().getString(ParameterCollections.ARGS_EMAIL);

        TextView label_nama_lengkap = (TextView)v.findViewById(R.id.label_nama_lengkap);
        TextView label_nama_program = (TextView)v.findViewById(R.id.label_nama_program);
        TextView label_email = (TextView)v.findViewById(R.id.label_email);
        TextView label_no_tlp = (TextView)v.findViewById(R.id.label_no_tlp);

        label_nama_lengkap.setTypeface(Font.setLato(getActivity()));
        label_nama_program.setTypeface(Font.setLato(getActivity()));
        label_email.setTypeface(Font.setLato(getActivity()));
        label_no_tlp.setTypeface(Font.setLato(getActivity()));

        tv_nama_lengkap = (TextView)v.findViewById(R.id.tv_nama_lengkap);
        tv_nama_program = (TextView)v.findViewById(R.id.tv_nama_program);
        tv_email = (TextView)v.findViewById(R.id.tv_email);
        ed_NoHp = (EditText)v.findViewById(R.id.ed_donatur_no_tlpn);

        tv_nama_lengkap.setTypeface(Font.setLato(getActivity()));
        tv_nama_program.setTypeface(Font.setLato(getActivity()));
        tv_email.setTypeface(Font.setLato(getActivity()));
        ed_NoHp.setTypeface(Font.setLato(getActivity()));

        tv_nama_lengkap.setText(cNamaDonatur);
        tv_nama_program.setText(cNamaProgram);
        tv_email.setText(cEmail);

        btn = (Button)v.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cTlp = ed_NoHp.getText().toString();
                if (!cTlp.equals("")) {
                    new AsyncTask_Get_MID_and_PROSES().execute();
                }
            }
        });

        return v;
    }

    private class AsyncTask_Get_MID_and_PROSES extends AsyncTask<Void, Void,Void> {
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
                JSONObject jObj = sh.get_VA(cNamaFakultas);
                cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

                if (cCode.equals("1")) {
                    JSONArray jData = jObj.getJSONArray(ParameterCollections.TAG_DATA);
                    cDonasi_NamaRek = jData.getString(0);
                    cDonasi_NoRek = jData.getString(1);

                    JSONObject json_insertDonasi = sh.insert_donasi_transfer(cProgramId,cUserId,cNamaProgram,"Yes",
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
