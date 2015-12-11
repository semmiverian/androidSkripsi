package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.AllMessageByUserInterface;
import com.skripsi.semmi.restget3.Interface.AllUserInterface;
import com.skripsi.semmi.restget3.Model.AllMessageByUser;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.AllUserProfile;
import com.skripsi.semmi.restget3.activity.MessageActivity;
import com.skripsi.semmi.restget3.adapter.AllMessageByUserAdapter;
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

    private AllMessageByUserAdapter mAdapter;
    private SharedPreferences sharedPreferences;
    private String currentUser;
    private int currentUserId;


    public static AllUserFragment getInstance(){
        AllUserFragment fragment=new AllUserFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter=new AllMessageByUserAdapter(getActivity(),0);
        setListShown(false);
        setListShown(true);
        sharedPreferences= this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        currentUserId= sharedPreferences.getInt("idSession", 0);

        getUserList(currentUserId);

    }

    private void getUserList(final int currentUserId) {

        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        Log.d("start",""+currentUserId);
        AllMessageByUserInterface allMessageByUserInterface = restAdapter.create(AllMessageByUserInterface.class);
        allMessageByUserInterface.fetchMessageByUser(currentUserId, new Callback<List<AllMessageByUser>>() {
            @Override
            public void success(List<AllMessageByUser> allMessageByUsers, Response response) {
                if (allMessageByUsers.equals("") || allMessageByUsers == null) {
                    // TODO set a adapter to show no message been started by this user
                    Log.d("no data", "no Data detected");
                    return;
                }
                for (AllMessageByUser allMessageByUser : allMessageByUsers) {
                    mAdapter.add(allMessageByUser);
                    Log.d(" data", allMessageByUser.getTo_username());
                }
                mAdapter.notifyDataSetChanged();
                setListAdapter(mAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("get Error", "from Retrofit" + error.getMessage());
                new MaterialDialog.Builder(getActivity())
                        .title("Something went wrong")
                        .content("Mohon maaf terjadi kesalahan pada penampilan data")
                        .positiveText("Muat ulang")
//                        .icon(Drawable.createFromPath(String.valueOf(R.drawable.ic_media_play)))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                getUserList(currentUserId);
                            }
                        })
                        .show();
            }
        });




    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
// Intent intent = new Intent(getActivity(), AllUserProfile.class);
//        intent.putExtra(AllUserProfile.extra,mAdapter.getItem(position));
//        startActivity(intent);ntent);
        Log.d("to_id_testing","" + mAdapter.getItem(position).getTo_id());

        Intent startMessageActivityIntent = new Intent(getActivity(), MessageActivity.class);
        startMessageActivityIntent.putExtra(MessageActivity.to_id,mAdapter.getItem(position).getTo_id());
        startActivity(startMessageActivityIntent);
    }
}
