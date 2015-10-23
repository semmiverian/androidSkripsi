package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skripsi.semmi.restget3.R;

/**
 * Created by semmi on 21/10/2015.
 */
public class UserProfileFragment extends Fragment {
    private TextView mUsername;
    private TextView mStatus;
    public static UserProfileFragment getInstance(){
        UserProfileFragment fragment=new UserProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_profile_fragment,container,false);
        mUsername= (TextView) view.findViewById(R.id.usernameProfile);
        mStatus= (TextView) view.findViewById(R.id.usernameStatus);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        mUsername.setText(sharedPreferences.getString("usernameSession","Username" ));
        mStatus.setText(sharedPreferences.getString("statusSession","Status"));
        return view;
    }
}
