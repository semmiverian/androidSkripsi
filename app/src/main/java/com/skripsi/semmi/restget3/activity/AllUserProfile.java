package com.skripsi.semmi.restget3.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.skripsi.semmi.restget3.Interface.UserCareerInterface;
import com.skripsi.semmi.restget3.Interface.UserImageInterface;
import com.skripsi.semmi.restget3.Interface.UserProductInterface;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.Model.Career;
import com.skripsi.semmi.restget3.Model.Product;
import com.skripsi.semmi.restget3.Model.UserImage;
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
public class AllUserProfile extends AppCompatActivity {
    private TextView mUsername;
    private TextView mStatus;
    private ImageView mImageView;


    private GridView mGridView;
    private GridView mGridView2;
    private UserSaleAdapter mAdapater;
    private UserCareerAdapter mAdapater2;
    public static final String extra="extra";
    private String userName;
    private String userImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_user_profile_fragment);
        mUsername= (TextView) findViewById(R.id.usernameProfile);
        mStatus= (TextView) findViewById(R.id.usernameStatus);
        mImageView= (ImageView) findViewById(R.id.UserProfileImage);
        mGridView= (GridView) findViewById(R.id.gridSale);
        mGridView2= (GridView) findViewById(R.id.gridCareer);

        // ambil data yang udah di parse dari list fragment
        AllUser allUser= getIntent().getExtras().getParcelable(extra);

        mUsername.setText(allUser.getUsername().toUpperCase());
        mStatus.setText(allUser.getStatus());
        Picasso.with(AllUserProfile.this)
                .load(allUser.getImage())
                .into(mImageView);
        userName=allUser.getUsername();
        getUserProductInfo(userName);
        getCareerInfo(userName);


    }

    private void getCareerInfo(String username) {
        mAdapater2=new UserCareerAdapter(this,2);
        mGridView2.setAdapter(mAdapater2);
        RestAdapter restAdapter2=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserCareerInterface userCareerInterface=restAdapter2.create(UserCareerInterface.class);
        userCareerInterface.getCareer(username, new Callback<List<Career>>() {
            @Override
            public void success(List<Career> careers, Response response) {
                Log.d("berhasil catch", "berhasil gan daru career");
                if (careers == null || careers.isEmpty()) {
                    Toast.makeText(AllUserProfile.this, "Ga ada Career  yang di pasang", Toast.LENGTH_SHORT).show();
                }
                for (Career career : careers) {
                    mAdapater2.add(career);
                }
                mAdapater2.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Career Grid" + error.getMessage());
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
        userProductInterface.getUserProduct(username, new Callback<List<Product>>() {
            @Override
            public void success(List<Product> products, Response response) {
                Log.d("berhasil catch", "berhasil gan");
                if(products == null  || products.isEmpty()){
                    Toast.makeText(AllUserProfile.this, "Ga ada Produk yang di Jual", Toast.LENGTH_SHORT).show();
                }
                for(Product product:products){
                    mAdapater.add(product);
                }
                mAdapater.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Retrofit" + error.getMessage());
            }
        });
    }




}
