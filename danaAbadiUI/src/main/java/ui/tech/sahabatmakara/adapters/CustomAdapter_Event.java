package ui.tech.sahabatmakara.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import ui.tech.sahabatmakara.activity.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ui.tech.sahabatmakara.activity.Activity_DetailEvent;
import ui.tech.sahabatmakara.entities.RowData_Event;
import ui.tech.sahabatmakara.util.Font;

public class CustomAdapter_Event extends ArrayAdapter<RowData_Event> {
	private Context context;
	private List<RowData_Event> objects;

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

	public CustomAdapter_Event(Context context, int resource, List<RowData_Event> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View v = mInflater.inflate(R.layout.item_event, null);

		final RowData_Event item = objects.get(position);

		View wrapper = (View) v.findViewById(R.id.wrapper);

		TextView tv_Title = (TextView) v.findViewById(R.id.tv_title);
		TextView tv_Tgl = (TextView) v.findViewById(R.id.tv_tgl);
		TextView tv_Alamat = (TextView) v.findViewById(R.id.tv_alamat);
		TextView tv_Desc = (TextView) v.findViewById(R.id.tv_desc);
		TextView tv_LabelCountdown = (TextView) v.findViewById(R.id.tv_label_countdown);
		final TextView tv_Countdown = (TextView) v.findViewById(R.id.tv_tgl_event_countdown);

		tv_Title.setTypeface(Font.setFontGaramond(context));
		tv_Tgl.setTypeface(Font.setFontGaramond(context));
		tv_Alamat.setTypeface(Font.setFontGaramond(context));
		tv_Desc.setTypeface(Font.setFontGaramond(context));
		tv_Countdown.setTypeface(Font.setFontGaramond(context));
		tv_LabelCountdown.setTypeface(Font.setFontGaramond(context));
		
		ImageView img = (ImageView) v.findViewById(R.id.img);
		Button btn = (Button) v.findViewById(R.id.btn_more);

		Animation animate_TV_Title = AnimationUtils.loadAnimation(context, R.anim.anim_tv_in_alpha);
		Animation animate_TV = AnimationUtils.loadAnimation(context, R.anim.anim_tv_in_from_bot);
		Animation animate_Img = AnimationUtils.loadAnimation(context, R.anim.anim_in);

		tv_Title.startAnimation(animate_TV_Title);
		tv_Desc.startAnimation(animate_TV);
		tv_Alamat.startAnimation(animate_TV);

		tv_Title.setText(item.title);
		tv_Tgl.setText(item.date);
		tv_Alamat.setText(item.alamat);
		tv_Desc.setText(item.desc);
		Picasso.with(context).load(item.url_img).into(img);

		wrapper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, Activity_DetailEvent.class);
				intent.putExtra("id", item.id);
				context.startActivity(intent);
			}
		});

		String date_nextevent = item.date;
		tgl_event = date_nextevent;

		try {
			Date time_event_temp = new SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss", Locale.US).parse(tgl_event);
			Calendar c_temp = Calendar.getInstance();
			Date now_temp = c_temp.getTime();

			DateTime dt_event_temp = new DateTime(time_event_temp);
			DateTime dt_now_temp = new DateTime(now_temp);

			//jika event telah lewat
			if(dt_event_temp.isBefore(dt_now_temp)){
				tv_LabelCountdown.setText("Acara sudah selesai");
			}else{
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

				mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
					StringBuilder time = new StringBuilder();

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						time.setLength(0);
						if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
							long count = millisUntilFinished / DateUtils.DAY_IN_MILLIS;
							if (count > 1) {
								time.append(count).append(" Hari ");
							} else {
								time.append(count).append(" Hari ");
							}
							millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
						}

						time.append(DateUtils.formatElapsedTime(Math.round(millisUntilFinished / 1000d)));
						tv_Countdown.setText(time.toString());
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub

					}
				}.start();

			}

		}catch (ParseException e) {
			e.printStackTrace();
		}catch (Exception e){

		}



		return v;
	}

}
