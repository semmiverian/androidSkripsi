package com.skripsi.semmi.restget3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import android.view.View;

import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.AllCareerInterface;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.Helper.DateHelper;
import com.skripsi.semmi.restget3.activity.CareerDetailActivity;
import com.skripsi.semmi.restget3.adapter.AllCareerAdapater;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;


import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 01/11/2015.
 *
 */
public class CareerListFragment extends ListFragment  {
    private AllCareerAdapater mAdapater;
    public Handler handler= new Handler();;
    private Timer timer;
    public TimerTask timerTask;
    public Runnable runnable;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MaterialDialog dialog;

    public static CareerListFragment getInstance(){
        CareerListFragment fragment=new CareerListFragment();
        return fragment;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapater=new AllCareerAdapater(getActivity(),0);

        setListShown(false);
        getCareerData();
        setListShown(true);

        // set dialog proses ambil data dari server
         dialog = new MaterialDialog.Builder(getActivity())
                .title("Proses")
                .content("Mengambil data dari server")
                .progress(true,0)
                .show();


        showIntro(getListView(),"ListViewIntro2222","This is career you can Click to see the detail of the Career");
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.career_fragment, container, false);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//                getCareerData();
//            }
//        });
//    }



    //    public void timerDoTheStuff() {
//        timer = new Timer();
//        timerTask = new TimerTask() {
//
//            @Override
//            public void run() {
//               handler.post(new Runnable() {
//                   @Override
//                   public void run() {
//                       getCareerData();
//                   }
//               });
//            }
//        };
//
//        timer.schedule(timerTask, 2000, 500);
//    }


    @Override
    public void onResume() {
        super.onResume();
        getCareerData();
    }



    public void getCareerData() {
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        AllCareerInterface allCareerInterface= restAdapter.create(AllCareerInterface.class);
        allCareerInterface.getCareer(new Callback<List<AllCareer>>() {
            @Override
            public void success(List<AllCareer> allCareers, Response response) {
                // ga bakal isi apa apa kalau ga ada data yang dateng
                if (allCareers == null || allCareers.isEmpty()) {
                    return;
                }
                mAdapater.clear();
                for (AllCareer allCareer : allCareers) {
                    // parse data yang diambil dari server ke adapter
                    // dari adapter nanti bakal ditampilin ke aplikasi

                    mAdapater.add(allCareer);
//                    Log.d("Sukses", allCareer.getKarirCreate());
                    DateHelper dateHelper = new DateHelper();
                    String dateTest = dateHelper.changeDateFormat(allCareer.getKarirCreate());
                    Log.d("Sukses", allCareer.getKarirnama());
                }

                ListView listView = getListView();
                listView.invalidate();
                // pasang ini biar bisa nge detek kalau ada data yang berubah
                mAdapater.notifyDataSetChanged();
                setListAdapter(mAdapater);

                // kalau udah berhasil maka dialog awal bakal ilang
                dialog.setContent("Data berhasil di ambil");
                dialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("get Error", "from Retrofit" + error.getMessage());
                dialog.dismiss();
                new MaterialDialog.Builder(getActivity())
                        .title("Something went wrong")
                        .content("Mohon maaf terjadi kesalahan pada penampilan data")
                        .positiveText("Muat ulang")
//                        .icon(Drawable.createFromPath(String.valueOf(R.drawable.ic_media_play)))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                getCareerData();
                            }
                        })
                        .show();
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
        CareerDetailIntent.putExtra(CareerDetailActivity.EXTRA,mAdapater.getItem(position));
        startActivity(CareerDetailIntent);
    }

    private void showIntro(View view,String ID,String content){
        new MaterialIntroView.Builder(getActivity())
//                .enableDotAnimation(true)
                //.enableIcon(false)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.MINIMUM)
                .setDelayMillis(500)
                .enableFadeAnimation(true)
                .performClick(true)
                .setInfoText(content)
                .setTarget(view)
                .setUsageId(ID) //THIS SHOULD BE UNIQUE ID
                .show();
    }
}
