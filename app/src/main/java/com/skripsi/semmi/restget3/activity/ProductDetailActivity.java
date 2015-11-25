package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Interface.UserProfileFromAroundMe;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.Model.AllProduct;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 03/11/2015.
 */
public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
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

        // set onclik listener
        profilepict.setOnClickListener(this);
        user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AllProduct allProduct=getIntent().getExtras().getParcelable(extra);
        String username = allProduct.getUserName();

        switch( v.getId()){
            case R.id.imageProfile:
                goToUserProfile(username);
                break;
            case R.id.userNameProduk:
                goToUserProfile(username);
                break;
        }
    }

    private void goToUserProfile(String username) {
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserProfileFromAroundMe upfa = restAdapter.create(UserProfileFromAroundMe.class);
        upfa.fetchUser(username, new Callback<AllUser>() {
            @Override
            public void success(AllUser allUser, Response response) {
                Intent userProfileIntent = new Intent(ProductDetailActivity.this, UserProfileFromAroundMeActivity.class);
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraUsername,allUser.getUsername());
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraImage,allUser.getImage());
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraStatus,allUser.getStatus());
                userProfileIntent.putExtra("IDValue",allUser.getId());
                startActivity(userProfileIntent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("career", "from Career detail" + error.getMessage());
            }
        });
    }
}
