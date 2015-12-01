package com.skripsi.semmi.restget3.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.AllProductActivity;
import com.skripsi.semmi.restget3.activity.AllUserActivity;
import com.skripsi.semmi.restget3.activity.AroundMeActivity;
import com.skripsi.semmi.restget3.activity.CareerActivity;
import com.skripsi.semmi.restget3.activity.UserProfileNewActivity;

/**
 * Created by semmi on 01/12/2015.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String  username="username";
    public static final String  status="status";
    private TextView mUserLogin;
    private TextView mStatusLogin;
    private Button mAroundMe;
    private Button mCareer;
    private Button mDummy;
    private Button allUser;
    private Button product;
    private Button userProfileBeta;
    private ImageView userImage;
    private String usernameExtra;
    private String statusExtra;
    private String imageUserLink;
    private SharedPreferences sharedPreferences;
    private TextView navigationName;
    private TextView navigationStatus;

    public static HomeFragment getInstance(){
        HomeFragment fragment=  new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_content,container,false);
        // define ID from xml
        mUserLogin= (TextView) view.findViewById(R.id.userLogin);
        mStatusLogin= (TextView) view.findViewById(R.id.statusLogin);
        mAroundMe= (Button) view.findViewById(R.id.aroundme);
        mCareer= (Button) view.findViewById(R.id.careerButton);
        mDummy= (Button) view.findViewById(R.id.dummybutton);
        allUser= (Button) view.findViewById(R.id.allUserButton);
        product = (Button) view.findViewById(R.id.trading);
        userProfileBeta = (Button) view.findViewById(R.id.userProfileBeta);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // define onclick listener
        mAroundMe.setOnClickListener(this);
        mCareer.setOnClickListener(this);
        allUser.setOnClickListener(this);
        product.setOnClickListener(this);
        userProfileBeta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aroundme:
                Intent intentAroundMe=new Intent(getActivity(),AroundMeActivity.class);
                intentAroundMe.putExtra(AroundMeActivity.username, usernameExtra);
                startActivity(intentAroundMe);
                break;
            case R.id.careerButton:
                Intent intentCareer=new Intent(getActivity(),CareerActivity.class);
//                intentAroundMe.putExtra(AroundMeActivity.username, usernameExtra);
                startActivity(intentCareer);
                break;
            case R.id.allUserButton:
                Intent intentUser = new Intent(getActivity(), AllUserActivity.class);
                startActivity(intentUser);
                break;
            case R.id.trading:
                Intent intentTrading = new Intent(getActivity() , AllProductActivity.class);
                startActivity(intentTrading);
                break;
            case R.id.userProfileBeta:
                Intent userProfileBetaIntent = new Intent(getActivity(), UserProfileNewActivity.class);
                startActivity(userProfileBetaIntent);
                break;
        }
    }
}
