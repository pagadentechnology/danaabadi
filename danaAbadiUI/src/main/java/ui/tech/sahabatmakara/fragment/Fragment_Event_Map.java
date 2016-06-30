package ui.tech.sahabatmakara.fragment;

import ui.tech.sahabatmakara.activity.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import ui.tech.sahabatmakara.util.ParameterCollections;

public class Fragment_Event_Map extends Fragment{
	
	public static Fragment_Event_Map create(String lat, String longi){
		Fragment_Event_Map fragment = new Fragment_Event_Map();
		Bundle b = new Bundle();
		b.putString(ParameterCollections.ARGS_LAT, lat);
		b.putString(ParameterCollections.ARGS_LONG, longi);
		fragment.setArguments(b);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_img_map, null);

		String latitude = getArguments().getString(ParameterCollections.ARGS_LAT);
		String longitude = getArguments().getString(ParameterCollections.ARGS_LONG);
		
		String url = "http://maps.google.com/maps/api/staticmap?center="
				+ getArguments().getString(ParameterCollections.ARGS_LAT)
				+ ","
				+ getArguments().getString(ParameterCollections.ARGS_LONG)
				+ "&zoom=15&size=300x200&markers=color:blue|size:mid|"
				+ latitude
				+ ","
				+ longitude
				+ "&sensor=false";
		ImageView img = (ImageView)v.findViewById(R.id.img);
		Picasso.with(getActivity()).load(url).into(img);
		
		return v;
	}

}
