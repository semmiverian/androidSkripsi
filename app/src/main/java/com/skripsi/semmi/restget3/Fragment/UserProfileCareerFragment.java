package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Helper.ListViewHelper;
import com.skripsi.semmi.restget3.Interface.userCareerInterface;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.AddNewCareerActivity;
import com.skripsi.semmi.restget3.activity.UserCareerDetailActivity;
import com.skripsi.semmi.restget3.adapter.UserCareerAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 29/11/2015.
 */
public class UserProfileCareerFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private UserCareerAdapter mAdapater;
    private  String user;
    private SharedPreferences sharedPreferences;
    private static final int INITIAL_DELAY_MILLIS = 300;
    private FloatingActionButton mFloatingActionButton;

    public static UserProfileCareerFragment getInstance(){
        UserProfileCareerFragment fragment=new UserProfileCareerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_career,container,false);
        listView = (ListView) view.findViewById(R.id.CareerListView);
        sharedPreferences= this.getActivity().getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        user=sharedPreferences.getString("usernameSession","Username");
        mFloatingActionButton= (FloatingActionButton) view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapater = new UserCareerAdapter(getActivity(),0);
        fetchUserCareer();

        // Design listview yang akan tampil
        ListViewHelper listViewHelper = new ListViewHelper();
        listViewHelper.googleCardslistViewDesign(getResources(),listView);

        listView.setAdapter(mAdapater);


    }



    // fungsi untuk mengambil data career dari server
    private void fetchUserCareer() {
        // REST untuk ambil karir yang di mulai oleh user
        RestAdapter restAdapter2=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        userCareerInterface userCareerInterface=restAdapter2.create(com.skripsi.semmi.restget3.Interface.userCareerInterface.class);
        userCareerInterface.getCareer(user, new Callback<List<AllCareer>>() {
            @Override
            public void success(List<AllCareer> allCareers, Response response) {
                if (allCareers == null || allCareers.isEmpty()) {
                    Toast.makeText(getActivity(), "Ga ada Career  yang di pasang", Toast.LENGTH_SHORT).show();
                   String kosong = "kosong";
                    String noneImage ="http://i.imgur.com/0hi2ZKN.png";
                    AllCareer noCareer = new AllCareer(kosong,kosong,noneImage,kosong,kosong,kosong,kosong,kosong,kosong);
                    mAdapater.add(noCareer);
                    return;
                }
                for (AllCareer allCareer : allCareers) {
                    mAdapater.add(allCareer);
                    Log.d("careerTest",allCareer.getKarirnama());
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent CareerDetailIntent= new Intent(getActivity(),UserCareerDetailActivity.class);
                        // Taruh Extra biar bisa parsing detail data dari server ke detail tampilan
                        CareerDetailIntent.putExtra(UserCareerDetailActivity.extra,mAdapater.getItem(position));
                        startActivity(CareerDetailIntent);
                    }
                });
                mAdapater.notifyDataSetChanged();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("post Error", "from Career " + error.getMessage());
                showFailureDialog();
            }
        });
    }

    // Define dialog ketika gagal ambil data dari server
    private void showFailureDialog() {
        new MaterialDialog.Builder(getActivity())
                .title("Error")
                .content("Mohon maaf terjadi kesalahan pada penampilan data")
                .positiveText("Muat ulang")
//                        .icon(Drawable.createFromPath(String.valueOf(R.drawable.ic_media_play)))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        fetchUserCareer();
                    }
                })
                .show();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:
                Intent intent = new Intent(getActivity(), AddNewCareerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
