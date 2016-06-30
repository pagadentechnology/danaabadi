package ui.tech.sahabatmakara.activity;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.squareup.picasso.Picasso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Activity_DetailNews extends ActionBarActivity{
	private ImageView img, img_Back;
	private TextView tv_Title, tv_Desc, tv_TitleActionBar;
	private int extra_position;
	private String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail_news_program);
		extra_position = getIntent().getIntExtra("position", 0);
		id = getIntent().getStringExtra("id");
		initView();
		
		new AsyncTask_LoadData().execute();
	}
	
	private void initView(){
		TextView tv_Title_ActionBar = (TextView)findViewById(R.id.tv_title_actionbar);
		tv_Title_ActionBar.setText("BERITA");
		tv_Title_ActionBar.setTypeface(Font.setFontGaramond(getApplicationContext()));
		
		img = (ImageView)findViewById(R.id.img_bg);
		img_Back = (ImageView)findViewById(R.id.btn_actionbar_back);
		img_Back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_Title = (TextView)findViewById(R.id.tv_title);
		tv_Desc= (TextView)findViewById(R.id.tv_desc);
		tv_Title.setTypeface(Font.setFontGaramond(getApplicationContext()));
		tv_Desc.setTypeface(Font.setFontGaramond(getApplicationContext()));
		
		Animation animate_FromLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_from_left);
		Animation animate_Alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_tv_in_alpha);
		Animation animate_FromBot = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_tv_in_from_bot);
		Animation animate_BounceFromTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_in);
		tv_Title.startAnimation(animate_Alpha);
		tv_Desc.startAnimation(animate_FromBot);
	}

	private class AsyncTask_LoadData extends AsyncTask<Void, Void, Void>{
		String cCode, cTitle, cDate, cCreator, cDesc, cUrlImg;
		Dialog_Progress pDialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog =new Dialog_Progress();
			pDialog.show(getSupportFragmentManager(), "");
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			try {
				JSONObject jObj = sh.getNewsDetail(id);

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONObject jData = jObj.getJSONObject(ParameterCollections.TAG_DATA);

					cTitle = jData.getString(ParameterCollections.TAG_NEWS_TITLE);
					cDate = jData.getString(ParameterCollections.TAG_NEWS_DATE);
					cCreator= jData.getString(ParameterCollections.TAG_NEWS_CREATOR);
					cDesc= jData.getString(ParameterCollections.TAG_NEWS_DESC);
					
					JSONArray jArray_Images = jData.getJSONArray(ParameterCollections.TAG_ARRAY_IMAGES);

					if (jArray_Images.length() >= 0) {
						for (int i = 0; i < jArray_Images.length(); i++) {
							JSONObject c = jArray_Images.getJSONObject(i);
							cUrlImg = ParameterCollections.URL_IMG_ORIGINAL
									+ c.getString(ParameterCollections.TAG_ARRAY_IMAGES_NAMA);

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
//			ArrayList<RowData_News> datanya = new ArrayList<RowData_News>();
//			datanya.add(new RowData_News("", "Angkatan Sumpah Lulusan Ners FIK UI", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e6e4b5133c9.jpg", "Saturday, 01-09-2015"));
//			datanya.add(new RowData_News("", "Alumni FIK UI Bersatu", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e6e44adca28.jpg", "Monday, 30-04-2015"));
//			datanya.add(new RowData_News("", "Dies Natalis Ke-54 Tahun FMIPA UI", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e6e575d49a4.jpg", "Tuesday, 16-12-2014"));
//			datanya.add(new RowData_News("", "Angkatan Sumpah Lulusan Ners FIK UI", "", "http://128.199.176.5/UI/admin/files/thumbnail/55e6e4b5133c9.jpg", "Saturday, 01-09-2015"));

			pDialog.dismiss();

			if (cCode.equals("1")) {
				tv_Title.setText(cTitle);
				tv_Desc.setText(Jsoup.parse(cDesc).text());

				Picasso.with(getApplicationContext()).load(cUrlImg).into(img);
			}else{
				Toast.makeText(getApplicationContext(), "Terjadi Kesalahan silahkan dicoba lagi.",Toast.LENGTH_SHORT).show();
			}

			
		}
	}
}
