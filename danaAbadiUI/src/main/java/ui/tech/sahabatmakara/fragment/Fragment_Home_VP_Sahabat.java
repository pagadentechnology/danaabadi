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
public class Fragment_Home_VP_Sahabat extends Fragment {
    private String mNama, mFakultas, mUrl,mNama2, mFakultas2, mUrl2;
    private  int mPageNumber;

    public static Fragment_Home_VP_Sahabat create(int pageNumber, String nama, String fakultas, String url,
                                                  String nama2, String fakultas2, String url2){
        Fragment_Home_VP_Sahabat fragment = new Fragment_Home_VP_Sahabat();
        Bundle b = new Bundle();
        b.putInt(ParameterCollections.ARGS_PAGENUMBER, pageNumber);
        b.putString(ParameterCollections.ARGS_DONATUR_NAME, nama);
        b.putString(ParameterCollections.ARGS_FAKULTAS, fakultas);
        b.putString(ParameterCollections.ARGS_URLIMG, url);

        b.putString(ParameterCollections.ARGS_DONATUR_NAME2, nama2);
        b.putString(ParameterCollections.ARGS_FAKULTAS2, fakultas2);
        b.putString(ParameterCollections.ARGS_URLIMG2, url2);

        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPageNumber = getArguments().getInt(ParameterCollections.ARGS_PAGENUMBER);
        mNama = getArguments().getString(ParameterCollections.ARGS_DONATUR_NAME);
        mFakultas = getArguments().getString(ParameterCollections.ARGS_FAKULTAS);
        mUrl= getArguments().getString(ParameterCollections.ARGS_URLIMG);

        mNama2 = getArguments().getString(ParameterCollections.ARGS_DONATUR_NAME2);
        mFakultas2 = getArguments().getString(ParameterCollections.ARGS_FAKULTAS2);
        mUrl2= getArguments().getString(ParameterCollections.ARGS_URLIMG2);

        View v = inflater.inflate(R.layout.layout_item_vp_sahabat, null);

        TextView tv_nama_1 = (TextView)v.findViewById(R.id.tv_name_1);
        TextView tv_nama_2 = (TextView)v.findViewById(R.id.tv_name_2);

        TextView tv_fakultas_1 = (TextView)v.findViewById(R.id.tv_fakultas_1);
        TextView tv_fakultas_2 = (TextView)v.findViewById(R.id.tv_fakultas_2);

        ImageView img = (ImageView)v.findViewById(R.id.img_1);
        ImageView img2 = (ImageView)v.findViewById(R.id.img_2);

        tv_nama_1.setText(mNama);
        tv_nama_2.setText(mNama2);

        tv_fakultas_1.setText(mFakultas);
        tv_fakultas_2.setText(mFakultas2);

        tv_nama_1.setTypeface(Font.setLato(getActivity()));
        tv_nama_2.setTypeface(Font.setLato(getActivity()));
        tv_fakultas_1.setTypeface(Font.setLato(getActivity()));
        tv_fakultas_2.setTypeface(Font.setLato(getActivity()));

        if(mUrl.equals("")){
            img.setImageResource(R.drawable.img_not_found);
        }else{
            Picasso.with(getActivity()).load(mUrl).into(img);
        }

        if(mUrl2.equals("")){
            img.setImageResource(R.drawable.img_not_found);
        }else{
            Picasso.with(getActivity()).load(mUrl2).into(img2);
        }



        return v;
    }
}
