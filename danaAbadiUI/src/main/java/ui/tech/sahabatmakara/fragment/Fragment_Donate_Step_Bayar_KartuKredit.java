package ui.tech.sahabatmakara.fragment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

import ui.tech.sahabatmakara.activity.Activity_Selesai_KartuKredit;
import ui.tech.sahabatmakara.activity.Activity_Webview;
import ui.tech.sahabatmakara.activity.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ui.co.veritrans.android.api.VTDirect;
import ui.co.veritrans.android.api.VTInterface.ITokenCallback;
import ui.co.veritrans.android.api.VTModel.VTCardDetails;
import ui.co.veritrans.android.api.VTModel.VTToken;
import ui.co.veritrans.android.api.VTUtil.VTConfig;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.FourDigitCardFormatWatcher;
import ui.tech.sahabatmakara.util.ParameterCollections;


public class Fragment_Donate_Step_Bayar_KartuKredit extends Fragment {
    TextView tv_TotalDonasi, tv_NamaDonatur;
    String cTotalDonasi, cNamaDonator;
    private String cToken, cBank;
    private boolean success = false;
    private String urlRedirect;

    private EditText ed_Nama, ed_NoKartu, ed_CCV, ed_Tlp, ed_Alamat, ed_Negara;
    private Spinner spinner_Bulan, spinner_Tahun;
    private String cKartu_Bulan, cKartu_Tahun;
    private Button btn, btn_konfirmasi;
    private SharedPreferences sp;

    public static Fragment_Donate_Step_Bayar_KartuKredit create(String nama_donatur,
                                                                String jumlah_donasi) {
        Fragment_Donate_Step_Bayar_KartuKredit fragment = new Fragment_Donate_Step_Bayar_KartuKredit();
        Bundle b = new Bundle();
        b.putString(ParameterCollections.ARGS_DONATUR_NAME, nama_donatur);
        b.putString(ParameterCollections.ARGS_DONATUR_JUMLAH_DONASI, jumlah_donasi);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.layout_step_bayar_kartukredit, null);

