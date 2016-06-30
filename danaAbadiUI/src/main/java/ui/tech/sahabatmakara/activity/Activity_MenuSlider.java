package ui.tech.sahabatmakara.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.facebook.CallbackManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

import org.json.JSONObject;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.fragment.Fragment_Donate_Step_Info;
import ui.tech.sahabatmakara.fragment.Fragment_Event;
import ui.tech.sahabatmakara.fragment.Fragment_Home;
import ui.tech.sahabatmakara.fragment.Fragment_HubungiKami;
import ui.tech.sahabatmakara.fragment.Fragment_ListGallery;
import ui.tech.sahabatmakara.fragment.Fragment_ListHistory;
import ui.tech.sahabatmakara.fragment.Fragment_News;
import ui.tech.sahabatmakara.fragment.Fragment_Program;
import ui.tech.sahabatmakara.fragment.Fragment_Setting;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import jp.wasabeef.blurry.Blurry;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Activity_MenuSlider extends ActionBarActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBar ac;
    private FragmentManager fm;
    // private NavigationDrawerCallbacks mCallback;
    private ImageView mLauncher;
    Bitmap original = null;
    Bitmap mask;
    Bitmap result = null;
    Canvas mCanvas;
    Paint imgPaint;
    InputStream is;
    DownloadImageTask task = new DownloadImageTask();
    ProgressBar pg_img;
    ImageView img_profile, img_bg;
    SharedPreferences sp;
    private boolean isLogged;

    //	Session session;
    CallbackManager fb_callBackk = CallbackManager.Factory.create();

    TextView tv_Username, tv_Email;
    ImageView img_setting;
    String cUserId;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.activity_mainmenu_slider_ls);

        sp = getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);
        cUserId = sp.getString(ParameterCollections.SH_USER_ID, "1");

        isLogged = sp.getBoolean(ParameterCollections.SH_LOGGED, false);
        fm = getSupportFragmentManager();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLauncher = (ImageView) findViewById(R.id.btn_actionbar);
        mLauncher.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mDrawer.isDrawerOpen(Gravity.START)) {
                    mDrawer.closeDrawer(Gravity.START);
                } else {
                    mDrawer.openDrawer(Gravity.START);
                }
            }
        });

        if (arg0 == null) {
            initView();
            fm.beginTransaction().replace(R.id.frame_container, new Fragment_Home()).commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh_Profile();
    }

    private void refresh_Profile() {
        if (sp.getBoolean(ParameterCollections.SH_LOGGED, false)) {
            new AsyncTask_GetUserData().execute();
        }
    }

    private void initView() {
        TextView tv_Title = (TextView) findViewById(R.id.tv_title_actionbar);

        pg_img = (ProgressBar) findViewById(R.id.pg_img);

        img_bg = (ImageView) findViewById(R.id.img_bg);
        img_setting = (ImageView) findViewById(R.id.btn_setting);
        Button img_logout = (Button) findViewById(R.id.btn_logout);
        if (sp.getBoolean(ParameterCollections.SH_LOGGED, false)) {
            img_logout.setVisibility(View.VISIBLE);
        } else {
            img_logout.setVisibility(View.GONE);
        }

        img_logout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().clear().commit();
                Toast.makeText(getApplicationContext(), "Anda telah Logout", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        img_profile = (ImageView) findViewById(R.id.img_profile);
        tv_Username = (TextView) findViewById(R.id.tv_username);
        tv_Email = (TextView) findViewById(R.id.tv_email);


        TextView tv_Home = (TextView) findViewById(R.id.tv_home);
        TextView tv_Program = (TextView) findViewById(R.id.tv_program);
        TextView tv_Event = (TextView) findViewById(R.id.tv_event);
        TextView tv_News = (TextView) findViewById(R.id.tv_news);
        TextView tv_Gallery = (TextView) findViewById(R.id.tv_gallery);

        TextView tv_Donasi = (TextView) findViewById(R.id.tv_donasi);
        TextView tv_HubungiKami = (TextView) findViewById(R.id.tv_hubungikami);
        TextView tv_History = (TextView) findViewById(R.id.tv_history);


        if (sp.getBoolean(ParameterCollections.SH_LOGGED, false)) {

            task.execute(new String[]{sp.getString(ParameterCollections.SH_USER_PHOTO, ParameterCollections.URL_IMG_PROFILE)});
            tv_Username.setText(sp.getString(ParameterCollections.SH_NAMA_DONATUR, "Firstname"));
            tv_Email.setText(sp.getString(ParameterCollections.SH_EMAIL_DONATUR, "Email"));

        } else {
//            task.execute(new String[]{ParameterCollections.URL_IMG_PROFILE});
            tv_Username.setText(sp.getString(ParameterCollections.SH_NAMA_DONATUR, "Firstname"));
            img_profile.setVisibility(View.INVISIBLE);
            img_setting.setVisibility(View.INVISIBLE);
            tv_Email.setText(sp.getString(ParameterCollections.SH_EMAIL_DONATUR, "Email"));
            tv_Username.setVisibility(View.INVISIBLE);
            tv_Email.setVisibility(View.INVISIBLE);
            pg_img.setVisibility(View.INVISIBLE);
        }

        tv_Title.setTypeface(Font.setFontGaramond(getApplicationContext()));
        tv_Username.setTypeface(Font.setLato(getApplicationContext()));
        tv_Program.setTypeface(Font.setLato(getApplicationContext()));
        tv_Event.setTypeface(Font.setLato(getApplicationContext()));
        tv_News.setTypeface(Font.setLato(getApplicationContext()));
        tv_Gallery.setTypeface(Font.setLato(getApplicationContext()));
        tv_Donasi.setTypeface(Font.setLato(getApplicationContext()));
        tv_History.setTypeface(Font.setLato(getApplicationContext()));

        tv_Home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                fm.beginTransaction().replace(R.id.frame_container, new Fragment_Home()).addToBackStack(null)
//                        .commit();
                fm.beginTransaction().replace(R.id.frame_container, new Fragment_Home()).commit();
                mDrawer.closeDrawers();
            }
        });

        tv_Program.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                fm.beginTransaction().replace(R.id.frame_container, new Fragment_Program()).addToBackStack(null)
