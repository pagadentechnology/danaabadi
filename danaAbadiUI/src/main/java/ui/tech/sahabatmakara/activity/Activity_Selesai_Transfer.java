package ui.tech.sahabatmakara.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import ui.tech.sahabatmakara.util.ParameterCollections;

/**
 * Created by RebelCreative-A1 on 20/10/2015.
 */
public class Activity_Selesai_Transfer extends ActionBarActivity {
    Button btn;
    TextView tv_label_nama_rek, tv_label_no_rek;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_selesai_transfer);

        tv_label_nama_rek = (TextView)findViewById(R.id.tv_label_nama_rek);
        tv_label_no_rek = (TextView)findViewById(R.id.tv_label_no_rek);

        String nama_rek = getIntent().getStringExtra(ParameterCollections.ARGS_NAMA_REK);
        String no_rek = getIntent().getStringExtra(ParameterCollections.ARGS_NO_REK);

        tv_label_nama_rek.setText(nama_rek);
        tv_label_no_rek.setText(no_rek);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
