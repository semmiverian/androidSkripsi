package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.skripsi.semmi.restget3.Interface.AllUserInterface;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.AllUserProfile;
import com.skripsi.semmi.restget3.adapter.AllUserAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 09/11/2015.
 */
public class AllUserFragment extends ListFragment {
    private AllUserAdapter mAdapter;
    private SharedPreferences sharedPreferences;
    private String currentUser;
    public static AllUserFragment getInstance(){
        AllUserFragment fragment=new AllUserFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter=new AllUserAdapter(getActivity(),0);
        setListShown(false);
        getUserList();
        setListShown(true);
        sharedPreferences= this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        currentUser= sharedPreferences.getString("usernameSession","Username" );
    }

    private void getUserList() {
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        AllUserInterface allUserInterface= restAdapter.create(AllUserInterface.class);
        allUserInterface.getAllUser(new Callback<List<AllUser>>() {
            @Override
            public void success(List<AllUser> allUsers, Response response) {
                if(allUsers == null || allUsers.isEmpty()){
                    return;
                }
                for(AllUser allUser : allUsers){
                    // cek siapa yang lagi login
                    // ga usah di tampilin di all user kalau dia yang login
                    if(! allUser.getUsername().equals(currentUser)){
                        mAdapter.add(allUser);
                    }

                }
                mAdapter.notifyDataSetChanged();
                setListAdapter(mAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("get Error", "from Retrofit" + error.getMessage());
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), AllUserProfile.class);
        intent.putExtra(AllUserProfile.extra,mAdapter.getItem(position));
        startActivity(intent);
    }
}