//                        .commit();
                fm.beginTransaction().replace(R.id.frame_container, new Fragment_Program()).commit();
                mDrawer.closeDrawers();
            }
        });
        tv_Event.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDrawer.closeDrawers();
                fm.beginTransaction().replace(R.id.frame_container, new Fragment_Event()).addToBackStack(null).commit();
            }
        });
        tv_News.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDrawer.closeDrawers();
//                fm.beginTransaction().replace(R.id.frame_container, new Fragment_News()).addToBackStack(null).commit();
                fm.beginTransaction().replace(R.id.frame_container, new Fragment_News()).commit();
            }
        });
        tv_Gallery.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDrawer.closeDrawers();
//                fm.beginTransaction().replace(R.id.frame_container, new Fragment_ListGallery()).addToBackStack(null)
//                        .commit();
                fm.beginTransaction().replace(R.id.frame_container, new Fragment_ListGallery()).commit();
            }
        });

        tv_Donasi.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDrawer.closeDrawers();
                startActivity(new Intent(getApplicationContext(), Activity_Donate_Donasi.class));

//                fm.beginTransaction().replace(R.id.frame_container, new Fragment_Donate_Step_Info()).commit();
            }
        });

        tv_History.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDrawer.closeDrawers();
//                fm.beginTransaction().replace(R.id.frame_container, new Fragment_ListHistory()).addToBackStack(null)
//                        .commit();
                fm.beginTransaction().replace(R.id.frame_container, new Fragment_ListHistory()).commit();
            }
        });

        tv_HubungiKami.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDrawer.closeDrawers();
//                fm.beginTransaction().replace(R.id.frame_container, new Fragment_ListHistory()).addToBackStack(null)
//                        .commit();
                fm.beginTransaction().replace(R.id.frame_container, new Fragment_HubungiKami()).commit();
            }
        });

        img_setting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDrawer.closeDrawers();
