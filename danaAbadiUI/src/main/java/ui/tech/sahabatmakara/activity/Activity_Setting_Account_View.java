package ui.tech.sahabatmakara.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;
import jp.wasabeef.blurry.Blurry;

public class Activity_Setting_Account_View extends ActionBarActivity {
    private BootstrapCircleThumbnail img_Profile;
    private EditText ed_NamaLengkap, ed_Email, ed_Alamat, ed_Fakultas, ed_Tlp;
    private Spinner spinner_Kelamin, spinner_Fakultas;
    private TextView tv_Fakultas;
    private SharedPreferences sp;
    private String idUser,userGender, userFakultas;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting_account);
        setContentView(R.layout.layout_view_account);
        initView();
        sp = getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
        idUser = sp.getString(ParameterCollections.SH_USER_ID, "1");
        new AsyncTask_GetUserData().execute();
    }

    private void initView() {

        img_Profile = (BootstrapCircleThumbnail) findViewById(R.id.img_profile);

        ed_NamaLengkap = (EditText) findViewById(R.id.ed_nama_lengkap);
        ed_Email = (EditText) findViewById(R.id.ed_email);
        ed_Alamat = (EditText) findViewById(R.id.ed_alamat);
        ed_Tlp = (EditText) findViewById(R.id.ed_tlp);

        spinner_Kelamin = (Spinner) findViewById(R.id.spinner_kelamin);
        spinner_Fakultas = (Spinner) findViewById(R.id.spinner_fakultas);

        spinner_Fakultas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userFakultas = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_Kelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    userGender = "male";
                } else {
                    userGender = "female";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tv_Fakultas = (TextView) findViewById(R.id.tv_fakultas_selected);

        ed_NamaLengkap.setEnabled(false);
        ed_Email.setEnabled(false);
        ed_Alamat.setEnabled(false);
        ed_Tlp.setEnabled(false);
        spinner_Kelamin.setEnabled(false);
        spinner_Fakultas.setEnabled(false);

        img = (ImageView) findViewById(R.id.img_bg);
        ImageView img_Back = (ImageView) findViewById(R.id.btn_actionbar_back);
        img_Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });


    }

    private class AsyncTask_GetUserData extends AsyncTask<Void, Void, Void> {
        String cCode, userId, userEmail, userAlamat, userFullName, userGender, url_img, userFakultas, userTlp;
        private Dialog_Progress pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog_Progress();
            pDialog.show(getSupportFragmentManager(),"");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();
            JSONObject jObj = sh.getAccountUserDetail(idUser);

            try {
                Thread.sleep(1000);

                cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

                if (cCode.equals("1")) {
                    JSONObject json_Data = jObj.getJSONObject(ParameterCollections.TAG_DATA);

                    userId = json_Data.getString(ParameterCollections.TAG_USER_ID);
                    userEmail = json_Data.getString(ParameterCollections.TAG_USER_EMAIL);
                    userAlamat = json_Data.getString(ParameterCollections.TAG_USER_ALAMAT);
                    userFullName = json_Data.getString(ParameterCollections.TAG_USER_FULLNAME);
                    userGender = json_Data.getString(ParameterCollections.TAG_USER_KELAMIN);
                    url_img = json_Data.getString(ParameterCollections.TAG_USER_IMG_URL);
                    userFakultas = json_Data.getString(ParameterCollections.TAG_USER_FAKULTAS);
                    userTlp = json_Data.getString(ParameterCollections.TAG_USER_TELEPON);
                } else {

                }
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();

            if (cCode.equals("1")) {
                ed_NamaLengkap.setText(userFullName);
                ed_Email.setText(userEmail);
                ed_Alamat.setText(userAlamat);
                ed_Tlp.setText(userTlp);

                spinner_Fakultas.setVisibility(View.GONE);
                tv_Fakultas.setVisibility(View.VISIBLE);
                tv_Fakultas.setText(userFakultas);

                if (userGender.contains("fe")) {
                    spinner_Kelamin.setSelection(1);
                } else {
                    spinner_Kelamin.setSelection(0);
                }


                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                        img_Profile.setImage(bitmap);

                        Blurry.with(getApplicationContext()).capture(img_Profile).into(img);
                    }

                    @Override
                    public void onBitmapFailed() {
                        img_Profile.setImage(R.drawable.ic_user);
                    }
                };

                if(url_img.equals("")){
                    Picasso.with(getApplicationContext()).load(ParameterCollections.URL_IMG_PROFILE).into(target);
                }else{
                    Picasso.with(getApplicationContext()).load(url_img).into(target);
                }

            }

        }
    }


}
