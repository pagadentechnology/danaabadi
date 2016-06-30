package ui.tech.sahabatmakara.adapters;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ui.tech.sahabatmakara.activity.Activity_DetailNews;
import ui.tech.sahabatmakara.entities.RowData_News;
import ui.tech.sahabatmakara.util.Font;

import ui.tech.sahabatmakara.activity.R;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class CustomAdapter_News extends ArrayAdapter<RowData_News> {
    private List<RowData_News> objects;
    private Context context;

    public CustomAdapter_News(Context context, int resource,
                              List<RowData_News> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.item_news, null);

        final RowData_News item = objects.get(position);

        TextView tv_Author = (TextView) v.findViewById(R.id.tv_author);
        TextView tv_Tgl = (TextView) v.findViewById(R.id.tv_tgl);
        TextView tv_Title = (TextView) v.findViewById(R.id.tv_title);
        TextView tv_Desc = (TextView) v.findViewById(R.id.tv_desc);

        tv_Author.setTypeface(Font.setFontGaramond(context));
        tv_Tgl.setTypeface(Font.setFontGaramond(context));
        tv_Title.setTypeface(Font.setFontGaramond(context));
        tv_Desc.setTypeface(Font.setFontGaramond(context));

        ImageView img = (ImageView) v.findViewById(R.id.img);

        DateTime dateTime = DateTime.parse(item.date, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        int hari = dateTime.getDayOfWeek();
        switch (hari) {
            case 1:
                tv_Tgl.setText("Senin, " + item.date);
                break;
            case 2:
                tv_Tgl.setText("Selasa, " + item.date);
                break;
            case 3:
                tv_Tgl.setText("Rabu, " + item.date);
                break;
            case 4:
                tv_Tgl.setText("Kamis, " + item.date);
                break;
            case 5:
                tv_Tgl.setText("Jumat, " + item.date);
                break;
            case 6:
                tv_Tgl.setText("Sabtu, " + item.date);
                break;
            case 7:
                tv_Tgl.setText("Minggu, " + item.date);
                break;


        }

        tv_Title.setText(item.title);
        Picasso.with(context).load(item.link).into(img);
        tv_Author.setText(item.creator);
        v.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(context, Activity_DetailNews.class);
                intent.putExtra("id", item.id);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });

        Animation animate_Alpha = AnimationUtils.loadAnimation(context, R.anim.anim_tv_in_alpha);
        Animation animate_FromBot = AnimationUtils.loadAnimation(context, R.anim.anim_tv_in_from_bot);
        Animation animate_BounceFromTop = AnimationUtils.loadAnimation(context, R.anim.anim_in);
        tv_Author.startAnimation(animate_Alpha);
        tv_Title.startAnimation(animate_Alpha);
        tv_Desc.startAnimation(animate_FromBot);
        img.startAnimation(animate_Alpha);
        tv_Desc.setText(item.desc);

        return v;
    }

}