//                fm.beginTransaction().replace(R.id.frame_container, new Fragment_Setting()).addToBackStack(null)
//                        .commit();
                fm.beginTransaction().replace(R.id.frame_container, new Fragment_Setting()).commit();
            }
        });


    }

    private class Async_LoadSlider extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub

            if (sp.getBoolean(ParameterCollections.SH_LOGGED, false)) {
                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
//                        img_bg.setImageBitmap(bitmap);
                        Blurry.with(getApplicationContext()).capture(img_profile).into(img_bg);
                    }

                    @Override
                    public void onBitmapFailed() {

                    }
                };

                Picasso.with(getApplicationContext()).
                        load(sp.getString(ParameterCollections.SH_USER_PHOTO, ParameterCollections.URL_IMG_PROFILE)).into(target);

            }

//            fm.beginTransaction().replace(R.id.frame_container, new Fragment_Program()).commit();

            super.onPostExecute(result);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private Bitmap map_bg;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pg_img.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Bitmap map = null;
            for (String url : params) {
                map = downloadImage(url);
                map_bg = map;
            }

            if (map == null) {
                map = BitmapFactory.decodeResource(getResources(), R.drawable.ic_user);
                map_bg = map;
            }

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
            mask = BitmapFactory.decodeResource(getResources(), R.drawable.mask);
            DisplayMetrics dp = getApplicationContext().getResources().getDisplayMetrics();
            int w = dp.widthPixels;
            // int h = dp.heightPixels;
            int h = 0;
            // if (w < 480) {
            // h = 300;
            // } else {
            // h = 800;
            // }
            w = 300;
            h = 300;
            // result = Bitmap.createBitmap(w, h, Config.ARGB_8888);
            result = Bitmap.createBitmap(w, h, Config.ARGB_8888);

            map = Bitmap.createScaledBitmap(map, w, h, false);
            mask = Bitmap.createScaledBitmap(mask, w, h, false);

            mCanvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

            imgPaint = new Paint();
            imgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

            mCanvas.drawBitmap(map, 0, 0, imgPaint);
            mCanvas.drawBitmap(mask, 0, 0, paint);
            paint.setXfermode(null);

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            pg_img.setVisibility(View.INVISIBLE);
            try {
                img_profile.setImageBitmap(map_bg);
                Blurry.with(getApplicationContext()).radius(25).sampling(1)
                        .async().capture(findViewById(R.id.img_profile)).into((ImageView) findViewById(R.id.img_bg));

                img_profile.setImageBitmap(result);
                img_profile.setScaleType(ScaleType.CENTER_CROP);


            } catch (OutOfMemoryError e) {
                // TODO: handle exception
                img_profile.setImageResource(R.drawable.ic_launcher);
                img_profile.setScaleType(ScaleType.CENTER_CROP);
            }
        }

        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConn = (HttpURLConnection) connection;
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                if (httpConn.getResponseCode() == httpConn.HTTP_OK) {
                    stream = httpConn.getInputStream();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stream;
        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        getFragmentManager().popBackStack();
    }

    private class AsyncTask_GetUserData extends AsyncTask<Void, Void, Void> {
        String cCode, url_img;
        private Dialog_Progress pDialog;
        DownloadImageTask task_lagi = new DownloadImageTask();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new Dialog_Progress();
//            pDialog.show(getSupportFragmentManager(), "");

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();
            JSONObject jObj = sh.getAccountUserDetail(cUserId);

            try {
                cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

                if (cCode.equals("1")) {
                    JSONObject json_Data = jObj.getJSONObject(ParameterCollections.TAG_DATA);
                    url_img = json_Data.getString(ParameterCollections.TAG_USER_IMG_URL);


                }
            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            pDialog.dismiss();

//            Target target = new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
////                        img_bg.setImageBitmap(bitmap);
//                    img_profile.setImageBitmap(bitmap);
//                    Blurry.with(getApplicationContext()).capture(img_profile).into(img_bg);
//                }
//
//                @Override
//                public void onBitmapFailed() {
//
//                }
//            };
//
//            Picasso.with(getApplicationContext()).
//                    load(url_img).into(img_profile);
            task_lagi.execute(new String[]{url_img});

        }

    }
}
