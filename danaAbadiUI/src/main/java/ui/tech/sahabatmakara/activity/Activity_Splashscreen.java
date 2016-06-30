package ui.tech.sahabatmakara.activity;

import ui.tech.sahabatmakara.util.ParameterCollections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Activity_Splashscreen extends Activity {
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity__splashscreen);

		sp = getSharedPreferences(ParameterCollections.SH_NAME, Context.MODE_PRIVATE);

		ImageView img_Logo = (ImageView)findViewById(R.id.img);
		Animation animate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_in);
		img_Logo.startAnimation(animate);
		new Async_Load().execute();
	}

	
	private class Async_Load extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				Thread.sleep(3000);
				showHashKey();
			}catch(Exception e){
				
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if(sp.getBoolean(ParameterCollections.SH_LOGGED, false)){
				startActivity(new Intent(getApplicationContext(), Activity_MenuSlider.class));
				finish();
			}else{
				startActivity(new Intent(getApplicationContext(), Activity_Login.class));
				finish();
			}
			
//			startActivity(new Intent(getApplicationContext(), Activity_MenuSlider.class));


		}
	}

	private void showHashKey()
	{
		// Add code to print out the key hash
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"ui.tech.sahabatmakara.activity",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (PackageManager.NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

	}
	
}
