package ui.tech.sahabatmakara.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

/**
 * Created by Anoa 34 on 08/10/2015.
 */
public class Activity_Login extends ActionBarActivity {
    private ImageView img_logo;
    private EditText ed_Username, ed_Password;
    private Button btn_Login, btn_Skip;
    private View btn_Fb, btn_Sso, btn_Signup;
    private SharedPreferences sp;
    private CallbackManager cm;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private LoginResult json_result;

    private String user_id, user_email, user_fullname, user_foto, user_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        sp = getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
        setContentView(R.layout.layout_login);

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    private void initView() {
        TextView tv_label_sahabatmakara = (TextView)findViewById(R.id.tv_label_sahabatmakara);
        tv_label_sahabatmakara.setTypeface(Font.setFontGaramond(getApplicationContext()));

        img_logo = (ImageView) findViewById(R.id.img_logo);
        ed_Username = (EditText) findViewById(R.id.ed_username);
        ed_Password = (EditText) findViewById(R.id.ed_password);

        btn_Login = (Button) findViewById(R.id.btn_login);
        btn_Skip = (Button) findViewById(R.id.btn_skip);

        btn_Fb = (View) findViewById(R.id.btn_fb_connect);
        btn_Sso = (View) findViewById(R.id.btn_sso_connect);

        btn_Signup = (View) findViewById(R.id.wrapper_signup);

        btn_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Activity_MenuSlider.class));
                finish();
            }
        });

        btn_Sso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Coming Soon...", Toast.LENGTH_LONG).show();
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask_Login().execute();
            }
        });

        btn_Fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(Activity_Login.this, Arrays.asList("public_profile", "email"));
            }
        });

        cm = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = AccessToken.getCurrentAccessToken();


            }
        };


        LoginManager.getInstance().registerCallback(cm, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("sukses", loginResult.toString());
                        json_result = loginResult;

                        GraphRequest request = GraphRequest.newMeRequest(json_result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject user, GraphResponse response) {

                                user_id = user.optString("id");
                                user_fullname = user.optString("name");
                                user_email = user.optString("email");
                                user_gender = user.optString("gender");

                                try {
//                            String url_img_profile = "http://graph.facebook.com/"+id+"/picture.type(large)";
//                            ServiceHandlerJSON sh = new ServiceHandlerJSON();

                                    JSONObject obj_pic = user.optJSONObject("picture");
                                    JSONObject obj_data = obj_pic.optJSONObject("data");
                                    user_foto = obj_data.optString("url");

                                    new AsyncTask_SignupFB().execute();
                                } catch (Exception e) {

                                }
                            }
                        });

                        final Bundle b = new Bundle();
                        b.putString("fields", "name,email,gender,picture.type(large)");
                        request.setParameters(b);
                        request.executeAsync();

//                GraphRequest.newMeRequest(json_result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//
//                    @Override
//                    public void onCompleted(JSONObject user, GraphResponse response) {
//                        String id = user.optString("id");
//                        String name = user.optString("name");
//                        String email = user.optString("email");
//
//
//                    }
//                }).executeAsync();

//                new AsyncTask_FB_LOGIN().execute();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("canceled", "");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("error", error.getMessage().toString());
                    }
                }

        );

        btn_Signup.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Signup.class);
                startActivity(intent);
            }
        });
    }

    private class AsyncTask_Login extends AsyncTask<Void, Void, Void> {
        private String cCode, cErrorMessage, cUsername, cPassword;
        private Dialog_Progress pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cUsername = ed_Username.getText().toString();
            cPassword = ed_Password.getText().toString();

            pDialog = new Dialog_Progress();
            pDialog.show(getSupportFragmentManager(), "");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();
            JSONObject json = sh.login(cUsername, cPassword);

            try {
                cCode = json.getString(ParameterCollections.TAG_JSON_CODE);
                if (cCode.equals("1")) {
                    JSONObject c = json.getJSONObject(ParameterCollections.TAG_DATA);

                    String user_id = c.getString(ParameterCollections.TAG_USER_ID);
                    String user_email = c.getString(ParameterCollections.TAG_USER_EMAIL);
                    String user_fullname = c.getString(ParameterCollections.TAG_USER_FULLNAME);
                    String user_foto = c.getString(ParameterCollections.TAG_USER_IMG_URL);

                    sp.edit().putString(ParameterCollections.SH_USER_ID, user_id).commit();
                    sp.edit().putString(ParameterCollections.SH_NAMA_DONATUR, user_fullname).commit();
                    sp.edit().putString(ParameterCollections.SH_EMAIL_DONATUR, user_email).commit();
                    sp.edit().putString(ParameterCollections.SH_USER_PHOTO, user_foto).commit();
                    sp.edit().putBoolean(ParameterCollections.SH_LOGGED, true).commit();

                } else {
                    cErrorMessage = json.getString(ParameterCollections.TAG_JSON_ERROR_MESSAGE);
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

                Intent intent = new Intent(getApplicationContext(), Activity_MenuSlider.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), cErrorMessage, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class AsyncTask_SignupFB extends AsyncTask<Void, Void, Void> {
        private String cCode, cErrorMessage, cUsername, cPassword, cEmail;
        private boolean logged;
        private Dialog_Progress pdialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            cUsername = ed_Username.getText().toString();
//            cPassword = ed_Password.getText().toString();
//            cEmail = ed_Email.getText().toString();

            pdialog = new Dialog_Progress();
            pdialog.show(getSupportFragmentManager(), "");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();
            JSONObject json = sh.registerFB(user_id,user_fullname,user_foto,user_email,user_gender);

            try {
                cCode = json.getString(ParameterCollections.TAG_JSON_CODE);
                if (cCode.equals("1")) {
                    logged = true;

                    //get UserID dari UI
                    JSONObject jobj = json.getJSONObject(ParameterCollections.TAG_DATA);
                    String user_id = jobj.getString(ParameterCollections.TAG_USER_ID);

                    sp.edit().putString(ParameterCollections.SH_USER_ID, user_id).commit();
                    sp.edit().putString(ParameterCollections.SH_NAMA_DONATUR, user_fullname).commit();
                    sp.edit().putString(ParameterCollections.SH_EMAIL_DONATUR, user_email).commit();
                    sp.edit().putString(ParameterCollections.SH_USER_PHOTO, user_foto).commit();
                    sp.edit().putBoolean(ParameterCollections.SH_LOGGED, true).commit();
                } else {
                    cErrorMessage = json.getString(ParameterCollections.TAG_JSON_ERROR_MESSAGE);
                    logged = false;
                }
            } catch (Exception e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pdialog.dismiss();

            if (logged) {
                startActivity(new Intent(getApplicationContext(), Activity_MenuSlider.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), cErrorMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
