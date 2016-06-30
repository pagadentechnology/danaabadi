package ui.tech.sahabatmakara.adapters;

import java.util.List;

import ui.tech.sahabatmakara.activity.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ui.tech.sahabatmakara.entities.RowData_ListGallery;
import ui.tech.sahabatmakara.fragment.Fragment_Gallery;
import ui.tech.sahabatmakara.util.Font;

public class CustomAdapter_ListGallery extends ArrayAdapter<RowData_ListGallery>{
	private Context context;
	private List<RowData_ListGallery> objects;
	private FragmentManager fm;

	public CustomAdapter_ListGallery(Context context, int resource, List<RowData_ListGallery> objects, FragmentManager fm) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.objects = objects;
		this.fm = fm;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View v;
		LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(position % 2 == 1){
			v = mInflater.inflate(R.layout.item_list_gallery_right, null);
		}else{
			v = mInflater.inflate(R.layout.item_list_gallery, null);
		}
		
		final RowData_ListGallery item = objects.get(position);
		
		TextView tv_Title = (TextView)v.findViewById(R.id.tv_title); 
		tv_Title .setTypeface(Font.setFontGaramond(context));
		
		ImageView img = (ImageView)v.findViewById(R.id.img);
		
		tv_Title.setText(item.title);
		Picasso.with(context).load(item.url_img).into(img);
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(getContext(), Activity_Content.class);
//				context.startActivity(intent);
				fm.beginTransaction().replace(R.id.frame_container, new Fragment_Gallery().create(item.id, item.img_name)).addToBackStack("").commit();
			}
		});
		
		Animation animate_FromLeft = AnimationUtils.loadAnimation(context, R.anim.anim_from_left);
		Animation animate_FromRight = AnimationUtils.loadAnimation(context, R.anim.anim_from_right);
		
		Animation animate_Alpha = AnimationUtils.loadAnimation(context, R.anim.anim_tv_in_alpha);
		Animation animate_FromBot = AnimationUtils.loadAnimation(context, R.anim.anim_tv_in_from_bot);
		Animation animate_BounceFromTop = AnimationUtils.loadAnimation(context, R.anim.anim_in);
		if(position % 2 == 1){
			tv_Title.startAnimation(animate_FromRight);
			img.startAnimation(animate_Alpha);
		}else{
			tv_Title.startAnimation(animate_FromLeft);
			img.startAnimation(animate_Alpha);
		}
		
		return v;
	}

}
