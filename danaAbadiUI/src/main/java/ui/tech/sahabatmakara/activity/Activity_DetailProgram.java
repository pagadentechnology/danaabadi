package ui.tech.sahabatmakara.activity;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Activity_DetailProgram extends ActionBarActivity {
	private ImageView img, img_Back;
	private TextView tv_Title, tv_Desc, tv_TitleActionBar;
	private TextView tv_Goal, tv_Raised, tv_Donatur;
	private int extra_position;
	private String id;
	Button btn_Donate;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.layout_detail_news_program);
		setContentView(R.layout.layout_detail_program);

		sp = getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);

		extra_position = getIntent().getIntExtra("position", 0);
		id = getIntent().getStringExtra("id");
		sp.edit().putString(ParameterCollections.SH_PROGRAM_ID, id).commit();

		initView();

		new AsyncTask_LoadData().execute();
	}

	private void initView() {
		TextView tv_Title_ActionBar = (TextView) findViewById(R.id.tv_title_actionbar);

		img = (ImageView) findViewById(R.id.img);
		img_Back = (ImageView) findViewById(R.id.btn_actionbar_back);
		img_Back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_Title = (TextView) findViewById(R.id.tv_title);
		tv_Desc = (TextView) findViewById(R.id.tv_desc);
		tv_Goal = (TextView) findViewById(R.id.tv_program_goal);
		tv_Raised = (TextView) findViewById(R.id.tv_program_raised);
		tv_Donatur = (TextView) findViewById(R.id.tv_program_donatur);

		tv_Title_ActionBar.setTypeface(Font.setFontGaramond(getApplicationContext()));
		tv_Title_ActionBar.setText("PROGRAM");
		btn_Donate = (Button) findViewById(R.id.btn_donate);

		tv_Title.setTypeface(Font.setFontGaramond(getApplicationContext()));
		tv_Desc.setTypeface(Font.setFontGaramond(getApplicationContext()));
		btn_Donate.setTypeface(Font.setLato(getApplicationContext()));

		Animation animate_FromLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_from_left);
		Animation animate_Alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_tv_in_alpha);
		Animation animate_FromBot = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_tv_in_from_bot);
		Animation animate_BounceFromTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_in);
		tv_Title.startAnimation(animate_Alpha);
		tv_Desc.startAnimation(animate_FromBot);

		
	}

	private class AsyncTask_LoadData extends AsyncTask<Void, Void, Void> {
		String cCode, cTitle, cGoal, cRaised, cDonatur, cDesc, cUrlImg;
		private Dialog_Progress pg;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pg = new Dialog_Progress();
			pg.show(getSupportFragmentManager(),"");
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			try {
				JSONObject jObj = sh.getProgramDetail(id);

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONObject jData = jObj.getJSONObject(ParameterCollections.TAG_DATA);

					cTitle = jData.getString(ParameterCollections.TAG_PROGRAM_TITLE);
					cGoal = jData.getString(ParameterCollections.TAG_PROGRAM_GOAL);
					cRaised = jData.getString(ParameterCollections.TAG_PROGRAM_RAISED);
					cDonatur = jData.getString(ParameterCollections.TAG_PROGRAM_DONATOR);
					cDesc = jData.getString(ParameterCollections.TAG_PROGRAM_DESC);

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

			pg.dismiss();

			if (cCode.equals("1")) {
				tv_Title.setText(cTitle);
				tv_Desc.setText(Jsoup.parse(cDesc).text());

				NumberFormat format = NumberFormat.getCurrencyInstance();
				String nominal_collected = format.format(new BigDecimal(cRaised));
				nominal_collected = nominal_collected.replace("$", "Rp ");
				nominal_collected = nominal_collected.replace(".00", "");
				nominal_collected = nominal_collected.replace(",", ".");

				String nominal_target = format.format(new BigDecimal(cGoal));
				nominal_target = nominal_target.replace("$", "Rp ");
				nominal_target = nominal_target.replace(".00", "");
				nominal_target = nominal_target.replace(",", ".");

				tv_Goal.setText(nominal_target);
				tv_Raised.setText(nominal_collected);
				tv_Donatur.setText(cDonatur);

				Picasso.with(getApplicationContext()).load(cUrlImg).into(img);

				btn_Donate.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent load = new Intent(getApplicationContext(), Activity_Donate_Program.class);
						sp.edit().putString(ParameterCollections.SH_NAMA_PROGRAM, cTitle).commit();
						startActivity(load);
					}
				});
			}else {
				Toast.makeText(getApplicationContext(), "Terjadi Kesalahan silahkan dicoba lagi.", Toast.LENGTH_SHORT).show();

			}
		}
	}
}
