package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 03/11/2015.
 */
public class CareerDetailActivity extends AppCompatActivity {
    public static final String extra="extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_detail);

        // ambil data yang udah di parse
        AllCareer allCareer=getIntent().getExtras().getParcelable(extra);
        // define objek yang di xml
        TextView judul= (TextView) findViewById(R.id.careerJudul);
        TextView detail= (TextView) findViewById(R.id.carerDetail);
        ImageView gambar= (ImageView) findViewById(R.id.careerImage);

        //  ambil data dari server terus di tampilin
        judul.setText(allCareer.getNama());
        detail.setText(allCareer.getDetail());
        Picasso.with(this)
                .load(allCareer.getImage())
                .into(gambar);

    }
}
