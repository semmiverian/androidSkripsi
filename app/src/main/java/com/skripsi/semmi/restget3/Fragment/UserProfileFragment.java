package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.Interface.UserProductInterface;
import com.skripsi.semmi.restget3.Model.Product;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.UserSaleAdapter;
import com.skripsi.semmi.restget3.view.AnimatedExpandableListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 21/10/2015.
 */
public class UserProfileFragment extends Fragment {
    private TextView mUsername;
    private TextView mStatus;
    private ImageView mImageView;
    private Button mButton;
    private String imageLink;
    private GridView mGridView;
    private UserSaleAdapter mAdapater;
    private  SharedPreferences sharedPreferences;
    public static UserProfileFragment getInstance(){
        UserProfileFragment fragment=new UserProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_profile_fragment, container, false);
        mUsername= (TextView) view.findViewById(R.id.usernameProfile);
        mStatus= (TextView) view.findViewById(R.id.usernameStatus);
        mImageView= (ImageView) view.findViewById(R.id.UserProfileImage);
        String defaultImage=getString(R.string.avatarDefaultString);
        sharedPreferences= this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        mUsername.setText(sharedPreferences.getString("usernameSession","Username" ).toUpperCase());
        mStatus.setText(sharedPreferences.getString("statusSession","Status"));
        imageLink=sharedPreferences.getString("imageSession",defaultImage);
        Picasso.with(getContext())
                .load(imageLink)
                .into(mImageView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridView= (GridView) view.findViewById(R.id.gridSale);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String user=sharedPreferences.getString("usernameSession","Username");
        mAdapater=new UserSaleAdapter(getContext(),1);
        mGridView.setAdapter(mAdapater);
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserProductInterface userProductInterface=restAdapter.create(UserProductInterface.class);
        userProductInterface.getUserProduct(user, new Callback<Product>() {
            @Override
            public void success(Product product, Response response) {
                Log.d("berhasil catch","berhasil gan");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Retrofit" + error.getMessage());
            }
        });
    }
}
