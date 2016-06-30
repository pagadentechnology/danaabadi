package ui.tech.sahabatmakara.adapters;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import ui.tech.sahabatmakara.activity.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso.LoadedFrom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import ui.tech.sahabatmakara.activity.Activity_DetailProgram;
import ui.tech.sahabatmakara.entities.RowData_Program;
import ui.tech.sahabatmakara.helper.NavigationDrawerCallbacks;
import ui.tech.sahabatmakara.util.Font;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter_Program extends ArrayAdapter<RowData_Program> implements OnItemClickListener, NavigationDrawerCallbacks{
	private List<RowData_Program> objects;
	private Context context;
	private ImageView img;
	private View v;
//	private NavigationDrawerCallbacks mCallbacks;
	
	private final BaseSpringSystem mSpringSystem = SpringSystem.create();
	
	private Spring mScaleSpring;
		
	private class ExampleSpringListener extends SimpleSpringListener{
		
		@Override
		public void onSpringUpdate(Spring spring) {
			// TODO Auto-generated method stub
			super.onSpringUpdate(spring);
			float mappedValue = (float)SpringUtil.mapValueFromRangeToRange(spring.getCurrentValue(), 0,1,1,0.5);
//			img.setScaleX(mappedValue);
//			img.setScaleY(mappedValue);
			
//			v.setScaleX(mappedValue);
//			v.setScaleY(mappedValue);
//			float value = (float) spring.getCurrentValue();
//		    float scale = 1f - (value * 0.5f);
//		    img.setScaleX(scale);
//			img.setScaleY(scale);
		}
	}
	
	
	
	public CustomAdapter_Program(Context context, int resource, List<RowData_Program> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.objects = objects;
		this.context = context;
		
		mScaleSpring = mSpringSystem.createSpring();
	}
	
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = mInflater.inflate(R.layout.item_program, null);
		
		final RowData_Program item = objects.get(position);
		
		img = (ImageView)v.findViewById(R.id.img);
		
//		ExampleSpringListener mSpringListener = new ExampleSpringListener();		
//		mScaleSpring.addListener(mSpringListener);
		
//		v.setOnTouchListener(new View.OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				// TODO Auto-generated method stub
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//		            // When pressed start solving the spring to 1.
//		            mScaleSpring.setEndValue(1);
//		            break;
//		          case MotionEvent.ACTION_UP:
//		          case MotionEvent.ACTION_CANCEL:
//		            // When released start solving the spring to 0.
//		            mScaleSpring.setEndValue(0);
//		            break;
//				}
//				return true;
//			}
//		});
		
		final BootstrapCircleThumbnail img2 = (BootstrapCircleThumbnail)v.findViewById(R.id.img_2);
		
		Animation animate = AnimationUtils.loadAnimation(context, R.anim.anim_in);
		img2.startAnimation(animate);
		
		Target target = new Target() {
			
			@Override
			public void onBitmapLoaded(Bitmap arg0, LoadedFrom arg1) {
				// TODO Auto-generated method stub
				img2.setImage(arg0);
			}
			
			@Override
			public void onBitmapFailed() {
				// TODO Auto-generated method stub
				
			}
		};
		
		Picasso.with(context).load(item.url_img).into(target);
		
		TextView tv_donasi = (TextView)v.findViewById(R.id.tv_program_donasi);
		TextView tv_title = (TextView)v.findViewById(R.id.tv_program_title);
		TextView tv_desc = (TextView)v.findViewById(R.id.tv_program_desc);

		TextView tv_target = (TextView)v.findViewById(R.id.tv_target);
		TextView tv_achivement = (TextView)v.findViewById(R.id.tv_achivement);

		TextView tv_label_target = (TextView)v.findViewById(R.id.tv_label_target);
		TextView tv_label_achivement = (TextView)v.findViewById(R.id.tv_label_achivement);

		
		tv_donasi.setTypeface(Font.setFontGaramond(context));
		tv_title.setTypeface(Font.setFontGaramond(context));
		tv_desc .setTypeface(Font.setFontGaramond(context));
		tv_target.setTypeface(Font.setFontGaramond(context));
		tv_achivement .setTypeface(Font.setFontGaramond(context));
		tv_label_target.setTypeface(Font.setFontGaramond(context));
		tv_label_achivement.setTypeface(Font.setFontGaramond(context));

		Animation animate_TV_Title = AnimationUtils.loadAnimation(context, R.anim.anim_tv_in_alpha);
		tv_title.startAnimation(animate_TV_Title);
		tv_donasi.startAnimation(animate_TV_Title);
		
		Animation animate_TV = AnimationUtils.loadAnimation(context, R.anim.anim_tv_in_from_bot);
		tv_desc.startAnimation(animate_TV);

		NumberFormat format = NumberFormat.getCurrencyInstance();
		String nominal_collected = format.format(new BigDecimal(item.collected));
		nominal_collected = nominal_collected.replace("$", "Rp ");
		nominal_collected = nominal_collected.replace(".00", "");
		nominal_collected = nominal_collected.replace(",", ".");

		String nominal_target = format.format(new BigDecimal(item.target));
		if(item.target.equals("0")){
			nominal_target = "~";
		}else{

			nominal_target = nominal_target.replace("$", "Rp ");
			nominal_target = nominal_target.replace(".00", "");
			nominal_target = nominal_target.replace(",", ".");
		}


		tv_donasi.setText(nominal_collected + " / " + nominal_target);

		tv_achivement.setText(nominal_collected);
		tv_target.setText(nominal_target);

		tv_title.setText(item.program_title);
		tv_desc.setText(item.desc);
		
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, Activity_DetailProgram.class);
				intent.putExtra("id", item.id);
				intent.putExtra("position", position);
				context.startActivity(intent);
			}
		});
		
		return v;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "KLIK", Toast.LENGTH_LONG).show();
	}



	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		
	}
	

}
