package ui.tech.sahabatmakara.adapters;

import java.util.List;

import ui.tech.sahabatmakara.activity.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import ui.tech.sahabatmakara.entities.RowData_GalleryImage;
import ui.tech.sahabatmakara.helper.GalleryViewCallback;

public class RecyclerAdapter_Gallery extends RecyclerView.Adapter<RecyclerAdapter_Gallery.ViewHolder>{
	private List<RowData_GalleryImage> data;
	private Context ctx;
	private GalleryViewCallback mCallback;
	
	public GalleryViewCallback getGalleryCallback(){
		return mCallback;
	}
	
	public void setGalleryCallback(GalleryViewCallback mCallback){
		this.mCallback = mCallback;
	}
	
	public RecyclerAdapter_Gallery(List<RowData_GalleryImage> data, Context ctx) {
		// TODO Auto-generated constructor stub
		this.data =data;
		this.ctx = ctx;
	}
	
	public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
		public ImageView img;

		public ViewHolder(View itemView) {
			super(itemView);
			// TODO Auto-generated constructor stub
//			img = (ImageView)itemView.findViewWithTag(itemView.getTag());
			img = (ImageView)itemView.findViewById(R.id.img);
			itemView.setClickable(true);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			ctx.startActivity(new Intent(ctx, Activity_ViewImage.class));
//			Log.e("CLICK di VIEW HOLDER", "GALLERY");
		}
		
	}
	

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0,final int arg1) {
		// TODO Auto-generated method stub
//		arg0.img.setImageResource(R.drawable.img_bg_program);
//		arg0.onClick(arg0.img);
		final RowData_GalleryImage item = data.get(arg1);
		Picasso.with(ctx).load(item.url).into(arg0.img);
		arg0.img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final RowData_GalleryImage item = data.get(arg1);
				if(mCallback != null) mCallback.onGalleryItemClicked(item.url, item.img_name);
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, final int arg1) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = mInflater.inflate(R.layout.layout_imageview, null) ;
		
//		ImageView view_img = new ImageView(ctx);
//		view_img.setLayoutParams(new LayoutParams(300,300));
//		view_img.setScaleType(ScaleType.CENTER_CROP);		
//		view_img.setTag(arg1);
//		view_img.setPadding(10, 10, 10, 10);
//		view_img.setBackgroundResource(R.drawable.img_bg_image);
		
		v.setPadding(0, 0, 0, 0);
		
		return new ViewHolder(v);
	}

	

}
