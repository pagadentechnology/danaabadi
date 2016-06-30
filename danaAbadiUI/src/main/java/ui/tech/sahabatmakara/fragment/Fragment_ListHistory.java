package ui.tech.sahabatmakara.fragment;

import java.util.ArrayList;

import ui.tech.sahabatmakara.activity.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import ui.tech.sahabatmakara.adapters.CustomAdapter_ListHistory;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.entities.RowData_History;
import ui.tech.sahabatmakara.entities.RowData_News;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Fragment_ListHistory extends Fragment{
	ListView lv;
	TextView tv_empty;
	ImageView img_empty;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new AsyncTask_History_Donasi().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_empty, null);
		lv = (ListView)v.findViewById(R.id.lv);
		tv_empty = (TextView)v.findViewById(R.id.tv);
		img_empty = (ImageView)v.findViewById(R.id.img);

		return v;
	}

	private class AsyncTask_History_Donasi extends AsyncTask<Void,Void,Void>{
		String cCode, cUrlImg;
		private ArrayList<RowData_History> objects;
		private Dialog_Progress pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new Dialog_Progress();
			pDialog.show(getFragmentManager(), "");
		}

		@Override
		protected Void doInBackground(Void... voids) {
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			try {
				JSONObject jObj = sh.getHistory_Donation("155");

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONArray jArray = jObj.getJSONArray(ParameterCollections.TAG_DATA);

					objects = new ArrayList<RowData_History>();

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject c = jArray.getJSONObject(i);
						String id = c.getString(ParameterCollections.TAG_DONATION_ID);
						String title = c.getString(ParameterCollections.TAG_DONATION_TITLE);
						String amount = c.getString(ParameterCollections.TAG_DONATION_AMOUNT);
						String fakultas_donasi = c.getString(ParameterCollections.TAG_DONATION_TO_FACULTY);

//						JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);
//						if (jArray_Images.length() >= 0) {
//							for (int j = 0; j < jArray_Images.length(); j++) {
//								JSONObject d = jArray_Images.getJSONObject(j);
//								cUrlImg = ParameterCollections.URL_IMG_ORIGINAL
//										+ d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);
//							}
						objects.add(new RowData_History(id, title, amount, fakultas_donasi));

						}

//						objects.add(new RowData_Event(id, title, place, desc, cUrlImg, date + " " + time ));

				}

				// } catch (JSONException e) {

			}catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			pDialog.dismiss();
			if(cCode.equals("1")){
				CustomAdapter_ListHistory adapter = new CustomAdapter_ListHistory(getActivity(), 0, objects);
				lv.setAdapter(adapter);
				tv_empty.setVisibility(View.GONE);
				img_empty.setVisibility(View.GONE);

			}else{
				lv.setVisibility(View.GONE);
			}

		}
	}

}
