package com.skripsi.semmi.restget3.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.skripsi.semmi.restget3.Interface.UserProfileFromAroundMe;
import com.skripsi.semmi.restget3.Model.AllCareer;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.Model.Dialog;
import com.skripsi.semmi.restget3.R;
import com.skripsi.semmi.restget3.adapter.ContactDialogAdapter;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by semmi on 03/11/2015.
 */
public class CareerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA="extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_detail);




        // ambil data yang udah di parse
        AllCareer allCareer=getIntent().getExtras().getParcelable(EXTRA);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        // define objek yang di xml
        TextView judul= (TextView) findViewById(R.id.careerJudul);
        TextView detail= (TextView) findViewById(R.id.carerDetail);
        ImageView gambar= (ImageView) findViewById(R.id.careerImage);
        ImageView profilepict = (ImageView) findViewById(R.id.imageProfile);
        TextView user = (TextView) findViewById(R.id.userNameCareer);
        //  ambil data dari server terus di tampilin
        judul.setText(allCareer.getKarirnama());
        detail.setText(allCareer.getKarirdetail());
        Picasso.with(this)
                .load(allCareer.getKarirImage())
                .into(gambar);
        user.setText(allCareer.getUserName());
        Picasso.with(this)
                .load(allCareer.getUserImage())
                .into(profilepict);


        // Set on click listener ke user profile
        profilepict.setOnClickListener(this);
        user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AllCareer allCareer=getIntent().getExtras().getParcelable(EXTRA);
        String username = allCareer.getUserName();
        String email = allCareer.getKarirEmail();
        String telepon = allCareer.getKarirTelepon();
        switch ( v.getId()){
            case R.id.imageProfile:
                goToUserProfile(username);
                break;
            case R.id.userNameCareer:
                goToUserProfile(username);
                break;
            case R.id.fab:
                showCareerContactInfo(email,telepon);
                break;
        }
    }

    private void showCareerContactInfo(final String email, final String telepon) {

        // Testing
        Dialog data1 = new Dialog(MaterialDrawableBuilder.IconValue.GMAIL,email);
        Dialog data2 = new Dialog(MaterialDrawableBuilder.IconValue.MESSAGE,telepon);

        List<Dialog> items = new ArrayList<Dialog>();
        items.add(data1);
        items.add(data2);

        ContactDialogAdapter contactDialogAdapter;
        contactDialogAdapter = new ContactDialogAdapter(this,items);

        DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setAdapter(contactDialogAdapter)
                .setContentHolder(new ListHolder())
                .setGravity(Gravity.BOTTOM)
                .setHeader(R.layout.dialog_header)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
//                                        Toast.makeText(ProductDetailActivity.this, "tes posisi" + position, Toast.LENGTH_SHORT).show();
                        if (position == 1) {


                            // Set Email Address
                            String[] addresses = new String[1];
                            addresses[0] = email;

                            Intent SendEmailIntent = new Intent(Intent.ACTION_SENDTO);
                            SendEmailIntent.setType("*/*");
                            SendEmailIntent.setData(Uri.parse("mailto:"));
                            SendEmailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
                            startActivity(SendEmailIntent);
                        }
                        if (position == 2) {
                            // Start intent buat bisa open dialer
                            Intent OpenDialerIntent = new Intent(Intent.ACTION_VIEW);
                            OpenDialerIntent.setData(Uri.parse("tel:" + telepon));
                            startActivity(OpenDialerIntent);
                        }
                    }
                })
                .create();
        dialogPlus.show();
    }


    private void goToUserProfile(String username) {

        SharedPreferences sharedPreferences= this.getSharedPreferences("Session Check", Context.MODE_PRIVATE);
        final String userLogin=sharedPreferences.getString("usernameSession", "Username");
        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        UserProfileFromAroundMe upfa = restAdapter.create(UserProfileFromAroundMe.class);
        upfa.fetchUser(username, new Callback<AllUser>() {
            @Override
            public void success(AllUser allUser, Response response) {
                if(userLogin.equals(allUser.getUsername())){
                    Intent ownProfileIntent = new Intent(CareerDetailActivity.this, home_activity.class);
                    ownProfileIntent.putExtra("code2","from career");
                    startActivity(ownProfileIntent);
                    return;
                }

                Intent userProfileIntent = new Intent(CareerDetailActivity.this, UserProfileFromAroundMeActivity.class);
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraUsername,allUser.getUsername());
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraImage,allUser.getImage());
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraStatus,allUser.getStatus());
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraNama,allUser.getNama());
                userProfileIntent.putExtra("IDValue",allUser.getId());
                startActivity(userProfileIntent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("career", "from Career detail" + error.getMessage());
            }
        });
    }


}
