package ui.tech.sahabatmakara.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ui.tech.sahabatmakara.activity.Activity_ViewImage;
import ui.tech.sahabatmakara.adapters.RecyclerAdapter_Gallery;
import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.entities.RowData_GalleryImage;
import ui.tech.sahabatmakara.helper.GalleryViewCallback;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Fragment_Gallery extends Fragment  {
	// private RecyclerAdapter_Gallery adapter;
	RecyclerView re_view;
	private String id, name_img;
	private ArrayList<RowData_GalleryImage> data;

	public static Fragment_Gallery create(String gallery_id, String nama_image) {
		Fragment_Gallery fragment = new Fragment_Gallery();
		Bundle b = new Bundle();
		b.putString(ParameterCollections.ARGS_ID, gallery_id);
		b.putString(ParameterCollections.ARGS_NAMA_IMAGE, nama_image);
		fragment.setArguments(b);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new AsyncTask_DetailListGallery().execute();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		id = getArguments().getString(ParameterCollections.ARGS_ID);
		name_img = getArguments().getString(ParameterCollections.ARGS_NAMA_IMAGE);

		re_view = new RecyclerView(getActivity());

//		data = new ArrayList<RowData_GalleryImage>();
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56a9140946.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56aa1b0dec.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56ab86d8f8.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56ac2dbd05.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56aca8d8d5.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56ae2db874.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56aed31fe9.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56afc90729.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56b0b0f415.jpg"));
//		data.add(new RowData_GalleryImage("", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e56b18684f1.jpg"));

		

		return re_view;
	}

	private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
		private int space;

		public SpacesItemDecoration(int space) {
			// TODO Auto-generated constructor stub
			this.space = space;
		}

		@Override
		public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
			// TODO Auto-generated method stub
			outRect.left = space;
			outRect.right = space;
			outRect.bottom = space;
			outRect.top = space;
			// if(parent.getChildPosition(view) == 0) outRect.top = space;
		}
	}

	private class AsyncTask_DetailListGallery extends AsyncTask<Void, Void, Void> implements GalleryViewCallback{
		String cCode, cUrlImg , cImgName;
		private ArrayList<RowData_GalleryImage> objects;
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
				JSONObject jObj = sh.getGalleryDetail(id);

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					objects = new ArrayList<RowData_GalleryImage>();

					JSONObject c = jObj.getJSONObject(ParameterCollections.TAG_DATA);
					String id = c.getString(ParameterCollections.TAG_GALLERY_ID);
					String title = c.getString(ParameterCollections.TAG_GALLERY_TITLE);

					JSONArray jArray_Images = c.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);

					if (jArray_Images.length() >= 0) {
						for (int j = 0; j < jArray_Images.length(); j++) {
							JSONObject d = jArray_Images.getJSONObject(j);
							cUrlImg = ParameterCollections.URL_IMG_THUMBNAIL
									+ d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);
							cImgName = d.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);
							objects.add(new RowData_GalleryImage(id, title, cUrlImg, cImgName));

						}

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
			RecyclerAdapter_Gallery adapter = new RecyclerAdapter_Gallery(objects, getActivity());

			GridLayoutManager grid_manager = new GridLayoutManager(getActivity(), 3);
			StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, 1);
			re_view.setAdapter(adapter);
			re_view.setLayoutManager(grid_manager);
			// re_view.setLayoutManager(manager);
			re_view.addItemDecoration(new SpacesItemDecoration(0));

			adapter.setGalleryCallback(this);
		}
		
		@Override
		public void onGalleryItemClicked(String url, String name) {
			// TODO Auto-generated method stub
			Intent load = new Intent(getActivity(), Activity_ViewImage.class);
			load.putExtra("url_img", url);
			load.putExtra("name_img", name);
			startActivity(load);
			Log.e("CLICK di VIEW HOLDER", "GALLERY");
		}

	}

	
}
