package ui.tech.sahabatmakara.util;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;

public class Font {
	public static Typeface mFont;
	
	public static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();
	private static final String url_font_garomond = "font/EBGaramond-Regular.ttf";
	private static final String url_font_lato = "font/Lato-Regular.ttf";
	
	public static Typeface setFontGaramond(Context c) {
		synchronized (cache) {
			if (!cache.containsKey(url_font_garomond)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							url_font_garomond);
					cache.put(url_font_garomond, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(url_font_garomond);
		}
	}
	
	public static Typeface setLato(Context c) {
		synchronized (cache) {
			if (!cache.containsKey(url_font_lato)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							url_font_lato);
					cache.put(url_font_lato, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(url_font_lato);
		}
	}

}
