package ui.tech.sahabatmakara.fragment;

import java.util.ArrayList;

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

import ui.tech.sahabatmakara.adapters.CustomAdapter_Program;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.entities.RowData_Program;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;
import android.widget.ListView;

public class Fragment_Program extends Fragment {
	private ListView lv;
	private CustomAdapter_Program adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_listview, null);
		lv = (ListView) v.findViewById(R.id.lv);

		// ColorDrawable color_divider = new ColorDrawable(R.color.transparent);
		// lv.setDivider(color_divider);
		lv.setDividerHeight(1);

//		ArrayList<RowData_Program> datanya = new ArrayList<RowData_Program>();
//		datanya.add(new RowData_Program("", "Beasiswa Fakultas Teknik", "Rp 17.000.000/~", "", "",
//				"Beasiswa untuk para mahasiswa berprestasi yang kurang mampu",
//				"http://128.199.176.5/UI/admin/files/original/55e6c977b6d8d.jpg"));
//		datanya.add(new RowData_Program("", "Perpustakaan Keliling", "Rp 3.000.000/Rp 800.000.000", "", "",
//				"Perpustakaan keliling untuk anak anak",
//				"http://128.199.176.5/UI/admin/files/original/55d5a3f122e4a.jpg"));
//		datanya.add(new RowData_Program("", "Pembangunan Bangunan Baru", "Rp 90.500.000/Rp 780.000.000", "", "",
//				"Bangunan baru untuk perkuliahan", "http://128.199.176.5/UI/admin/files/original/55d5a3e18ce07.png"));
//
//		adapter = new CustomAdapter_Program(getActivity(), 0, datanya);
//		lv.setAdapter(adapter);


//		new AsyncTask_AllProgram().execute();
		new AsyncTask_AllProgram().execute();

		return v;
	}

	private class AsyncTask_AllProgram extends AsyncTask<Void, Void, Void> {
		String cCode;
		private ArrayList<RowData_Program> objects;
		private Dialog_Progress pDialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			pDialog = new Dialog_Progress();
			pDialog.show(getFragmentManager(), "");

            objects = new ArrayList<RowData_Program>();
		}

        @Override
        protected Void doInBackground(Void... voids) {

			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			try {
				JSONObject jObj = sh.getAllProgram();

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONArray jArray = jObj.getJSONArray(ParameterCollections.TAG_DATA);

					for(int i=0; i< jArray.length(); i++){
						JSONObject c = jArray.getJSONObject(i);
						String id = c.getString(ParameterCollections.TAG_PROGRAM_ID);
						String title = c.getString(ParameterCollections.TAG_PROGRAM_TITLE);
						String goal = c.getString(ParameterCollections.TAG_PROGRAM_GOAL);
						String raised = c.getString(ParameterCollections.TAG_PROGRAM_RAISED);
						String donatur = c.getString(ParameterCollections.TAG_PROGRAM_DONATOR);
						String desc = c.getString(ParameterCollections.TAG_PROGRAM_DESC);
						desc = Jsoup.parse(desc).text();

						String url = "";
						JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);

						if (jArray_Images.length() >= 0) {
							for (int j = 0; j < jArray_Images.length(); j++) {
								JSONObject d = jArray_Images.getJSONObject(j);
								url = ParameterCollections.URL_IMG_ORIGINAL
										+ d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

							}
							objects.add(new RowData_Program(id, title, raised, goal, "10", desc, url));

						}else{
							objects.add(new RowData_Program(id, title, raised, goal, "10", desc, "http://128.199.176.5/UI/admin/files/original/55e6c977b6d8d.jpg"));

						}

//						switch (i) {
//							case 0:
//								objects.add(new RowData_Program(id, title, raised, goal, "10", desc, "http://128.199.176.5/UI/admin/files/original/55e6c977b6d8d.jpg"));
//								break;
//							case 1:
//								objects.add(new RowData_Program(id, title, raised, goal, "10", desc, "http://128.199.176.5/UI/admin/files/original/55d5a3f122e4a.jpg"));
//								break;
//							case 2:
//								objects.add(new RowData_Program(id, title, raised, goal, "10", desc, "http://128.199.176.5/UI/admin/files/original/55d5a3e18ce07.png"));
//								break;
//
//							default:
//								break;
//						}

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
			adapter = new CustomAdapter_Program(getActivity(), 0, objects);
			lv.setAdapter(adapter);
		}
	}
}
