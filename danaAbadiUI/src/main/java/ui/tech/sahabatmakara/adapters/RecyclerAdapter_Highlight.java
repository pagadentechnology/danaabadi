package ui.tech.sahabatmakara.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import ui.tech.sahabatmakara.activity.R;
import ui.tech.sahabatmakara.entities.RowData_News;

/**
 * Created by RebelCreative-A1 on 15/10/2015.
 */
public class RecyclerAdapter_Highlight extends RecyclerView.Adapter<RecyclerAdapter_Highlight.ViewHolder> {
    private Context ctx;
    private List<RowData_News> data;

    public RecyclerAdapter_Highlight(List<RowData_News> data, Context ctx){
        this.data = data;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_Highlight.ViewHolder viewHolder, int i) {

        final RowData_News item = data.get(i);



        Picasso.with(ctx).load(item.link).into(viewHolder.img);
        viewHolder.tv_Title.setText(item.title);
        viewHolder.tv_Date.setText(item.date);
        viewHolder.tv_Desc.setText(item.desc);

        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx,"Klik Data", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public RecyclerAdapter_Highlight.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.layout_item_vp_highlight, null);



        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView tv_Title, tv_Date, tv_Desc;

        public ViewHolder(View v){
            super(v);
            img = (ImageView)v.findViewById(R.id.img);
            tv_Title = (TextView)v.findViewById(R.id.tv_title);
            tv_Date = (TextView)v.findViewById(R.id.tv_tgl);
            tv_Desc= (TextView)v.findViewById(R.id.tv_desc);;

        }
    }
}
