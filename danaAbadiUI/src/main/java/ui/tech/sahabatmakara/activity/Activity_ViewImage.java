package ui.tech.sahabatmakara.activity;

import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ui.tech.sahabatmakara.util.ParameterCollections;

public class Activity_ViewImage extends Activity{
	ImageView img;
	private final BaseSpringSystem mSpringSystem = SpringSystem.create();
	private final ExampleSpringListener mSpringListener = new ExampleSpringListener();
	private Spring mScaleSpring;
	String url,name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
//		img = new ImageView(getApplicationContext());
//		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		img.setScaleType(ScaleType.CENTER_CROP);
//		img.setPadding(30, 30, 30, 30);
//		setContentView(img);
		url = getIntent().getStringExtra("url_img");
		name = getIntent().getStringExtra("name_img");

		url = url.replace("thumbnail", "original");
		
		WebView wv = new WebView(getApplicationContext());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		wv.setLayoutParams(params);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setLoadWithOverviewMode(true);
		wv.getSettings().setUseWideViewPort(true);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setDisplayZoomControls(false);
		wv.loadData(ParameterCollections.URL_01 + url + ParameterCollections.URL_02, "text/html", "UTF-8");
		setContentView(wv);

		wv.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(getApplicationContext(),"Mengunduh...", Toast.LENGTH_LONG).show();
				Target target = new Target(){

					@Override
					public void onBitmapFailed() {
						Toast.makeText(getApplicationContext(),"Unduh gagal", Toast.LENGTH_LONG).show();
					}

					@Override
					public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
						new AsyncTask<Void, Void, Void>(){
							boolean berhasil =false;

							@Override
							protected Void doInBackground(Void... params) {
								FileOutputStream fos = null;

								String root = Environment.getExternalStorageDirectory().toString();
								File myDir = new File(root + "/SahabatMakara/");
								myDir.mkdirs();

								File file = new File(myDir +"/" +name);
								if(file.exists())file.delete();

								try{
									file.createNewFile();

									fos = new FileOutputStream(file);
									bitmap.compress(Bitmap.CompressFormat.JPEG, 80,fos);
									fos.close();
									berhasil = true;
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
								return null;
							}

							@Override
								protected void onPostExecute(Void aVoid) {
									super.onPostExecute(aVoid);
								if(berhasil){
									Toast.makeText(getApplicationContext(),"Unduh berhasil", Toast.LENGTH_LONG).show();
								}else{
									Toast.makeText(getApplicationContext(),"Unduh gagal", Toast.LENGTH_LONG).show();
								}
							}
						}.execute();


					}
				};

				Picasso.with(getApplicationContext()).load(url).into(target);
				return true;
			}
		});
		
//		Picasso.with(getApplicationContext()).load(url).into(img);
		
		mScaleSpring = mSpringSystem.createSpring();
//		img.setOnTouchListener(new View.OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				return true;
//			}
//		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mScaleSpring.addListener(mSpringListener);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 mScaleSpring.removeListener(mSpringListener);
	}
	
	private class ExampleSpringListener extends SimpleSpringListener{
		
		@Override
		public void onSpringUpdate(Spring spring) {
			// TODO Auto-generated method stub
			super.onSpringUpdate(spring);
//			float mappedValue = (float)SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0,1,1,0.5);
//			img.setScaleX(mappedValue);
//			img.setScaleY(mappedValue);
			
			float value = (float) spring.getCurrentValue();
		    float scale = 1f - (value * 0.5f);
		    img.setScaleX(scale);
			img.setScaleY(scale);
		}
	}

}
