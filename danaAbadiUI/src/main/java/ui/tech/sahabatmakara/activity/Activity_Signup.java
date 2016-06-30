package ui.tech.sahabatmakara.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

/**
 * Created by Anoa 34 on 08/10/2015.
 */
public class Activity_Signup extends ActionBarActivity {
    private EditText ed_Username, ed_Password, ed_Email;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        initView();
    }

    private void initView() {
        ed_Username = (EditText) findViewById(R.id.ed_username);
        ed_Password = (EditText) findViewById(R.id.ed_password);
        ed_Email = (EditText) findViewById(R.id.ed_email);

        TextView tv_label_sahabatmakara = (TextView)findViewById(R.id.tv_label_sahabatmakara);
        tv_label_sahabatmakara.setTypeface(Font.setFontGaramond(getApplicationContext()));

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask_Signup().execute();
            }
        });

    }

    private class AsyncTask_Signup extends AsyncTask<Void, Void, Void> {
        private String cCode, cErrorMessage, cUsername, cPassword, cEmail;
        private boolean logged;
        private Dialog_Progress pdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cUsername = ed_Username.getText().toString();
            cPassword = ed_Password.getText().toString();
            cEmail = ed_Email.getText().toString();

            pdialog = new Dialog_Progress();
            pdialog.show(getSupportFragmentManager(),"");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();
            JSONObject json = sh.register(cUsername, cPassword, cEmail);

            try {
                cCode = json.getString(ParameterCollections.TAG_JSON_CODE);
                if (cCode.equals("1")) {
                    logged = true;
                }else{
                    cErrorMessage = json.getString(ParameterCollections.TAG_JSON_ERROR_MESSAGE);
                    logged = false;
                }
            }catch (Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pdialog.dismiss();

            if(logged){
                Toast.makeText(getApplicationContext(), "Akun anda berhasil dibuat", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(getApplicationContext(), cErrorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
