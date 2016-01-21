package com.skripsi.semmi.restget3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.skripsi.semmi.restget3.Interface.DeleteCareerInterface;
import com.skripsi.semmi.restget3.Interface.UserProfileFromAroundMe;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.Model.DeleteData;
import com.skripsi.semmi.restget3.R;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 03/11/2015.
 */
public class UserCareerDetailActivity extends AppCompatActivity {
    public static final String extra="extra";
    private  MaterialDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ambil data yang udah di parse
        AllCareer allCareer=getIntent().getExtras().getParcelable(extra);
        // define objek yang di xml
        TextView judul= (TextView) findViewById(R.id.careerJudul);
        TextView detail= (TextView) findViewById(R.id.carerDetail);
        ImageView gambar= (ImageView) findViewById(R.id.careerImage);
//        ImageView profilepict = (ImageView) findViewById(R.id.imageProfile);
//        TextView user = (TextView) findViewById(R.id.userNameCareer);
        //  ambil data dari server terus di tampilin
        judul.setText(allCareer.getKarirnama());
        detail.setText(allCareer.getKarirdetail());
        Picasso.with(this)
                .load(allCareer.getKarirImage())
                .into(gambar);
//        user.setText(allCareer.getUserName());
//        Picasso.with(this)
//                .load(allCareer.getUserImage())
//                .into(profilepict);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_career,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        AllCareer allCareer=getIntent().getExtras().getParcelable(extra);
        String karirNama= allCareer.getKarirnama();
        String karirDetail = allCareer.getKarirdetail();
        String karirId = allCareer.getKarirId();
        String karirEmail = allCareer.getKarirEmail();
        String karirTelepon = allCareer.getKarirTelepon();
        switch (id){
            case R.id.action_delete:
                showDeleteConfirmDialog();
                break;
            case R.id.action_edit:
                editCareer(karirNama, karirDetail, karirId,karirEmail,karirTelepon);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmDialog() {
        AllCareer allCareer=getIntent().getExtras().getParcelable(extra);
        final String karirId= allCareer.getKarirId();
        new MaterialDialog.Builder(this)
                .title("Delete Karir")
                .content("Apa anda yakin untuk menghapus karir ini")
                .positiveText("ya")
                .negativeText("tidak")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        deleteCareer(karirId);
                        dialog = new MaterialDialog.Builder(UserCareerDetailActivity.this)
                                .title("Proses")
                                .content("Connecting to server")
                                .progress(true,0)
                                .show();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        materialDialog.dismiss();
                    }
                })
                .show();
    }

    private void deleteCareer(String karirId) {

        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        DeleteCareerInterface dca = restAdapter.create(DeleteCareerInterface.class);
        dca.deleteData(karirId, new Callback<DeleteData>() {
            @Override
            public void success(DeleteData deleteData, Response response) {
                dialog.setContent("Berhasil Delete Data");
                Intent userProfileIntent = new Intent(UserCareerDetailActivity.this , home_activity.class);
                userProfileIntent.putExtra("kode","delete Career");
                startActivity(userProfileIntent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Delete Error", "from Career " + error.getMessage());
            }
        });
    }

    private void editCareer(String karirNama, String karirDetail, String karirId, String karirEmail, String karirTelepon) {
        Intent editCarerIntent = new Intent(this, CareerEditActivity.class);
        editCarerIntent.putExtra(CareerEditActivity.karirNama,karirNama);
        editCarerIntent.putExtra(CareerEditActivity.karirDetail,karirDetail);
        editCarerIntent.putExtra(CareerEditActivity.karirId,karirId);
        editCarerIntent.putExtra(CareerEditActivity.karirEmail,karirEmail);
        editCarerIntent.putExtra(CareerEditActivity.karirTelepon,karirTelepon);
        startActivity(editCarerIntent);
    }




}
