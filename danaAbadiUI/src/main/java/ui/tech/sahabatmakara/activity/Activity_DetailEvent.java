package ui.tech.sahabatmakara.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ui.tech.sahabatmakara.dialog.Dialog_Progress;
import ui.tech.sahabatmakara.fragment.Fragment_Event_Alamat;
import ui.tech.sahabatmakara.fragment.Fragment_Event_Map;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;
import ui.tech.sahabatmakara.util.ServiceHandlerJSON;

public class Activity_DetailEvent extends ActionBarActivity {
	private ImageView btn_Back;
	private TextView tv_Title;
	private FragmentManager fm;
	private Button btn_Map, btn_Alamat;
	private BootstrapCircleThumbnail img_Event;
	private TextView tv_Tgl_Event, tv_CountDown, tv_Judul_Event, tv_Desc;

	int bedaHari, bedaJam, bedaMenit, bedaDetik;
	String ago;

	CountDownTimer mCountDownTimer;
	long mInitialTime;
	String tgl_event;
	Date time_event = null;
	Calendar cal_event = null;

	Calendar cal_now;
	Date event = null;
	Date now;
	long diff;
	long diff_menit;
	float diff_jam;

	String hari, jam, menit, detik;

	TextView tv_Countdown_Days, tv_Countdown_Hours, tv_Countdown_Minutes, 
	tv_Countdown_Seconds;
	
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail_event);
		initView();
		fm = getSupportFragmentManager();
		id = getIntent().getStringExtra("id");
		new AsyncTask_LoadDetailEvent().execute();
	}

	private void initView() {
		TextView tv_Title_ActionBar = (TextView)findViewById(R.id.tv_title_actionbar);
		
		btn_Back = (ImageView) findViewById(R.id.btn_actionbar_back);
//		tv_Title = (TextView) findViewById(R.id.tv_title);

		img_Event = (BootstrapCircleThumbnail) findViewById(R.id.img_event);
		tv_Tgl_Event = (TextView) findViewById(R.id.tv_tgl_event);
		tv_CountDown = (TextView) findViewById(R.id.tv_tgl_event_countdown);
		tv_Judul_Event = (TextView) findViewById(R.id.tv_title_event);
		tv_Desc = (TextView) findViewById(R.id.tv_desc_event);
		btn_Map = (Button) findViewById(R.id.btn_fragment_map);
		btn_Alamat = (Button) findViewById(R.id.btn_fragment_alamat);
		
		tv_Countdown_Days =(TextView)findViewById(R.id.tv_countdown_days);
		tv_Countdown_Hours =(TextView)findViewById(R.id.tv_countdown_hours);
		tv_Countdown_Minutes =(TextView)findViewById(R.id.tv_countdown_minutes);
		tv_Countdown_Seconds =(TextView)findViewById(R.id.tv_countdown_seconds);
		
		tv_Title_ActionBar.setText("ACARA");
		tv_Title_ActionBar.setTypeface(Font.setFontGaramond(getApplicationContext()));
		tv_CountDown.setTypeface(Font.setFontGaramond(getApplicationContext()));
		tv_Tgl_Event.setTypeface(Font.setFontGaramond(getApplicationContext()));
		tv_Judul_Event.setTypeface(Font.setFontGaramond(getApplicationContext()));
		tv_Desc.setTypeface(Font.setFontGaramond(getApplicationContext()));
		
		btn_Map.setTypeface(Font.setLato(getApplicationContext()));
		btn_Alamat.setTypeface(Font.setLato(getApplicationContext()));
		
		tv_Countdown_Days.setTypeface(Font.setLato(getApplicationContext()));
		tv_Countdown_Hours.setTypeface(Font.setLato(getApplicationContext()));
		tv_Countdown_Minutes.setTypeface(Font.setLato(getApplicationContext()));
		tv_Countdown_Seconds.setTypeface(Font.setLato(getApplicationContext()));
		
		Animation animate_FromLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_from_left);
		Animation animate_Alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_tv_in_alpha);
		Animation animate_FromBot = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_tv_in_from_bot);
		Animation animate_BounceFromTop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_in);
		
		tv_Judul_Event.startAnimation(animate_Alpha);
		tv_Desc.startAnimation(animate_FromBot);

		btn_Back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		
	}

	private class AsyncTask_LoadDetailEvent extends AsyncTask<Void, Void, Void> {
		String cCode, cTitle, cDate, cTime, cLat,cLong, cDesc, cPlace, cUrlImg;
		Dialog_Progress pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog =new Dialog_Progress();
			pDialog.show(getSupportFragmentManager(),"");
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			try {
				JSONObject jObj = sh.getEventDetail(id);

				cCode = jObj.getString(ParameterCollections.TAG_JSON_CODE);

				if (cCode.equals("1")) {
					JSONObject jData = jObj.getJSONObject(ParameterCollections.TAG_DATA);

					cTitle = jData.getString(ParameterCollections.TAG_EVENT_TITLE);
					cDate = jData.getString(ParameterCollections.TAG_EVENT_DATE);
					cTime = jData.getString(ParameterCollections.TAG_EVENT_TIME);
					cLat = jData.getString(ParameterCollections.TAG_EVENT_LAT);
					cLong = jData.getString(ParameterCollections.TAG_EVENT_LONG);
					cDesc= jData.getString(ParameterCollections.TAG_EVENT_DESC);
					cPlace= jData.getString(ParameterCollections.TAG_EVENT_PLACE);

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

			pDialog.dismiss();

			if (cCode.equals("1")) {

				Target target = new Target() {

					@Override
					public void onBitmapLoaded(Bitmap arg0, LoadedFrom arg1) {
						// TODO Auto-generated method stub
						img_Event.setImage(arg0);
					}

					@Override
					public void onBitmapFailed() {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Failed to Load Image", Toast.LENGTH_SHORT).show();
					}
				};

				Picasso.with(getApplicationContext()).load(cUrlImg).into(target);

				tv_Tgl_Event.setText(cDate);

				String date_nextevent = cDate + " " + cTime;
				tgl_event = date_nextevent;
				try {
					time_event = new SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss", Locale.US).parse(tgl_event);
					Calendar c = Calendar.getInstance();
					now = c.getTime();

					DateTime dt_event = new DateTime(time_event);
					DateTime dt_now = new DateTime(now);

					bedaHari = Days.daysBetween(dt_now, dt_event).getDays();
					bedaJam = Hours.hoursBetween(dt_now, dt_event).getHours() % 24;
					bedaMenit = Minutes.minutesBetween(dt_now, dt_event).getMinutes() % 60;
					bedaDetik = Seconds.secondsBetween(dt_now, dt_event).getSeconds() % 60;

					mInitialTime = DateUtils.DAY_IN_MILLIS * bedaHari + DateUtils.HOUR_IN_MILLIS * bedaJam
							+ DateUtils.MINUTE_IN_MILLIS * bedaMenit + DateUtils.SECOND_IN_MILLIS * bedaDetik;
				} catch (ParseException e) {
					e.printStackTrace();
				}

				mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
					StringBuilder time = new StringBuilder();
					StringBuilder time_hours = new StringBuilder();
					StringBuilder time_minutes = new StringBuilder();
					StringBuilder time_seconds = new StringBuilder();

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						time.setLength(0);
						time_hours.setLength(0);
						time_minutes.setLength(0);
						time_seconds.setLength(0);

						if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
							long count = millisUntilFinished / DateUtils.DAY_IN_MILLIS;
							if (count > 1) {
								time.append(count).append(" Days ");
								tv_Countdown_Days.setText(time.toString());
							} else {
								time.append(count).append(" Day ");
								tv_Countdown_Days.setText(time.toString());
							}


							millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
						}

						time.append(DateUtils.formatElapsedTime(Math.round(millisUntilFinished / 1000d)));
						tv_CountDown.setText(time.toString());


						long count_Hours = millisUntilFinished / DateUtils.HOUR_IN_MILLIS;
						time_hours.append(count_Hours);

						millisUntilFinished %= DateUtils.HOUR_IN_MILLIS;

						long count_Minutes = millisUntilFinished / DateUtils.MINUTE_IN_MILLIS;
						time_minutes.append(count_Minutes);

						millisUntilFinished %= DateUtils.MINUTE_IN_MILLIS;

						long count_Seconds = millisUntilFinished / DateUtils.SECOND_IN_MILLIS;
						time_seconds.append(count_Seconds);
						millisUntilFinished %= DateUtils.SECOND_IN_MILLIS;

						tv_Countdown_Hours.setText(String.valueOf(count_Hours));
						tv_Countdown_Minutes.setText(String.valueOf(count_Minutes));
						tv_Countdown_Seconds.setText(String.valueOf(count_Seconds));

					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub

					}
				}.start();

				// tv_CountDown.setText("36 : 10 : 07 : 12");
				tv_Judul_Event.setText(cTitle);
				tv_Desc.setText(Jsoup.parse(cDesc).text());

				fm.beginTransaction().replace(R.id.frame_container, new Fragment_Event_Map().create(cLat, cLong)).commit();

				btn_Map.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						fm.beginTransaction().replace(R.id.frame_container, new Fragment_Event_Map().create(cLat, cLong)).commit();
					}
				});

				btn_Alamat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						fm.beginTransaction().replace(R.id.frame_container, new Fragment_Event_Alamat().create(cPlace)).commit();
					}
				});
			}else{
				Toast.makeText(getApplicationContext(), "Terjadi Kesalahan silahkan dicoba lagi.",Toast.LENGTH_SHORT).show();

		}
	}
	}
}
