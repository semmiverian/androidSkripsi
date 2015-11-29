package com.skripsi.semmi.restget3.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.UserCareerInterface;
import com.skripsi.semmi.restget3.Model.Career;
import com.skripsi.semmi.restget3.R;
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

    public UserProfileCareerFragment(){}

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
        mAdapater = new UserCareerAdapter(this.getContext(),0);
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

    }

    // fungsi untuk mengambil data career dari server
    private void fetchUserCareer() {
        // REST untuk ambil karir yang di mulai oleh user
        RestAdapter restAdapter2=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserCareerInterface userCareerInterface=restAdapter2.create(UserCareerInterface.class);
        userCareerInterface.getCareer(user, new Callback<List<Career>>() {
            @Override
            public void success(List<Career> careers, Response response) {
                Log.d("berhasil catch", "berhasil gan daru career");
                if (careers == null || careers.isEmpty()) {
                    Toast.makeText(getActivity(), "Ga ada Career  yang di pasang", Toast.LENGTH_SHORT).show();
                }
                for (Career career : careers) {
                    mAdapater.add(career);
                    Log.d("careerTest",career.getKarirNama());
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
