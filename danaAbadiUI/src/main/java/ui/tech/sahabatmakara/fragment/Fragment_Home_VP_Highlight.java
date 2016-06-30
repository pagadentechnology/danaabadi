package ui.tech.sahabatmakara.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ui.tech.sahabatmakara.activity.Activity_DetailEvent;
import ui.tech.sahabatmakara.activity.Activity_DetailNews;
import ui.tech.sahabatmakara.activity.Activity_DetailProgram;
import ui.tech.sahabatmakara.activity.R;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;

/**
 * Created by Anoa 34 on 06/10/2015.
 */
public class Fragment_Home_VP_Highlight extends Fragment {
    private static int mPageNumber;
    private static String mId, mTitle, mDate, mDesc, mUrl;
    private ImageView img;
    private TextView tv_Title, tv_Date, tv_Desc;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector mDetector;

    public static Fragment_Home_VP_Highlight create(int pageNumber, String id, String title,
                                                    String date, String desc, String url_img){
        Fragment_Home_VP_Highlight fragment = new Fragment_Home_VP_Highlight();
        Bundle b = new Bundle();
        b.putInt(ParameterCollections.ARGS_PAGENUMBER, pageNumber);
        b.putString(ParameterCollections.ARGS_ID, id);
        b.putString(ParameterCollections.ARGS_TITLE, title);
        b.putString(ParameterCollections.ARGS_DATE, date);
        b.putString(ParameterCollections.ARGS_DESC, desc);
        b.putString(ParameterCollections.ARGS_URLIMG, url_img);
        fragment.setArguments(b);
        return fragment;
    }

    public static int getPageNumber() {
        return mPageNumber;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPageNumber = getArguments().getInt(ParameterCollections.ARGS_PAGENUMBER);
        mId = getArguments().getString(ParameterCollections.ARGS_ID);
        mTitle = getArguments().getString(ParameterCollections.ARGS_TITLE);
        mDate = getArguments().getString(ParameterCollections.ARGS_DATE);
        mDesc = getArguments().getString(ParameterCollections.ARGS_DESC);
        mUrl= getArguments().getString(ParameterCollections.ARGS_URLIMG);

        View v = inflater.inflate(R.layout.layout_item_vp_highlight, null);

        tv_Title = (TextView)v.findViewById(R.id.tv_title);
        tv_Date = (TextView)v.findViewById(R.id.tv_tgl);
        tv_Desc= (TextView)v.findViewById(R.id.tv_desc);;

        img = (ImageView)v.findViewById(R.id.img);

        tv_Title.setText(mTitle);
        tv_Date.setText(mDate);
        tv_Desc.setText(mDesc);


        tv_Title.setTypeface(Font.setLato(getActivity()));
        tv_Date.setTypeface(Font.setLato(getActivity()));
        tv_Desc.setTypeface(Font.setLato(getActivity()));

        if(mUrl.equals("")){
            img.setImageResource(R.drawable.img_not_found);
        }else{
            Picasso.with(getActivity()).load(mUrl).into(img);
        }


        mDetector = new GestureDetector(getActivity(), new MyViewGestureListener(mId, mPageNumber));

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);
                return true;
            }
        });
        return v;
    }

    private class MyViewGestureListener extends GestureDetector.SimpleOnGestureListener{
        private String idnya;
        private int posisinya;

        public MyViewGestureListener(String id, int posisi){
            idnya = id;
            posisinya = posisi;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e("judulnya di klik", idnya);

            if(posisinya == 0){
                Intent intent = new Intent(getActivity(), Activity_DetailEvent.class);
                intent.putExtra("id", idnya);
                startActivity(intent);
            }else{
                Intent intent = new Intent(getActivity(), Activity_DetailNews.class);
                intent.putExtra("id", idnya);
                startActivity(intent);
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {



            return true;
        }

    }


}
