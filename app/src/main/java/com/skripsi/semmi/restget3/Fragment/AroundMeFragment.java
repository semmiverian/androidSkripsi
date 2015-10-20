package com.skripsi.semmi.restget3.Fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by semmi on 19/10/2015.
 */
public class AroundMeFragment extends SupportMapFragment {
    public static AroundMeFragment getInstance(){
        AroundMeFragment fragment=new AroundMeFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        CameraPosition position=CameraPosition.builder()
//                .target(new LatLng(39.7500,-104.9500))
//                .zoom(16f)
//                .tilt(0.0f)
//                .bearing(0.0f)
//                .build();
//        getMap().animateCamera(CameraUpdateFactory.newCameraPosition(position),null);
//        getMap().setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}
