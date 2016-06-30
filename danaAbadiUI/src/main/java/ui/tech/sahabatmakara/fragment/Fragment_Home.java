package ui.tech.sahabatmakara.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

import ui.tech.sahabatmakara.activity.Activity_DetailProgram;
import ui.tech.sahabatmakara.activity.R;
import ui.tech.sahabatmakara.entities.RowData_KataMereka;
import ui.tech.sahabatmakara.entities.RowData_News;
import ui.tech.sahabatmakara.entities.RowData_Program;
import ui.tech.sahabatmakara.entities.RowData_SahabatMakara;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Fragment_Home extends Fragment {
    ViewPager vp_highlight, vp_KataMereka, vp_SahabatMakara;
    PagerAdapter_Highlight mPagerAdapter;
    PagerAdapter_KataMereka mPagerAdapter_KataMereka;
    PagerAdapter_Sahabat mPagerAdapter_Sahabat;
    CirclePageIndicator indikator_Highlight, indikator_KataMereka, indikator_Sahabat;

    private ArrayList<RowData_News> data_Highlight;
    private ArrayList<RowData_Program> data_Program;
    private ArrayList<RowData_KataMereka> data_KataMereka;
    private ArrayList<RowData_SahabatMakara> data_SahabatMakara;

    private TextView tv_target_01, tv_target_02, tv_target_03, tv_title_01, tv_title_02, tv_title_03,
            tv_label_katamereka, tv_label_sahabatmakara;
    private ImageView img_program_1, img_program_2, img_program_3;

    private String program_id_1, program_id_2, program_id_3;
    private int currentPage=0;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.layout_home, null);

        new AsyncTask_GetAllData().execute();

        rv = (RecyclerView)v.findViewById(R.id.rv);

        vp_highlight = (ViewPager) v.findViewById(R.id.viewPager_highlight);
        vp_KataMereka = (ViewPager) v.findViewById(R.id.viewPager_katamereka);
        vp_SahabatMakara = (ViewPager) v.findViewById(R.id.viewPager_sahabat);

        indikator_Highlight = (CirclePageIndicator) v.findViewById(R.id.indicator_highlight);
        indikator_Highlight.setFillColor(Color.BLACK);
        indikator_Highlight.setStrokeColor(Color.DKGRAY);

        indikator_KataMereka = (CirclePageIndicator) v.findViewById(R.id.indicator_katamereka);
        indikator_KataMereka.setFillColor(Color.BLACK);
        indikator_KataMereka.setStrokeColor(Color.DKGRAY);

        indikator_Sahabat = (CirclePageIndicator) v.findViewById(R.id.indicator_sahabat);
        indikator_Sahabat.setFillColor(Color.BLACK);
        indikator_Sahabat.setStrokeColor(Color.DKGRAY);

        tv_target_01 = (TextView) v.findViewById(R.id.tv_target_01);
        tv_target_02 = (TextView) v.findViewById(R.id.tv_target_02);
        tv_target_03 = (TextView) v.findViewById(R.id.tv_target_03);

        tv_title_01 = (TextView) v.findViewById(R.id.tv_title_01);
        tv_title_02 = (TextView) v.findViewById(R.id.tv_title_02);
        tv_title_03 = (TextView) v.findViewById(R.id.tv_title_03);

        img_program_1 = (ImageView) v.findViewById(R.id.img_program_1);
        img_program_2 = (ImageView) v.findViewById(R.id.img_program_2);
        img_program_3 = (ImageView) v.findViewById(R.id.img_program_3);

        tv_label_katamereka = (TextView) v.findViewById(R.id.tv_label_katamereka);
        tv_label_sahabatmakara = (TextView) v.findViewById(R.id.tv_label_sahabatmakara);

        tv_label_katamereka.setTypeface(Font.setFontGaramond(getActivity()));
        tv_label_sahabatmakara.setTypeface(Font.setFontGaramond(getActivity()));


        return v;
    }

    private class AsyncTask_GetAllData extends AsyncTask<Void, Void, Void> {
        private String cCode;
        private String title_1, target_1, url_1, title_2, target_2, url_2, title_3, target_3, url_3;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            data_Highlight = new ArrayList<RowData_News>();
            data_Program = new ArrayList<RowData_Program>();
            data_KataMereka = new ArrayList<RowData_KataMereka>();
            data_SahabatMakara = new ArrayList<RowData_SahabatMakara>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ServiceHandlerJSON sh = new ServiceHandlerJSON();

            try {
                JSONArray array_home = sh.get_HomeData();

                cCode ="1";

                    JSONObject jObj_Event = array_home.getJSONObject(0);

                try{
                    JSONArray array_event = jObj_Event.getJSONArray(ParameterCollections.TAG_DATA);

                    for(int i=0; i < array_event.length(); i++){
                        JSONObject c = array_event.getJSONObject(i);
                        String id = c.getString(ParameterCollections.TAG_EVENT_ID);
                        String title = c.getString(ParameterCollections.TAG_EVENT_TITLE);
                        String tgl = c.getString(ParameterCollections.TAG_EVENT_DATE);
                        String desc = c.getString(ParameterCollections.TAG_EVENT_DESC);
//                        desc = Jsoup.parse(desc).text();

                        String url = "";
                        try{
                            JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);
                            if (jArray_Images.length() >= 0) {
                                for (int j = 0; j < jArray_Images.length(); j++) {
                                    JSONObject d = jArray_Images.getJSONObject(j);
                                    url = ParameterCollections.URL_IMG_ORIGINAL
                                            + d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

                                }

                            }
                        }catch (Exception e){
                            url = ParameterCollections.URL_IMG_PROFILE;
                        }

                        data_Highlight.add(new RowData_News(id, title, desc, url, tgl, ""));
                    }

                }catch (Exception e){
                    data_Highlight.add(new RowData_News("", "Tidak ada Acara", "", ParameterCollections.URL_EVENT_NO_IMG,
                            "", ""));
                }


                    JSONObject jObj_Topnews = array_home.getJSONObject(3);
                    JSONArray array_topnews = jObj_Topnews.getJSONArray(ParameterCollections.TAG_DATA);
                    for(int i=0; i < array_topnews.length(); i++){
                        JSONObject c = array_topnews.getJSONObject(i);
                        String id = c.getString(ParameterCollections.TAG_NEWS_ID);
                        String title = c.getString(ParameterCollections.TAG_NEWS_TITLE);
                        String date = c.getString(ParameterCollections.TAG_NEWS_DATE);
                        String creator = c.getString(ParameterCollections.TAG_NEWS_CREATOR);
                        String desc = c.getString(ParameterCollections.TAG_NEWS_DESC);

//                        desc = Jsoup.parse(desc).text();

                        String url = "";
                        try{
                            JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);
                            if (jArray_Images.length() >= 0) {
                                for (int j = 0; j < jArray_Images.length(); j++) {
                                    JSONObject d = jArray_Images.getJSONObject(j);
                                    url = ParameterCollections.URL_IMG_ORIGINAL
                                            + d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

                                }

                            }
                        }catch (Exception e){
                            url = ParameterCollections.URL_IMG_PROFILE;
                        }

                        data_Highlight.add(new RowData_News(id, title, desc, url, date + " By " + creator, creator));

                    }

                    JSONObject jObj_3Program = array_home.getJSONObject(4);
                    JSONArray array_3program = jObj_3Program.getJSONArray(ParameterCollections.TAG_DATA);
                    for(int i=0; i < array_3program.length(); i++){
                        JSONObject c = array_3program.getJSONObject(i);
                        String id = c.getString(ParameterCollections.TAG_PROGRAM_ID);
                        String title = c.getString(ParameterCollections.TAG_PROGRAM_TITLE);
                        String goal = c.getString(ParameterCollections.TAG_PROGRAM_GOAL);

                        NumberFormat format = NumberFormat.getCurrencyInstance();
                        String nominal_collected = format.format(new BigDecimal(goal));
                        nominal_collected = nominal_collected.replace("$", "Rp ");
                        nominal_collected = nominal_collected.replace(".00", "");
                        nominal_collected = nominal_collected.replace(",", ".");

                        String raised = c.getString(ParameterCollections.TAG_PROGRAM_RAISED);
                        String donatur = c.getString(ParameterCollections.TAG_PROGRAM_DONATOR);
                        String desc = c.getString(ParameterCollections.TAG_PROGRAM_DESC);
//                        desc = Jsoup.parse(desc).text();


                        String cUrlImg = "";
                        try{
                            JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);
                            if (jArray_Images.length() >= 0) {
                                for (int j = 0; j < jArray_Images.length(); j++) {
                                    JSONObject d = jArray_Images.getJSONObject(j);
                                    cUrlImg = ParameterCollections.URL_IMG_ORIGINAL
                                            + d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

                                }

                            }
                        }catch (Exception e){
                            cUrlImg = ParameterCollections.URL_IMG_PROFILE;
                        }
                        switch (i) {
                            case 0:
                                program_id_1 = id;
                                target_1 = nominal_collected;
                                title_1 = title;
                                url_1 = cUrlImg;
                                break;
                            case 1:
                                program_id_2 = id;
                                target_2 = nominal_collected;
                                title_2 = title;
                                url_2 = cUrlImg;
                                break;
                            case 2:
                                program_id_3 = id;
                                target_3 = nominal_collected;
                                title_3 = title;
                                url_3 = cUrlImg;
                                break;

                            default:
                                break;
                        }
                    }

                    JSONObject jObj_Testimony = array_home.getJSONObject(5);
                    JSONArray array_testimony = jObj_Testimony.getJSONArray(ParameterCollections.TAG_DATA);
                    for(int i=0; i < array_testimony.length(); i++) {
                        JSONObject c = array_testimony.getJSONObject(i);

                        String id = c.getString(ParameterCollections.TAG_TESTIMONI_ID);
                        String content = c.getString(ParameterCollections.TAG_TESTIMONI_CONTENT);
                        String creator = c.getString(ParameterCollections.TAG_TESTIMONI_CREATOR);


                        String url = "";
                        try{
                            JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);
                            if (jArray_Images.length() >= 0) {
                                for (int j = 0; j < jArray_Images.length(); j++) {
                                    JSONObject d = jArray_Images.getJSONObject(j);
                                    url = ParameterCollections.URL_IMG_ORIGINAL
                                            + d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

                                }

                            }
                        }catch (Exception e){
                            url = ParameterCollections.URL_IMG_PROFILE;
                        }

                        data_KataMereka.add(new RowData_KataMereka(id, content, creator, url));
                    }

                    JSONObject jObj_Sahabat = array_home.getJSONObject(2);
                    JSONArray array_sahabat = jObj_Sahabat.getJSONArray(ParameterCollections.TAG_DATA);
                    for(int i=0; i < array_sahabat.length(); i++) {
                        JSONObject c = array_sahabat.getJSONObject(i);

                        String nama = c.getString(ParameterCollections.TAG_DONATION_NAME);
                        String fakultas = c.getString(ParameterCollections.TAG_DONATION_FACULTY);
                        String url = c.getString(ParameterCollections.TAG_DONATION_IMG);

                        data_SahabatMakara.add(new RowData_SahabatMakara("", nama, fakultas, url));
                    }




            } catch (Exception e) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (cCode.equals("1")) {

                //Rv
//                RecyclerAdapter_Highlight rv_Adapter = new RecyclerAdapter_Highlight(data_Highlight, getActivity());
//                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
//                rv.setAdapter(rv_Adapter);
//                rv.setLayoutManager(layoutManager);
                mPagerAdapter = new PagerAdapter_Highlight(getFragmentManager());
                vp_highlight.setAdapter(mPagerAdapter);

                mPagerAdapter_KataMereka = new PagerAdapter_KataMereka(getFragmentManager());
                vp_KataMereka.setAdapter(mPagerAdapter_KataMereka);

                mPagerAdapter_Sahabat = new PagerAdapter_Sahabat(getFragmentManager());
                vp_SahabatMakara.setAdapter(mPagerAdapter_Sahabat);

                indikator_Highlight.setViewPager(vp_highlight);

                ViewPager.OnPageChangeListener listener_Hightlight = new ViewPager.SimpleOnPageChangeListener();
                indikator_Highlight.setOnPageChangeListener(listener_Hightlight);

                Highlight_PageChangeListener highlight_listener = new Highlight_PageChangeListener();
                indikator_Highlight.setOnPageChangeListener(highlight_listener);

                indikator_KataMereka.setViewPager(vp_KataMereka);

                ViewPager.OnPageChangeListener listener_KataMereka = new ViewPager.SimpleOnPageChangeListener();
                indikator_KataMereka.setOnPageChangeListener(listener_KataMereka);


                indikator_Sahabat.setViewPager(vp_SahabatMakara);

                ViewPager.OnPageChangeListener listener_Sahabat = new ViewPager.SimpleOnPageChangeListener();
                indikator_Sahabat.setOnPageChangeListener(listener_Sahabat);

                tv_target_01.setText(target_1);
                tv_title_01.setText(title_1);
                if(url_1 == null){
                    img_program_1.setImageResource(R.drawable.ic_launcher);

                }else{
                    Picasso.with(getActivity()).load(url_1).into(img_program_1);
                }


                img_program_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), Activity_DetailProgram.class);
                        intent.putExtra("id", program_id_1);
                        startActivity(intent);
                    }
                });

                tv_target_02.setText(target_2);
                tv_title_02.setText(title_2);

                if(url_2 == null){
                    img_program_2.setImageResource(R.drawable.ic_launcher);
                }else{
                    Picasso.with(getActivity()).load(url_2).into(img_program_2);
                }


                img_program_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Activity_DetailProgram.class);
                        intent.putExtra("id", program_id_2);
                        startActivity(intent);
                    }
                });

                tv_target_03.setText(target_3);
                tv_title_03.setText(title_3);
                if(url_3 == null){
                    img_program_3.setImageResource(R.drawable.ic_launcher);
                }else{
                    Picasso.with(getActivity()).load(url_3).into(img_program_3);
                }

                img_program_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Activity_DetailProgram.class);
                        intent.putExtra("id", program_id_3);
                        startActivity(intent);

                    }
                });
            }
        }
    }

    public class Highlight_PageChangeListener extends ViewPager.SimpleOnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            currentPage = position;
            Log.e("position = ", String.valueOf(position));
        }

        public int getCurrentPage() {
            return currentPage;
        }
    }

    public class PagerAdapter_Highlight extends FragmentStatePagerAdapter {

        public PagerAdapter_Highlight(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment_Home_VP_Highlight.create(position, data_Highlight.get(position).id,
                    data_Highlight.get(position).title, data_Highlight.get(position).date,
                    data_Highlight.get(position).desc, data_Highlight.get(position).link);
        }

        @Override
        public int getCount() {
            return data_Highlight.size();
        }
    }

    public class PagerAdapter_KataMereka extends FragmentStatePagerAdapter {

        public PagerAdapter_KataMereka(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment_Home_VP_KataMereka.create(position, data_KataMereka.get(position).title,
                    data_KataMereka.get(position).url);
        }

        @Override
        public int getCount() {
            return data_KataMereka.size();
        }
    }

    public class PagerAdapter_Sahabat extends FragmentStatePagerAdapter {

        public PagerAdapter_Sahabat(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return Fragment_Home_VP_Sahabat.create(0, data_SahabatMakara.get(0).nama,
                        data_SahabatMakara.get(0).fakultas, data_SahabatMakara.get(0).url,
                        data_SahabatMakara.get(1).nama,
                        data_SahabatMakara.get(1).fakultas, data_SahabatMakara.get(1).url);

            }else {
                return Fragment_Home_VP_Sahabat.create(2, data_SahabatMakara.get(2).nama,
                        data_SahabatMakara.get(2).fakultas, data_SahabatMakara.get(2).url,
                        data_SahabatMakara.get(3).nama,
                        data_SahabatMakara.get(3).fakultas, data_SahabatMakara.get(3).url);

            }
//            else if(position == 2){
//                return Fragment_Home_VP_Sahabat.create(position, data_SahabatMakara.get(position).nama,
//                        data_SahabatMakara.get(position).fakultas, data_SahabatMakara.get(position).url,
//                        data_SahabatMakara.get(position + 1).nama,
//                        data_SahabatMakara.get(position + 1).fakultas, data_SahabatMakara.get(position + 1).url);
//
//            }else{
//                return Fragment_Home_VP_Sahabat.create(position, data_SahabatMakara.get(position).nama,
//                        data_SahabatMakara.get(position).fakultas, data_SahabatMakara.get(position).url,
//                        data_SahabatMakara.get(position + 1).nama,
//                        data_SahabatMakara.get(position + 1).fakultas, data_SahabatMakara.get(position + 1).url);
//            }

        }

        @Override
        public int getCount() {
            return data_SahabatMakara.size() / 2;
        }
    }
}
