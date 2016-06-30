package ui.tech.sahabatmakara.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ServiceHandlerJSON {

    static String response = null;
    static String url = null;
    static JSONObject obj = null;
    static JSONArray obj_array =null;
    List<NameValuePair> params;
    DefaultHttpClient hClient;
    HttpEntity hEntity;
    HttpResponse hResponse;

    public ServiceHandlerJSON() {
        // TODO Auto-generated constructor stub
        hClient = new DefaultHttpClient();
        hEntity = null;
        hResponse = null;
        params = null;
    }

    public JSONArray get_HomeData() {
        try {

            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_HOME));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_TYPE,
                    ParameterCollections.TAG_TYPE_MOBILE));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Home= :", response);
            obj_array = new JSONArray(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj_array;
    }

    public JSONObject get_VA(String faculty_name) {
        try {

            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_FACULTY,
                    faculty_name));

            url = ParameterCollections.URL_GET_VA
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);

            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message get = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getAllProgram() {
        try {

            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_PROGRAM));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message program= :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getTop3Program() {
        try {

            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_TOP3PROGRAM));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_TYPE,
                    ParameterCollections.TAG_TYPE_MOBILE));


            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Respons Top 3 News", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getProgramDetail(String id_program) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_PROGRAM));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_PROGRAM_ID,
                    id_program));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getAllNews() {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_NEWS));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getNewsDetail(String news_id) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_NEWS));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_NEWS_ID,
                    news_id));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getAllEvent() {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_EVENT));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getUpcomingEvent() {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_EVENT_UPCOMING));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getTestimoni() {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_TESTIMONY));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Respons testimoni", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getSahabatDonasi() {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_DONASI));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Respons Donasi", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getEventDetail(String event_id) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_EVENT));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_EVENT_ID,
                    event_id));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getHistory_Donation(String user_id) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_DONASI_RIWAYAT));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_ID,
                    user_id));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getAllGalleries() {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_GALLERY));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getGalleryDetail(String gallery_id) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_GALLERY));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_GALLERY_ID,
                    gallery_id));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getAccountUserDetail(String account_id) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_ACCOUNT_USER));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_ID,
                    account_id));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getTopNews() {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_TOPNEWS));

            url = ParameterCollections.URL_GET
                    + URLEncodedUtils.format(params, "utf-8");

            HttpGet hGet = new HttpGet(url);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject login(String username, String password) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_ACCOUNT_USER));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_LOGIN_USERNAME,
                    username));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_LOGIN_PASSWORD,
                    password));

            url = ParameterCollections.URL_LOGIN;
//                    + URLEncodedUtils.format(params, "utf-8");

//            HttpGet hGet = new HttpGet(url);
            HttpPost hPost = new HttpPost(url);
            hPost.setEntity(new UrlEncodedFormEntity(params));
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject register(String username, String password, String email) {
        try {
            params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair(ParameterCollections.KIND,
//                    ParameterCollections.KIND_ACCOUNT_USER));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_LOGIN_USERNAME,
                    username));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_LOGIN_PASSWORD,
                    password));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_LOGIN_FULLNAME,
                    ""));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_LOGIN_EMAIL,
                    email));

            url = ParameterCollections.URL_REGIS;
//                    + URLEncodedUtils.format(params, "utf-8");

//            HttpGet hGet = new HttpGet(url);
            HttpPost hPost = new HttpPost(url);
            hPost.setEntity(new UrlEncodedFormEntity(params));
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("result", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject registerFB(String id, String name, String picture, String email, String gender) {
        try {
            params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair(ParameterCollections.KIND,
//                    ParameterCollections.KIND_ACCOUNT_USER));
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_FB));

            params.add(new BasicNameValuePair(ParameterCollections.TAG_FB_NAME,
                    name));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_ID,
                    id));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_FB_EMAIL,
                    email));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_FB_PIC,
                    picture));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_FB_GENDER,
                    gender));

            url = ParameterCollections.URL_FB;
//                    + URLEncodedUtils.format(params, "utf-8");

//            HttpGet hGet = new HttpGet(url);
            HttpPost hPost = new HttpPost(url);
            hPost.setEntity(new UrlEncodedFormEntity(params));
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("result", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject editPassword(String user_id, String username, String user_password) {
        try {
            params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair(ParameterCollections.KIND,
//                    ParameterCollections.KIND_ACCOUNT_USER));
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_EDIT_PASSWORD));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_ID,
                    user_id));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_LOGIN_USERNAME,
                    username));
            params.add(new BasicNameValuePair(ParameterCollections.KIND_LOGIN_PASSWORD,
                    user_password));


            url = ParameterCollections.URL_UPDATE;
//                    + URLEncodedUtils.format(params, "utf-8");

//            HttpGet hGet = new HttpGet(url);
            HttpPost hPost = new HttpPost(url);
            hPost.setEntity(new UrlEncodedFormEntity(params));
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("result", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject editProfile(String user_id, String user_fullname, String user_telp, String user_email,String user_alamat,
                                  String gender, String user_fakultas) {
        try {
            params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair(ParameterCollections.KIND,
//                    ParameterCollections.KIND_ACCOUNT_USER));
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_EDIT_USER));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_ID,
                    user_id));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_FULLNAME,
                    user_fullname));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_TELEPON,
                    user_telp));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_EMAIL,
                    user_email));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_ALAMAT,
                    user_alamat));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_KELAMIN,
                    gender));
            params.add(new BasicNameValuePair(ParameterCollections.TAG_USER_FAKULTAS,
                    user_fakultas));

            url = ParameterCollections.URL_UPDATE;
//                    + URLEncodedUtils.format(params, "utf-8");

