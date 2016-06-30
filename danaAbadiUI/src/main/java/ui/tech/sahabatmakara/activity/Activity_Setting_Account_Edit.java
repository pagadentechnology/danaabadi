package ui.tech.sahabatmakara.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;

import jp.wasabeef.blurry.Blurry;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by RebelCreative-A1 on 21/10/2015.
 */
public class Activity_Setting_Account_Edit extends ActionBarActivity {
    private BootstrapCircleThumbnail img_Profile;
    private EditText ed_NamaLengkap, ed_Email, ed_Alamat, ed_Fakultas, ed_Tlp;
    private Spinner spinner_Kelamin, spinner_Fakultas;
    private Button btn_Save;
    private View btn_Edit;
    private TextView tv_Fakultas;
    private SharedPreferences sp;
    private String idUser, userGender, userFakultas;
    ImageView img;
    public static int CODE_UPLOAD = 111;
    private String mUrl_Img_00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_setting_account);
        setContentView(R.layout.layout_edit_account);
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

        btn_Edit = (View) findViewById(R.id.btn_setting);
        btn_Save = (Button) findViewById(R.id.btn_save);

        img = (ImageView) findViewById(R.id.img_bg);
        ImageView img_Back = (ImageView) findViewById(R.id.btn_actionbar_back);
        img_Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });


        img_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UploadImageDialog.class);
                startActivityForResult(intent, CODE_UPLOAD);
            }
        });

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadImageDialog.class);
                startActivityForResult(intent, CODE_UPLOAD);
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask_SaveData().execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CODE_UPLOAD) {
                mUrl_Img_00 = data.getStringExtra("mUrl_Img");

                new Async_UpdatePhoto().execute();
            }
        }
    }

    private class AsyncTask_GetUserData extends AsyncTask<Void, Void, Void> {
        String cCode, userId, userEmail, userAlamat, userFullName, userGender, url_img, userFakultas, userTlp;
        private Dialog_Progress pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog_Progress();
            pDialog.show(getSupportFragmentManager(), "");
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

                spinner_Fakultas.setVisibility(View.VISIBLE);
                tv_Fakultas.setVisibility(View.GONE);
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

                if (url_img.equals("")) {
                    Picasso.with(getApplicationContext()).load(ParameterCollections.URL_IMG_PROFILE).into(target);
                } else {
                    Picasso.with(getApplicationContext()).load(url_img).into(target);
                }

            }

        }
    }

    private class AsyncTask_SaveData extends AsyncTask<Void, Void, Void> {
        String cCode, userId, userEmail, userAlamat, userFullName, url_img, userTlp;
        private Dialog_Progress pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog_Progress();
            pDialog.show(getSupportFragmentManager(), "");
            userFullName = ed_NamaLengkap.getText().toString();
            userTlp = ed_Tlp.getText().toString();
            userEmail = ed_Email.getText().toString();
            userAlamat = ed_Alamat.getText().toString();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();

            JSONObject json_obj = sh.editProfile(idUser, userFullName, userTlp, userEmail, userAlamat, userGender, userFakultas);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            finish();
        }
    }

    private class Async_UpdatePhoto extends AsyncTask<Void, Void, String> {
        Dialog_Progress pDialog;
        String respondMessage;
        JSONObject jsonResult;
        int serverRespondCode = 0;
        String url_file;
        File sourceFile;
        FileInputStream fileInputStream;
        private String row_count="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new Dialog_Progress();
            pDialog.show(getSupportFragmentManager(), "");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadDataForm(mUrl_Img_00);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            row_count = result;

            if(row_count.equals("200")){
                startActivity(new Intent(getApplicationContext(), Activity_Selesai_UploadFoto.class));
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Upload Photo Gagal", Toast.LENGTH_SHORT).show();
            }
        }

        private String uploadDataForm(String url_gambar) {
            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;


            if (url_gambar != null) {
                url_file = url_gambar;
                sourceFile = new File(url_file);
            }

            try {
                DefaultHttpClient hClient = new DefaultHttpClient();
                FileBody localFileBody = new FileBody(sourceFile, "img/jpg");
                HttpPost localHttpPost = new HttpPost(ParameterCollections.URL_UPDATE_PHOTO);
                MultipartEntity localMultiPartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                localMultiPartEntity.addPart("user_id", new StringBody(idUser));
                localMultiPartEntity.addPart("photo-file", localFileBody);
                localHttpPost.setEntity(localMultiPartEntity);

                HttpResponse localHttpResponse = hClient.execute(localHttpPost);
                serverRespondCode = localHttpResponse.getStatusLine().getStatusCode();

                if (serverRespondCode == 200) {
                    Log.e("CODE ", "Success Upload");
                } else {
                    Log.e("CODE ", "Success failed");
                }

//                FileBo
//            } catch (MalformedURLException ex) {
//                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return String.valueOf(serverRespondCode);
        }
    }
}
