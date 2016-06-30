package ui.tech.sahabatmakara.fragment;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import android.R;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;

import ui.tech.sahabatmakara.adapters.CustomAdapter_News;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.entities.RowData_News;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Fragment_News extends Fragment {
	private ListView lv;
	private CustomAdapter_News adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new AsyncTask_AllNews().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		lv = new ListView(getActivity());
		lv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ColorDrawable color_divider = new ColorDrawable(R.color.transparent);
		lv.setDivider(color_divider);
		lv.setDividerHeight(1);

		// ArrayList<RowData_News> datanya = new ArrayList<RowData_News>();
		// datanya.add(new RowData_News("", "Angkatan Sumpah Lulusan Ners FIK
		// UI", "",
		// "http://128.199.176.5/UI/admin/files/thumbnail/55e6e4b5133c9.jpg",
		// "Saturday, 01-09-2015"));
		// datanya.add(new RowData_News("", "Alumni FIK UI Bersatu", "",
		// "http://128.199.176.5/UI/admin/files/thumbnail/55e6e44adca28.jpg",
		// "Monday, 30-04-2015"));
		// datanya.add(new RowData_News("", "Dies Natalis Ke-54 Tahun FMIPA UI",
		// "",
		// "http://128.199.176.5/UI/admin/files/thumbnail/55e6e575d49a4.jpg",
		// "Tuesday, 16-12-2014"));
		// datanya.add(new RowData_News("", "Angkatan Sumpah Lulusan Ners FIK
		// UI", "",
		// "http://128.199.176.5/UI/admin/files/thumbnail/55e6e4b5133c9.jpg",
		// "Saturday, 01-09-2015"));

		return lv;
	}

	private class AsyncTask_AllNews extends AsyncTask<Void, Void, Void> {
		String cCode, cUrlImg;
		private ArrayList<RowData_News> objects;
		private Dialog_Progress pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new Dialog_Progress();
			pDialog.show(getFragmentManager(), "");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			try {
				JSONObject jObj = sh.getAllNews();

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONArray jArray = jObj.getJSONArray(ParameterCollections.TAG_DATA);

					objects = new ArrayList<RowData_News>();

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject c = jArray.getJSONObject(i);
						String id = c.getString(ParameterCollections.TAG_NEWS_ID);
						String title = c.getString(ParameterCollections.TAG_NEWS_TITLE);
						String date = c.getString(ParameterCollections.TAG_NEWS_DATE);
						String creator= c.getString(ParameterCollections.TAG_NEWS_CREATOR);
						String desc = c.getString(ParameterCollections.TAG_NEWS_DESC);
						desc = Jsoup.parse(desc).text();
						
						JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);

						if (jArray_Images.length() >= 0) {
							for (int j = 0; j < jArray_Images.length(); j++) {
								JSONObject d = jArray_Images.getJSONObject(j);
								cUrlImg = ParameterCollections.URL_IMG_ORIGINAL
										+ d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

							}

						}
						objects.add(new RowData_News(id, title, desc, cUrlImg, date,creator));
//						objects.add(new RowData_Event(id, title, place, desc, cUrlImg, date + " " + time ));
					}
				}

				// } catch (JSONException e) {

			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
			Collections.reverse(objects);
			adapter = new CustomAdapter_News(getActivity(), 0, objects);
			lv.setAdapter(adapter);
		}
	}
	
}