//            HttpGet hGet = new HttpGet(url);
            HttpPost hPost = new HttpPost(url);
            hPost.setEntity(new UrlEncodedFormEntity(params));
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("result", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject chargeToken_SaveDonation(String token,String program_id, String user_id, String donation_faculty,
                                               String donation_published, String donation_name, String donation_amount,
                                               String donation_desc, String donation_email, String donation_phone,
                                               String donation_picture, String program_name
                                               ) {
        try {
            params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair(ParameterCollections.KIND_TOKEN,
//                    cToken));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PROGRAM_ID ,
                    program_id));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_USER_ID ,
                    user_id));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_FACULTY ,
                    donation_faculty));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PUBLISHED ,
                    donation_published));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_NAME ,
                    donation_name));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_AMOUNT ,
                    donation_amount));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_DESC ,
                    donation_desc));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_EMAIL  ,
                    donation_email));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PHONE ,
                    donation_phone));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PICTURE ,
                    donation_picture));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PROGRAM_NAME  ,
                    program_name));


            url = ParameterCollections.URL_SEND_TOKEN;
//                    + URLEncodedUtils.format(params, "utf-8");

//            HttpGet hGet = new HttpGet(url);
            HttpPost hPost = new HttpPost(url);
            hPost.setEntity(new UrlEncodedFormEntity(params));
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("result", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject registerUser(String fbid, String firstname,
                                   String lastname, String email, String phone, String address,
                                   String gender, String password) {
        try {
//			params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair(ParameterCollections.KIND,
//					ParameterCollections.KIND_INSERT_USER));
//			params.add(new BasicNameValuePair(
//					ParameterCollections.TAG_INSERT_USER_FBID, fbid));
//			params.add(new BasicNameValuePair(
//					ParameterCollections.TAG_INSERT_USER_FIRSTNAME, firstname));
//			params.add(new BasicNameValuePair(
//					ParameterCollections.TAG_INSERT_USER_LASTNAME, lastname));
//			params.add(new BasicNameValuePair(
//					ParameterCollections.TAG_INSERT_USER_EMAIL, email));
//			params.add(new BasicNameValuePair(
//					ParameterCollections.TAG_INSERT_USER_PHONE, phone));
//			params.add(new BasicNameValuePair(
//					ParameterCollections.TAG_INSERT_USER_ADDRESS, address));
//			params.add(new BasicNameValuePair(
//					ParameterCollections.TAG_INSERT_USER_GENDER, gender));
//			params.add(new BasicNameValuePair(
//					ParameterCollections.TAG_INSERT_USER_PASSWORD, password));
//
//			url = ParameterCollections.URL_INSERT
//					+ URLEncodedUtils.format(params, "utf-8");
            Log.e("URL = ", url);
            HttpPost hPost = new HttpPost(url);
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject getFBPhotoURL(String url_nya) {
        try {
//            params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair(ParameterCollections.KIND,
//                    ParameterCollections.KIND_GALLERY));

            HttpGet hGet = new HttpGet(url_nya);
            hResponse = hClient.execute(hGet);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("Message = :", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }


    public JSONObject insert_jemputdana(String program_id, String user_id, String donation_faculty,
                                         String donation_published, String donation_name, String donation_amount,
                                         String donation_desc, String donation_email, String donation_phone,
                                         String donation_picture
    ) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_DONASI_JEMPUTDANA));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PROGRAM_ID ,
                    program_id));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_USER_ID ,
                    user_id));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_FACULTY ,
                    donation_faculty));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PUBLISHED ,
                    "yes"));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_NAME ,
                    donation_name));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_AMOUNT ,
                    donation_amount));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_DESC ,
                    donation_desc));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_EMAIL  ,
                    donation_email));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PHONE ,
                    donation_phone));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PICTURE ,
                    donation_picture));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_METODE_PEMBAYARAN  ,
                    "shuttle"));


            url = ParameterCollections.URL_INSERT;
//                    + URLEncodedUtils.format(params, "utf-8");

//            HttpGet hGet = new HttpGet(url);
            HttpPost hPost = new HttpPost(url);
            hPost.setEntity(new UrlEncodedFormEntity(params));
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("result", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }

    public JSONObject insert_donasi_transfer(String program_id, String user_id, String donation_faculty,
                                        String donation_published, String donation_name, String donation_amount,
                                        String donation_desc, String donation_email, String donation_phone,
                                        String donation_picture
    ) {
        try {
            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(ParameterCollections.KIND,
                    ParameterCollections.KIND_DONASI_JEMPUTDANA));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PROGRAM_ID ,
                    program_id));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_USER_ID ,
                    user_id));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_FACULTY ,
                    donation_faculty));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PUBLISHED ,
                    "yes"));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_NAME ,
                    donation_name));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_AMOUNT ,
                    donation_amount));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_DESC ,
                    donation_desc));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_EMAIL  ,
                    donation_email));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PHONE ,
                    donation_phone));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_CHARGE_PICTURE ,
                    donation_picture));
            params.add(new BasicNameValuePair(ParameterCollections.JSON_METODE_PEMBAYARAN  ,
                    "transfer"));


            url = ParameterCollections.URL_INSERT;
//                    + URLEncodedUtils.format(params, "utf-8");

//            HttpGet hGet = new HttpGet(url);
            HttpPost hPost = new HttpPost(url);
            hPost.setEntity(new UrlEncodedFormEntity(params));
            hResponse = hClient.execute(hPost);
            hEntity = hResponse.getEntity();
            response = EntityUtils.toString(hEntity);
            Log.e("result", response);
            obj = new JSONObject(response);
        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        } catch (ClientProtocolException e) {
            // TODO: handle exception
        } catch (IOException e) {
            // TODO: handle exception
        }

        return obj;
    }
}