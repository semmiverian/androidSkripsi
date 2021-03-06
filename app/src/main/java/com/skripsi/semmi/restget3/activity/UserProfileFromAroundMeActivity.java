package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skripsi.semmi.restget3.Interface.userCareerInterface;
import com.skripsi.semmi.restget3.Interface.UserProductInterface;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.Model.AllProduct;
import com.skripsi.semmi.restget3.Model.Product;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.UserCareerAdapter;
import com.skripsi.semmi.restget3.adapter.UserSaleAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 09/11/2015.
 */
public class UserProfileFromAroundMeActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mUsername;
    private TextView mStatus;
    private ImageView mImageView;
    private Button mButton;

    private GridView mGridView;
    private GridView mGridView2;
    private UserSaleAdapter mAdapater;
    private UserCareerAdapter mAdapater2;
    private int to_id;
    public static final String extraUsername="extraUsername";
    public static final String extraImage="extraImage";
    public static final String extraNama="extraNama";

    public static final String extraStatus = "extraStatus";
    private String userName;
    private String userImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_user_profile_fragment);
        // define design di XML
        mUsername= (TextView) findViewById(R.id.usernameProfile);
        mStatus= (TextView) findViewById(R.id.usernameStatus);
        mImageView= (ImageView) findViewById(R.id.UserProfileImage);
        mGridView= (GridView) findViewById(R.id.gridSale);
        mGridView2= (GridView) findViewById(R.id.gridCareer);
        mButton = (Button) findViewById(R.id.messageButton);


        // Ambil data dari Intent
        if(getIntent()!= null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey(extraUsername)){
                Log.d("retrofit", getIntent().getExtras().getString(extraUsername));
                Log.d("retrofit", getIntent().getExtras().getString(extraStatus));
                mUsername.setText(getIntent().getExtras().getString(extraNama).toUpperCase());
                mStatus.setText(getIntent().getExtras().getString(extraStatus));
                Picasso.with(UserProfileFromAroundMeActivity.this)
                    .load(getIntent().getExtras().getString(extraImage))
                        .into(mImageView);
                userName=getIntent().getExtras().getString(extraUsername);
                to_id=getIntent().getIntExtra("IDValue",0);
            }
        }

        getUserProductInfo(userName);
        getCareerInfo(userName);
        mButton.setOnClickListener(this);

    }

    private void getCareerInfo(String username) {
        mAdapater2=new UserCareerAdapter(this,2);
        mGridView2.setAdapter(mAdapater2);
        RestAdapter restAdapter2=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        userCareerInterface userCareerInterface=restAdapter2.create(com.skripsi.semmi.restget3.Interface.userCareerInterface.class);
        userCareerInterface.getCareer(username, new Callback<List<AllCareer>>() {
            @Override
            public void success(List<AllCareer> allCareers, Response response) {
                if (allCareers == null || allCareers.isEmpty()) {
                    Toast.makeText(UserProfileFromAroundMeActivity.this, "Ga ada Career  yang di pasang", Toast.LENGTH_SHORT).show();
                }
                for (AllCareer allCareer : allCareers) {
                    mAdapater2.add(allCareer);
                    Log.d("careerTest", allCareer.getKarirnama());
                }
                mAdapater.notifyDataSetChanged();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Career " + error.getMessage());

            }
        });
    }

    private void getUserProductInfo(String username) {
        // REST untuk ambil produk yang di jual user
        mAdapater=new UserSaleAdapter(this,1);
        mGridView.setAdapter(mAdapater);
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserProductInterface userProductInterface=restAdapter.create(UserProductInterface.class);
        userProductInterface.getUserProduct(username, new Callback<List<AllProduct>>() {
            @Override
            public void success(List<AllProduct> allProducts, Response response) {
                Log.d("berhasil catch", "berhasil gan");
                if(allProducts == null  || allProducts.isEmpty()){
                    Toast.makeText(UserProfileFromAroundMeActivity.this, "Ga ada Produk yang di Jual", Toast.LENGTH_SHORT).show();
                }
                for(AllProduct allProduct:allProducts){
                    mAdapater.add(allProduct);
                }
                mAdapater.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Retrofit" + error.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.messageButton:

                Intent intentMessage = new Intent(this, MessageActivity.class);
                intentMessage.putExtra(MessageActivity.to_id,to_id);
                startActivity(intentMessage);
                break;
        }
    }
}