        sp = getActivity().getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);

        cNamaDonator = getArguments().getString(ParameterCollections.ARGS_DONATUR_NAME);
        cTotalDonasi = getArguments().getString(ParameterCollections.ARGS_DONATUR_JUMLAH_DONASI);

        getAllView(v);

        return v;
    }

    private void getAllView(View v) {
        TextView label_informasi = (TextView) v.findViewById(R.id.label_informasi);
        TextView label_nama_kartu = (TextView) v.findViewById(R.id.label_nama_kartu);
        TextView label_nokartu = (TextView) v.findViewById(R.id.label_nokartu);
        TextView label_tgl = (TextView) v.findViewById(R.id.label_tgl);
        TextView label_ccv = (TextView) v.findViewById(R.id.label_ccv);
        TextView label_informasi_penagihan = (TextView) v.findViewById(R.id.label_informasi_penagihan);
        TextView label_no_tlp = (TextView) v.findViewById(R.id.label_no_tlp);
        TextView label_alamat = (TextView) v.findViewById(R.id.label_alamat);
        TextView label_negara = (TextView) v.findViewById(R.id.label_negara);
        TextView label_total_donasi = (TextView) v.findViewById(R.id.label_total_donasi);
        TextView tv_donate_total = (TextView) v.findViewById(R.id.tv_donate_total);

        label_informasi.setTypeface(Font.setLato(getActivity()));
        label_nama_kartu.setTypeface(Font.setLato(getActivity()));
        label_nokartu.setTypeface(Font.setLato(getActivity()));
        label_tgl.setTypeface(Font.setLato(getActivity()));
        label_ccv.setTypeface(Font.setLato(getActivity()));
        label_informasi_penagihan.setTypeface(Font.setLato(getActivity()));
        label_no_tlp.setTypeface(Font.setLato(getActivity()));
        label_alamat.setTypeface(Font.setLato(getActivity()));
        label_negara.setTypeface(Font.setLato(getActivity()));
        label_total_donasi.setTypeface(Font.setLato(getActivity()));
        tv_donate_total.setTypeface(Font.setLato(getActivity()));

        tv_TotalDonasi = (TextView) v.findViewById(R.id.tv_donate_total);
        tv_TotalDonasi.setTypeface(Font.setLato(getActivity()));
        ed_Nama = (EditText) v.findViewById(R.id.ed_donate_bayar_nama_kartukredit);
        ed_Nama.setTypeface(Font.setLato(getActivity()));
        ed_NoKartu = (EditText) v.findViewById(R.id.ed_donate_bayar_nokartu);
        ed_NoKartu.setTypeface(Font.setLato(getActivity()));
        ed_NoKartu.addTextChangedListener(new FourDigitCardFormatWatcher());

        ed_CCV = (EditText) v.findViewById(R.id.ed_donate_bayar_ccv);
        ed_Tlp = (EditText) v.findViewById(R.id.ed_donate_bayar_no_tlp);
        ed_Tlp.addTextChangedListener(new FourDigitCardFormatWatcher());
        ed_Alamat = (EditText) v.findViewById(R.id.ed_donate_bayar_alamat_penagihan);
        ed_Negara = (EditText) v.findViewById(R.id.ed_donate_bayar_negara_penerbit);

        ed_CCV.setTypeface(Font.setLato(getActivity()));
        ed_Tlp.setTypeface(Font.setLato(getActivity()));
        ed_Alamat.setTypeface(Font.setLato(getActivity()));
        ed_Negara.setTypeface(Font.setLato(getActivity()));

        spinner_Bulan = (Spinner) v.findViewById(R.id.spinner_bulan);
        spinner_Tahun = (Spinner) v.findViewById(R.id.spinner_tahun);

        spinner_Bulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cKartu_Bulan = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_Tahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cKartu_Tahun = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn = (Button) v.findViewById(R.id.btn_next);
        btn_konfirmasi = (Button) v.findViewById(R.id.btn_konfirmasi);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask_GetToken().execute();
            }
        });

        btn_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask_SendToken().execute();
            }
        });

        tv_TotalDonasi.setText(cTotalDonasi);

    }

    private VTCardDetails CardFactory(boolean secure, String card_number,
                                      String ccv, int exp_month, int exp_year,
                                      String amount) {
        VTCardDetails cardDetails = new VTCardDetails();
//		cardDetails.setCard_number("4811111111111114");
        cardDetails.setCard_number(card_number);
        cardDetails.setCard_cvv(ccv);
        cardDetails.setCard_exp_month(exp_month);
        cardDetails.setCard_exp_year(exp_year);
        cardDetails.setSecure(secure);
//	        cardDetails.setGross_amount(totalPrice+"");
        cardDetails.setGross_amount(amount);
        return cardDetails;
    }

    private class AsyncTask_GetToken extends AsyncTask<Void, Void, Void> {
        private SharedPreferences sp;
        private String cCardNumber, cCCV, cAmount, cErrorMessage;
        private int exp_Month, exp_Year;
        private Dialog_Progress pDialog;

        private void getAllDonatorData() {
            sp = getActivity().getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
            cCardNumber = ed_NoKartu.getText().toString();
            cCardNumber = cCardNumber.replace(" ", "").trim();
            exp_Month = Integer.parseInt(cKartu_Bulan);
            exp_Year = Integer.parseInt(cKartu_Tahun);
            cCCV = ed_CCV.getText().toString();
            cAmount = cTotalDonasi;

            String cTlp = ed_Tlp.getText().toString();
            cTlp = cTlp.replace(" ", "").trim();
            sp.edit().putString(ParameterCollections.SH_PHONE_DONATUR, cTlp).commit();
        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new Dialog_Progress();
            pDialog.show(getFragmentManager(), "");

            Toast.makeText(getActivity(), "Sedang mendapatkan Token dari Server Veritrans", Toast.LENGTH_SHORT).show();
            getAllDonatorData();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            VTConfig.VT_IsProduction = true;
//            VTConfig.VT_IsProduction = false;
            VTConfig.CLIENT_KEY = ParameterCollections.VT_CLIENT;

            VTDirect vtDirect = new VTDirect();
            VTCardDetails cardDetails = CardFactory(true, cCardNumber, cCCV, exp_Month, exp_Year, cAmount);
            vtDirect.setCard_details(cardDetails);
            vtDirect.getToken(new ITokenCallback() {

                @Override
                public void onSuccess(VTToken token) {
                    // TODO Auto-generated method stub
                    if (token.getRedirect_url() != null) {

                        Log.e("VtLog Redirect URL", token.getRedirect_url());
                        success = true;

//					getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(token.getRedirect_url())));
                    }

                    Log.e("VtLog Token", token.getToken_id());
                    cToken = token.getToken_id();
                    cBank = token.getBank();

                    //load Browser
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(token.getRedirect_url()));
//                    startActivity(intent);
                    btn.setVisibility(View.GONE);
                    btn_konfirmasi.setVisibility(View.INVISIBLE);

                    Intent intent = new Intent(getActivity(), Activity_Webview.class);
                    intent.putExtra("url", token.getRedirect_url());
                    intent.putExtra("token",cToken);
                    intent.putExtra("bank", cBank);
                    intent.putExtra("total", cAmount);
                    startActivity(intent);
                    getActivity().finish();



                }

                @Override
                public void onError(Exception arg0) {
                    // TODO Auto-generated method stub
                    Log.e("VtLog", arg0.getMessage().toString());

                    Toast.makeText(getActivity(), arg0.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pDialog.dismiss();
            if (success) {
//                Toast.makeText(getActivity(), cToken, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AsyncTask_SendToken extends AsyncTask<Void, Void, Void> {
        private String cCode, cCode_Insert, cCode_Message, cCode_Charge = "";
        private boolean success;
        private Dialog_Progress pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new Dialog_Progress();
            pDialog.show(getFragmentManager(), "");
            Toast.makeText(getActivity(), "Donasi anda sedang diproses...", Toast.LENGTH_LONG).show();
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
                        "0"));
//                nameValuePairs.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PROGRAM_ID,
//                        sp.getString(ParameterCollections.SH_PROGRAM_ID, "")));
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
                        "-"));

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
            pDialog.dismiss();
            if (success) {
                Intent intent = new Intent(getActivity(), Activity_Selesai_KartuKredit.class);
                startActivity(intent);
                getActivity().finish();

                getActivity().finish();
            } else {
                Toast.makeText(getActivity(), "Donasi Gagal", Toast.LENGTH_LONG).show();
            }

        }


    }
}
