package ui.tech.sahabatmakara.fragment;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import ui.tech.sahabatmakara.activity.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import ui.tech.sahabatmakara.adapters.CustomAdapter_Event;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.entities.RowData_Event;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Fragment_Event extends Fragment{
	private ListView lv;
	private CustomAdapter_Event adapter;
	private ArrayList<RowData_Event> objects;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		new AsyncTask_AllEvent().execute();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_listview, null);
		
		lv = (ListView)v.findViewById(R.id.lv);
		
//		objects = new ArrayList<RowData_Event>();
//		objects.add(new RowData_Event("", "MALAM APRESIASI", "SOHEANNA HALL , SCBD LOT 11A JL JEND KAV 52-53, JL. JEND. SUDIRMAN, DAERAH KHUSUS IBUKOTA JAKARTA", 
//				"Malam Apresiasi Dana Abadi UI", "http://128.199.176.5/UI/admin/files/thumbnail/55e3d87c7b936.jpg", "THURSDAY, 22-10-2015 20:15:00"));
//		
//		adapter = new CustomAdapter_Event(getActivity(), 0, objects);		
//		lv.setAdapter(adapter);
		return v;
	}
	
	private class AsyncTask_AllEvent extends AsyncTask<Void, Void, Void> {
		String cCode, cUrlImg;
		private ArrayList<RowData_Event> objects;
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
				JSONObject jObj = sh.getAllEvent();

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONArray jArray = jObj.getJSONArray(ParameterCollections.TAG_DATA);

					objects = new ArrayList<RowData_Event>();

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject c = jArray.getJSONObject(i);
						String id = c.getString(ParameterCollections.TAG_EVENT_ID);
						String title = c.getString(ParameterCollections.TAG_EVENT_TITLE);
						String date = c.getString(ParameterCollections.TAG_EVENT_DATE);
						String time = c.getString(ParameterCollections.TAG_EVENT_TIME);
						String place = c.getString(ParameterCollections.TAG_EVENT_PLACE);
						String desc = c.getString(ParameterCollections.TAG_EVENT_DESC);
						desc = Jsoup.parse(desc).text();
						
						JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);

						if (jArray_Images.length() >= 0) {
							for (int j = 0; j < jArray_Images.length(); j++) {
								JSONObject d = jArray_Images.getJSONObject(j);
								cUrlImg = ParameterCollections.URL_IMG_ORIGINAL
										+ d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

							}

						}
						objects.add(new RowData_Event(id, title, place, desc, cUrlImg, date + " " + time ));
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

			if(cCode.equals("1")){
				Collections.reverse(objects);
				adapter = new CustomAdapter_Event(getActivity(), 0, objects);
				lv.setAdapter(adapter);
			}else{
				Toast.makeText(getActivity(), "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
			}


		}
	}

}
