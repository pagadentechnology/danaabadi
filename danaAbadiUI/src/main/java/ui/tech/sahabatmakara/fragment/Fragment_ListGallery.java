package ui.tech.sahabatmakara.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ui.tech.sahabatmakara.activity.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ui.tech.sahabatmakara.adapters.CustomAdapter_ListGallery;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.entities.RowData_ListGallery;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

import android.widget.ListView;
import android.widget.Toast;

public class Fragment_ListGallery extends Fragment{
	ListView lv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_listview, null);
		
//		objects = new ArrayList<RowData_ListGallery>();
//		objects.add(new RowData_ListGallery("", "Pelatihan Assesmen SAR AUN 2013", "http://128.199.176.5/UI/admin/files/thumbnail/55e56aa1b0dec.jpg"));
//		objects.add(new RowData_ListGallery("", "AUN-QA Training Workshop", "http://128.199.176.5/UI/admin/files/thumbnail/55e56aed31fe9.jpg"));
		
//		CustomAdapter_ListGallery adapter = new CustomAdapter_ListGallery(getActivity(), 0, objects, getFragmentManager());
		
		lv = (ListView)v.findViewById(R.id.lv);
//		lv.setAdapter(adapter);
		
		new AsyncTask_AllGallery().execute();
		return v;
	}
	
	private class AsyncTask_AllGallery extends AsyncTask<Void, Void, Void> {
		String cCode, cUrlImg, cImgName;
		private ArrayList<RowData_ListGallery> objects;
	private Dialog_Progress pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			pDialog = new Dialog_Progress();
			pDialog.show(getFragmentManager(),"");
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			try {
				JSONObject jObj = sh.getAllGalleries();

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONArray jArray = jObj.getJSONArray(ParameterCollections.TAG_DATA);

					objects = new ArrayList<RowData_ListGallery>();

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject c = jArray.getJSONObject(i);
						String id = c.getString(ParameterCollections.TAG_GALLERY_ID);
						String title = c.getString(ParameterCollections.TAG_GALLERY_TITLE);						
						
						JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);

						if (jArray_Images.length() >= 0) {
							for (int j = 0; j < jArray_Images.length(); j++) {
								JSONObject d = jArray_Images.getJSONObject(j);
								cUrlImg = ParameterCollections.URL_IMG_THUMBNAIL
										+ d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);
								cImgName = d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

							}

						}
//						objects.add(new RowData_Event(id, title, place, desc, cUrlImg, date + " " + time ));
						objects.add(new RowData_ListGallery(id, title, cUrlImg, cImgName));
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

			if (cCode.equals("1")) {
				CustomAdapter_ListGallery adapter = new CustomAdapter_ListGallery(getActivity(), 0, objects, getFragmentManager());
				lv.setAdapter(adapter);
			}else{
				Toast.makeText(getActivity(), "Terjadi kesalahan, Cek Koneksi Anda.", Toast.LENGTH_LONG).show();
			}


		}
	}

}
