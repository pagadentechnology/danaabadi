package ui.tech.sahabatmakara.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.ParameterCollections;

/**
 * Created by RebelCreative-A1 on 22/10/2015.
 */
public class Activity_Webview extends ActionBarActivity {
    private WebView webview;
    private String url, cToken, cBank, cTotalDonasi;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);

        sp = getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
        url = getIntent().getStringExtra("url");
        cToken = getIntent().getStringExtra("token");
        cBank = getIntent().getStringExtra("bank");
        cTotalDonasi = getIntent().getStringExtra("total");

        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });
        webview.setWebViewClient(new VtWebViewClient(cToken, cTotalDonasi));
        webview.loadUrl(url);
    }


    private class VtWebViewClient extends WebViewClient {

        String token;
        String price;

        public VtWebViewClient(String token, String price) {
            this.token = token;
            this.price = price;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("VtLog", url);

            if (url.startsWith(ParameterCollections.getPaymentApiUrl() + "/callback/")) {
                new AsyncTask_SendToken().execute();
            } else if (url.startsWith(ParameterCollections.getPaymentApiUrl() + "/redirect/") || url.contains("3dsecure")) {
                /* Do nothing */
            }
        }
    }

    private class AsyncTask_SendToken extends AsyncTask<Void, Void, Void> {
        private String cCode, cCode_Insert, cCode_Message, cCode_Charge = "";
        private boolean success;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Donasi anda sedang diproses...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ParameterCollections.URL_SEND_TOKEN);

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            try {
//                nameValuePairs.add(new BasicNameValuePair("token-id", cToken));
//                nameValuePairs.add(new BasicNameValuePair("price", cTotalDonasi));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.KIND_TOKEN,
                        cToken));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.KIND_BANK,
                        cBank));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PROGRAM_ID,
                        sp.getString(ParameterCollections.SH_PROGRAM_ID, "")));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_USER_ID,
                        sp.getString(ParameterCollections.SH_USER_ID, "0")));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_FACULTY,
                        sp.getString(ParameterCollections.SH_NAMA_FAKULTAS, "")));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PUBLISHED,
                        sp.getString(ParameterCollections.SH_PUBLISHED, "Yes")));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_NAME,
                        sp.getString(ParameterCollections.SH_NAMA_DONATUR, "")));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_AMOUNT,
                        cTotalDonasi));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_DESC,
                        sp.getString(ParameterCollections.SH_DONASI_DESC, "")));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_EMAIL,
                        sp.getString(ParameterCollections.SH_EMAIL_DONATUR, "")));
                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PHONE,
                        sp.getString(ParameterCollections.SH_PHONE_DONATUR, "")));

                if (sp.getBoolean(ParameterCollections.SH_LOGGED, false)) {
                    nameValuePairs.add(new BasicNameValuePair(ParameterCollections.SH_USER_PHOTO, ""
                    ));
                } else {
                    nameValuePairs.add(new BasicNameValuePair(ParameterCollections.URL_IMG_PROFILE, ""
                    ));
                }

                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PROGRAM_NAME,
                        sp.getString(ParameterCollections.SH_NAMA_PROGRAM, "")));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                String jsonResponse = EntityUtils.toString(response.getEntity()).toString();
                Log.e("VtLog response", jsonResponse);

                JSONObject json_result = new JSONObject(jsonResponse);
                cCode = json_result.getString(ParameterCollections.JSON_CHARGE_STATUS);

                if (cCode.equals("success")) {
                    //berhasil
                    JSONObject json_body = json_result.getJSONObject(ParameterCollections.JSON_CHARGE_BODY);

                    cCode_Charge = json_body.getString(ParameterCollections.JSON_CHARGE_STATUS_CODE);

                    if (cCode_Charge.equals("200")) {
                        //success
                        success = true;
                    } else if (cCode_Charge.equals("201")) {
                        //pending
                        success = false;
                    }


                }


            } catch (JSONException e) {

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (success) {
                Toast.makeText(getApplicationContext(), "Donasi Berhasil", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Activity_Selesai_KartuKredit.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Donasi Gagal", Toast.LENGTH_LONG).show();
            }

        }


    }
}
