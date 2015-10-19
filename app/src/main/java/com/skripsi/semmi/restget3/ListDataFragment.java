package com.skripsi.semmi.restget3;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 12/10/2015.
 */
public class ListDataFragment extends ListFragment {
    private dataAdapter mAdapter;
    public static ListDataFragment getInstance(){
        ListDataFragment fragment=new ListDataFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListShown(false);
        mAdapter=new dataAdapter(getActivity(),0);
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        ApiInterface apiInterface=restAdapter.create(ApiInterface.class);
        apiInterface.getStreams(new Callback<List<Data>>() {
            @Override
            public void success(List<Data> datas, Response response) {
                if (datas == null || datas.isEmpty()) {
                    return;
                }

                for (Data data : datas) {
                    Log.e("testing",data.getJudul());
                    Log.e("testing2",data.getDeskripsi());
                    mAdapter.add(data);
                }
                mAdapter.notifyDataSetChanged();
                setListAdapter(mAdapter);
                setListShown(true);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error", "Error" + error);
            }
        });
    }
}
