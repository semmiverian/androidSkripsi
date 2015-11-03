package com.skripsi.semmi.restget3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.skripsi.semmi.restget3.Interface.AllCareerInterface;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.CareerDetailActivity;
import com.skripsi.semmi.restget3.adapter.AllCareerAdapater;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 01/11/2015.
 */
public class CareerListFragment extends ListFragment {
    private AllCareerAdapater mAdapater;
    public static CareerListFragment getInstance(){
        CareerListFragment fragment=new CareerListFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(false);
        mAdapater=new AllCareerAdapater(getActivity(),0);
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        AllCareerInterface allCareerInterface= restAdapter.create(AllCareerInterface.class);
        allCareerInterface.getCareer(new Callback<List<AllCareer>>() {
            @Override
            public void success(List<AllCareer> allCareers, Response response) {
                // ga bakal isi apa apa kalau ga ada data yang dateng
                if(allCareers==null || allCareers.isEmpty()){
                    return;
                }
                for(AllCareer allCareer : allCareers){
                    // parse data yang diambil dari server ke adapter
                    // dari adapter nanti bakal ditampilin ke aplikasi
                    mAdapater.add(allCareer);
                    Log.d("Sukses",allCareer.getNama());
                }
                // pasang ini biar bisa nge detek kalau ada data yang berubah
                mAdapater.notifyDataSetChanged();
                setListAdapter(mAdapater);
                setListShown(true);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("get Error", "from Retrofit" + error.getMessage());
            }
        });
    }

    // Fungsi ini akan dijalankan ketika user me klik salah satu item yang berada pada list view
    // Dan akan menampilkan Detail dari karir yang di klik oleh user
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // buat intent untuk menampilkan detail user
        Intent CareerDetailIntent= new Intent(getActivity(),CareerDetailActivity.class);
        // Taruh Extra biar bisa parsing detail data dari server ke detail tampilan
        CareerDetailIntent.putExtra(CareerDetailActivity.extra,mAdapater.getItem(position));
        startActivity(CareerDetailIntent);

    }
}
