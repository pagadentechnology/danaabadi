package ui.tech.sahabatmakara.adapters;

import java.util.List;

import ui.tech.sahabatmakara.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import ui.tech.sahabatmakara.entities.RowData_History;
import ui.tech.sahabatmakara.util.Font;

public class CustomAdapter_ListHistory extends ArrayAdapter<RowData_History>{
	private Context context;
	private List<RowData_History> objects;

	public CustomAdapter_ListHistory(Context context, int resource, List<RowData_History> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.objects = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View v = inflater.inflate(R.layout.item_history, null);
		
		final RowData_History item = objects.get(position);
		
		TextView tv_Title = (TextView)v.findViewById(R.id.tv_program);
		TextView tv_NominalDonasi = (TextView)v.findViewById(R.id.tv_nominal_donasi);
		
		tv_Title.setTypeface(Font.setLato(context));
		tv_NominalDonasi.setTypeface(Font.setLato(context));

		if(item.title_Program == null || item.title_Program.equals("null") || item.title_Program.isEmpty()){
			tv_Title.setText("Donasi Ke Fakultas : " + item.fakultas_Donasi);
		}else{
			tv_Title.setText("Donasi Ke Program : " + item.title_Program);
		}

		tv_NominalDonasi.setText("Rp. " + String.format("%,d",Integer.parseInt(item.nominal_Donasi)).replace(",","."));
		
		return v;
	}

}
