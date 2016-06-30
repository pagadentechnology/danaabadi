package ui.tech.sahabatmakara.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import ui.tech.sahabatmakara.activity.Activity_Selesai_JemputDana;
import ui.tech.sahabatmakara.activity.R;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

/**
 * Created by RebelCreative-A1 on 19/10/2015.
 */
public class Fragment_JemputDana_Step2 extends Fragment {
    private String cNamaDonatur, cEmail, cNoHp, cNamaProgram,cNamaFakultas,cJumlahDonasi, cDesc, cProgramId, cUserId, cUrlImg;
    private EditText ed_NoHp, ed_Desc;
    private SharedPreferences sp;
    private Button btn;
    private ImageView btn_Back;

    public static Fragment_JemputDana_Step2 create(String cNamaDonatur, String cEmail,
                                                   String cNamaProgram, String cNamaFakultas, String  cJumlahDonasi,
                                                   String cProgramId){
        Fragment_JemputDana_Step2 fragment = new Fragment_JemputDana_Step2();
        Bundle b = new Bundle();
        b.putString(ParameterCollections.SH_NAMA_DONATUR, cNamaDonatur);
        b.putString(ParameterCollections.SH_EMAIL_DONATUR, cEmail);
        b.putString(ParameterCollections.SH_NAMA_PROGRAM, cNamaProgram);
        b.putString(ParameterCollections.SH_NAMA_FAKULTAS, cNamaFakultas);
        b.putString(ParameterCollections.SH_JUMLAH_DONASI, cJumlahDonasi);
        b.putString(ParameterCollections.SH_PROGRAM_ID, cProgramId);
        fragment.setArguments(b);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_step_bayar_jemputdana, null);

        sp = getActivity().getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
        cUserId = sp.getString(ParameterCollections.SH_USER_ID, "1");
        cUrlImg = sp.getString(ParameterCollections.SH_USER_PHOTO, ParameterCollections.URL_IMG_PROFILE);

        ed_NoHp = (EditText)v.findViewById(R.id.ed_no_tlp);
        ed_Desc = (EditText)v.findViewById(R.id.ed_donatur_desc);

        btn = (Button)v.findViewById(R.id.btn);

        cNamaDonatur = getArguments().getString(ParameterCollections.SH_NAMA_DONATUR);
        cEmail =  getArguments().getString(ParameterCollections.SH_EMAIL_DONATUR);
        cNamaProgram =  getArguments().getString(ParameterCollections.SH_NAMA_PROGRAM);
        cNamaFakultas =  getArguments().getString(ParameterCollections.SH_NAMA_FAKULTAS);
        cJumlahDonasi =  getArguments().getString(ParameterCollections.SH_JUMLAH_DONASI);
        cProgramId =  getArguments().getString(ParameterCollections.SH_PROGRAM_ID);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask_SendData().execute();
            }
        });

        return v;
    }

    private class AsyncTask_SendData extends AsyncTask<Void,Void,Void> {
        private Dialog_Progress pDialog;
        private String cCode;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog_Progress();
            pDialog.show(getFragmentManager(), "");

            cNoHp = ed_NoHp.getText().toString();
            cDesc = ed_Desc.getText().toString();

        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();
            JSONObject jObj = sh.insert_jemputdana(cProgramId,cUserId,cNamaFakultas,"",cNamaDonatur,cJumlahDonasi,cDesc,cEmail,cNoHp,cUrlImg);

            try {
                cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);


            }catch (Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();

            if(cCode.equals("1")){
//                Toast.makeText(getApplicationContext(), "Kami akan segera menghubungi anda. Terima Kasih.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity(), Activity_Selesai_JemputDana.class));
                getActivity().finish();
            }else{
                Toast.makeText(getActivity(), "Maaf terjadi kesalahan", Toast.LENGTH_LONG).show();
            }
        }
    }
}
