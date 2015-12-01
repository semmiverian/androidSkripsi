package com.skripsi.semmi.restget3.Fragment;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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
import com.skripsi.semmi.restget3.Interface.UserCareerInterface;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.Model.Career;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.activity.CareerDetailActivity;
import com.skripsi.semmi.restget3.activity.ProductDetailActivity;
import com.skripsi.semmi.restget3.activity.UserCareerDetailActivity;
import com.skripsi.semmi.restget3.activity.UserProfileNewActivity;
import com.skripsi.semmi.restget3.adapter.UserCareerAdapter;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 29/11/2015.
 */
public class UserProfileCareerFragment extends Fragment {

    private ListView listView;
    private UserCareerAdapter mAdapater;
    private  String user;
    private SharedPreferences sharedPreferences;
    private static final int INITIAL_DELAY_MILLIS = 300;

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
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapater = new UserCareerAdapter(getActivity(),0);
        fetchUserCareer();

        // Design listview yang akan tampil
        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setAdapter(mAdapater);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent CareerDetailIntent= new Intent(getActivity(),UserCareerDetailActivity.class);
                // Taruh Extra biar bisa parsing detail data dari server ke detail tampilan
                CareerDetailIntent.putExtra(UserCareerDetailActivity.extra,mAdapater.getItem(position));
                startActivity(CareerDetailIntent);
            }
        });

    }



    // fungsi untuk mengambil data career dari server
    private void fetchUserCareer() {
        // REST untuk ambil karir yang di mulai oleh user
        RestAdapter restAdapter2=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserCareerInterface userCareerInterface=restAdapter2.create(UserCareerInterface.class);
        userCareerInterface.getCareer(user, new Callback<List<AllCareer>>() {
            @Override
            public void success(List<AllCareer> allCareers, Response response) {
                if (allCareers == null || allCareers.isEmpty()) {
                    Toast.makeText(getActivity(), "Ga ada Career  yang di pasang", Toast.LENGTH_SHORT).show();
                }
                for (AllCareer allCareer : allCareers) {
                    mAdapater.add(allCareer);
                    Log.d("careerTest",allCareer.getKarirnama());
                }
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



}
