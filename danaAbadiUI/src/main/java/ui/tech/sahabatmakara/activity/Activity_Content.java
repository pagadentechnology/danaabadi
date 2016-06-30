package ui.tech.sahabatmakara.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.widget.TextView;
import ui.tech.sahabatmakara.fragment.Fragment_Event;
import ui.tech.sahabatmakara.fragment.Fragment_ListGallery;
import ui.tech.sahabatmakara.fragment.Fragment_News;
import ui.tech.sahabatmakara.fragment.Fragment_Program;
import ui.tech.sahabatmakara.fragment.Fragment_Setting;
import ui.tech.sahabatmakara.util.Font;

public class Activity_Content extends FragmentActivity{
	private int position;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_content);
		
		initAllView();
		
		position = getIntent().getIntExtra("pos", 0);
		
		if(arg0 == null){
			FragmentManager fm = getSupportFragmentManager();
			switch (position) {
			case 1:				
				fm.beginTransaction().replace(R.id.frame_container, new Fragment_Program()).commit();
				break;
			case 2:				
				fm.beginTransaction().replace(R.id.frame_container, new Fragment_Event()).commit();
				break;
			case 3:				
				fm.beginTransaction().replace(R.id.frame_container, new Fragment_News()).commit();
				break;
			case 4:				
				fm.beginTransaction().replace(R.id.frame_container, new Fragment_ListGallery()).commit();
				break;
			case 5:				
				fm.beginTransaction().replace(R.id.frame_container, new Fragment_News()).commit();
				break;
			case 6:				
				fm.beginTransaction().replace(R.id.frame_container, new Fragment_Setting()).commit();
				break;

			default:
				break;
			}
			
		}
	}
	
	private void initAllView(){
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setTypeface(Font.setFontGaramond(getApplicationContext()));
		
	}
}
