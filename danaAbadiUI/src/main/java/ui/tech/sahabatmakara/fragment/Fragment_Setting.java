package ui.tech.sahabatmakara.fragment;

import ui.tech.sahabatmakara.activity.Activity_Setting_Account_Edit;
import ui.tech.sahabatmakara.activity.Activity_Setting_Account_View;
import ui.tech.sahabatmakara.activity.Activity_TentangKami;
import ui.tech.sahabatmakara.activity.R;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class Fragment_Setting extends Fragment {
    View wrapper_Password, wrapper_Form;
    EditText ed_Username, ed_Password;
    Button btn, btn_Edit_Profile;
    SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.activity_setting, null);

        ed_Username = (EditText)v.findViewById(R.id.ed_username);
        ed_Password = (EditText)v.findViewById(R.id.ed_password);

        View wrapper_Account = (View) v.findViewById(R.id.wrapper_setting_account);
        wrapper_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Activity_Setting_Account_View.class));
            }
        });


        wrapper_Password = (View) v.findViewById(R.id.wrapper_password);
        wrapper_Form = (View) v.findViewById(R.id.wrapper_form_password);
        btn = (Button) v.findViewById(R.id.btn);
        btn_Edit_Profile = (Button) v.findViewById(R.id.btn_edit_profile);

        wrapper_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wrapper_Form.setVisibility(View.VISIBLE);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask_GantiPassword().execute();
            }
        });
        btn_Edit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Activity_Setting_Account_Edit.class));
            }
        });

        TextView tv_TentangKami = (TextView)v.findViewById(R.id.tv_tentangkami);
        tv_TentangKami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Activity_TentangKami.class));
            }
        });

        return v;
    }

    private class AsyncTask_GantiPassword extends AsyncTask<Void, Void, Void> {
        private String cUserId, cUsername, cPassword, cCode;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cUserId = sp.getString(ParameterCollections.SH_USER_ID, "1");
            cUsername = ed_Username.getText().toString();
            cPassword = ed_Password.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ServiceHandlerJSON sh = new ServiceHandlerJSON();
            JSONObject json_obj = sh.editPassword(cUserId, cUsername, cPassword);

            try {
                cCode = json_obj.getString(ParameterCollections.TAG_JSON_CODE);

            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(cCode.equals("1")){
                Toast.makeText(getActivity(), "Berhasil ubah Password", Toast.LENGTH_LONG).show();
                wrapper_Form.setVisibility(View.GONE);
            }else{
                Toast.makeText(getActivity(), "Gagal ubah Password", Toast.LENGTH_LONG).show();
            }
        }
    }
}
