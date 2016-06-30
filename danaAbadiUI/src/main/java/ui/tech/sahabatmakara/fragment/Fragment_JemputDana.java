package ui.tech.sahabatmakara.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import ui.tech.sahabatmakara.activity.R;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.entities.RowData_Program;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

/**
 * Created by RebelCreative-A1 on 13/10/2015.
 */
public class Fragment_JemputDana extends Fragment {
    private ArrayList<RowData_Program> objects;
    private Spinner spinner_Fakultas, spinner_Program, spinner_Donasi;
    private String cNamaDonatur, cEmail, cNamaProgram, cNamaFakultas, cMetode, cJumlahDonasi, cProgramId;
    private EditText ed_NamaDonatur, ed_Email, ed_NominalOthers;
    View wrapper_jmh_donasi;
    private Button btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new AsyncTask_GetProgram().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_jemputdana_step_info, null);

        ed_NamaDonatur = (EditText)v.findViewById(R.id.ed_donatur_nama);
        ed_Email = (EditText)v.findViewById(R.id.ed_donatur_email);

        wrapper_jmh_donasi = (View) v.findViewById(R.id.wrapper_jmhdonasi_other);
        ed_NominalOthers = (EditText)v.findViewById(R.id.ed_jmhdonasi_other);

        spinner_Fakultas = (Spinner)v.findViewById(R.id.spinner_fakultas);
        spinner_Program = (Spinner)v.findViewById(R.id.spinner_program);
        spinner_Donasi  = (Spinner)v.findViewById(R.id.spinner_donasi);

        spinner_Fakultas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cNamaFakultas = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_Donasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        cJumlahDonasi = "100000";
                        break;
                    case 1:
                        cJumlahDonasi = "1000000";
                        break;
                    case 2:
                        cJumlahDonasi = "2000000";
                        break;
                    case 3:
                        cJumlahDonasi = "5000000";
                        break;
                    case 4:
                        cJumlahDonasi = "10000000";
                        break;
                    case 5:
                        wrapper_jmh_donasi.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn = (Button)v.findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cNamaDonatur = ed_NamaDonatur.getText().toString();
                cEmail = ed_Email.getText().toString();


                if(wrapper_jmh_donasi.getVisibility() == View.VISIBLE){
                    cJumlahDonasi = ed_NominalOthers.getText().toString();
                }

//                Intent intent = new Intent(getActivity(), Activity_JemputData_Step2.class);
//                intent.putExtra("cNamaDonatur",cNamaDonatur);
//                intent.putExtra("cEmail",cEmail);
//                intent.putExtra("cProgramId",cProgramId);
//                intent.putExtra("cNamaProgram",cNamaProgram);
//                intent.putExtra("cNamaFakultas",cNamaFakultas);
//                intent.putExtra("cJumlahDonasi",cJumlahDonasi);
//
//                startActivity(intent);
//                getActivity().finish();

                getFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new Fragment_JemputDana_Step2().create(cNamaDonatur, cEmail, cNamaProgram,
                                cNamaFakultas, cJumlahDonasi, ""))
                                .addToBackStack("").commit();
            }
        });

        return v;
    }

    private class AsyncTask_GetProgram extends AsyncTask<Void, Void, Void>{
        String cCode;
        private Dialog_Progress pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            objects = new ArrayList<RowData_Program>();

            pDialog = new Dialog_Progress();
            pDialog.show(getFragmentManager(),"");

        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();
            try {
                JSONObject jObj = sh.getAllProgram();

                cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

                if (cCode.equals("1")) {
                    JSONArray jArray = jObj.getJSONArray(ParameterCollections.TAG_DATA);

                    for(int i=0; i< jArray.length(); i++){
                        JSONObject c = jArray.getJSONObject(i);
                        String id = c.getString(ParameterCollections.TAG_PROGRAM_ID);
                        String title = c.getString(ParameterCollections.TAG_PROGRAM_TITLE);
                        String goal = c.getString(ParameterCollections.TAG_PROGRAM_GOAL);
                        String raised = c.getString(ParameterCollections.TAG_PROGRAM_RAISED);
                        String donatur = c.getString(ParameterCollections.TAG_PROGRAM_DONATOR);
                        String desc = c.getString(ParameterCollections.TAG_PROGRAM_DESC);
                        desc = Jsoup.parse(desc).text();

                        String url = "";
                        JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);

                        if (jArray_Images.length() >= 0) {
                            for (int j = 0; j < jArray_Images.length(); j++) {
                                JSONObject d = jArray_Images.getJSONObject(j);
                                url = ParameterCollections.URL_IMG_ORIGINAL
                                        + d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

                            }
                            objects.add(new RowData_Program(id, title, raised, goal, "10", desc, url));

                        }else{
                            objects.add(new RowData_Program(id, title, raised, goal, "10", desc, "http://128.199.176.5/UI/admin/files/original/55e6c977b6d8d.jpg"));

                        }

                    }
                }

                // } catch (JSONException e) {

            } catch (Exception e) {
                // TODO: handle exception
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();

            List<String> data_spinner_program = new ArrayList<String>();
            for(int i=0; i < objects.size(); i++){
                data_spinner_program.add(objects.get(i).program_title);
            }

            ArrayAdapter<String> adapter_spinnner = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data_spinner_program);
            adapter_spinnner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_Program.setAdapter(adapter_spinnner);

            spinner_Program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cNamaProgram = parent.getSelectedItem().toString();
                    cProgramId = objects.get(position).id;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        }
    }
}
