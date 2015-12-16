package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private Button allUser;
    private Button product;
    private SharedPreferences sharedPreferences;
    private TextView welcomeName;
    private TextView welcomeJurusan;
    private TextView welcomeAngkatan;
    private TextView welcomeStatus;

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
        welcomeName = (TextView) view.findViewById(R.id.usernameWelcome);
        welcomeJurusan = (TextView) view.findViewById(R.id.jurusanWelcome);
        welcomeAngkatan= (TextView) view.findViewById(R.id.angkatanWelcome);
        welcomeStatus = (TextView) view.findViewById(R.id.statusWelcome);


        mAroundMe= (Button) view.findViewById(R.id.aroundme);
        mCareer= (Button) view.findViewById(R.id.careerButton);
        allUser= (Button) view.findViewById(R.id.allUserButton);
        product = (Button) view.findViewById(R.id.trading);
//        userProfileBeta = (Button) view.findViewById(R.id.userProfileBeta);
        sharedPreferences = this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);

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

        // Set welcome text
        welcomeName.setText(sharedPreferences.getString("namaSession","nama User"));
        welcomeAngkatan.setText(sharedPreferences.getString("angkatanSession","Tahun Lulus"));
        welcomeJurusan.setText(sharedPreferences.getString("jurusanSession","Jurusan User"));
        welcomeStatus.setText(sharedPreferences.getString("statusSession","Status user"));

//        userProfileBeta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String currentUser =sharedPreferences.getString("usernameSession", "Username");
        switch (v.getId()) {
            case R.id.aroundme:
                Intent intentAroundMe=new Intent(getActivity(),AroundMeActivity.class);
                intentAroundMe.putExtra(AroundMeActivity.username, currentUser);
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
//            case R.id.userProfileBeta:
//                Intent userProfileBetaIntent = new Intent(getActivity(), UserProfileNewActivity.class);
//                startActivity(userProfileBetaIntent);
//                break;
        }
    }
}
