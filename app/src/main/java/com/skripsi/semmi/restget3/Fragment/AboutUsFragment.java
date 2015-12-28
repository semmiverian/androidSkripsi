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
import android.widget.TextView;

import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.AllProductActivity;
import com.skripsi.semmi.restget3.activity.AllUserActivity;
import com.skripsi.semmi.restget3.activity.AroundMeActivity;
import com.skripsi.semmi.restget3.activity.CareerActivity;

/**
 * Created by semmi on 01/12/2015.
 */
public class AboutUsFragment extends Fragment  {

    public static AboutUsFragment getInstance(){
        AboutUsFragment fragment=  new AboutUsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us_fragment,container,false);
        return view;
    }

}
