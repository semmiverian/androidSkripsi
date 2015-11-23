package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.Model.AllProduct;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

/**
 * Created by semmi on 03/11/2015.
 */
public class ProductDetailActivity extends AppCompatActivity {
    public static final String extra="extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_detail);

        // ambil data yang udah di parse
        AllProduct allProduct=getIntent().getExtras().getParcelable(extra);

        // define objek yang di xml
        TextView judul= (TextView) findViewById(R.id.produkJudul);
        TextView detail= (TextView) findViewById(R.id.produkDetail);
        ImageView gambar= (ImageView) findViewById(R.id.produkImage);
        ImageView profilepict = (ImageView) findViewById(R.id.imageProfile);
        TextView user = (TextView) findViewById(R.id.userNameProduk);

        //  ambil data dari server terus di tampilin
        judul.setText(allProduct.getProduknama());
        detail.setText(allProduct.getProdukdetail());
        Picasso.with(this)
                .load(allProduct.getProdukImage())
                .into(gambar);
        user.setText(allProduct.getUserName());
        Picasso.with(this)
                .load(allProduct.getUserImage())
                .into(profilepict);

    }
}
