package ui.tech.sahabatmakara.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ui.tech.sahabatmakara.activity.R;
import ui.tech.sahabatmakara.util.Font;
import ui.tech.sahabatmakara.util.ParameterCollections;

/**
 * Created by Anoa 34 on 07/10/2015.
 */
public class Fragment_Home_VP_KataMereka extends Fragment {
    private String mId, mContent, mCreator,mUrl;

    public static Fragment_Home_VP_KataMereka create(int pageNumber, String content, String url_img){
        Fragment_Home_VP_KataMereka fragment = new Fragment_Home_VP_KataMereka();
        Bundle b= new Bundle();
        b.putInt(ParameterCollections.ARGS_PAGENUMBER, pageNumber);
        b.putString(ParameterCollections.ARGS_DESC, content);
        b.putString(ParameterCollections.ARGS_URLIMG, url_img);
        fragment.setArguments(b);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContent = getArguments().getString(ParameterCollections.ARGS_DESC);
        mId = getArguments().getString(ParameterCollections.ARGS_ID);
        mUrl = getArguments().getString(ParameterCollections.ARGS_URLIMG);

        View v = inflater.inflate(R.layout.layout_item_vp_katamereka, null);

        TextView tv_content = (TextView)v.findViewById(R.id.tv_title);
        ImageView img = (ImageView)v.findViewById(R.id.img);

        tv_content.setText(mContent);
        tv_content.setTypeface(Font.setLato(getActivity()));

        if(mUrl.equals("")){
            img.setImageResource(R.drawable.img_not_found);
        }else{
            Picasso.with(getActivity()).load(mUrl).into(img);
        }
        return v;
    }
}
