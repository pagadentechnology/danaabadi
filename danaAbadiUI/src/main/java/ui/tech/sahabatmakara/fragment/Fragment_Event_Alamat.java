package ui.tech.sahabatmakara.fragment;

import ui.tech.sahabatmakara.activity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ui.tech.sahabatmakara.util.ParameterCollections;

public class Fragment_Event_Alamat extends Fragment{
	
	public static Fragment_Event_Alamat create(String alamat){
		Fragment_Event_Alamat fragment = new Fragment_Event_Alamat();
		Bundle b = new Bundle();
		b.putString(ParameterCollections.ARGS_ALAMAT, alamat);
		fragment.setArguments(b);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.layout_alamat, null);
		TextView tv_Alamat = (TextView)v.findViewById(R.id.tv_desc);
		tv_Alamat.setText(getArguments().getString(ParameterCollections.ARGS_ALAMAT));
		return v;
	}

}
