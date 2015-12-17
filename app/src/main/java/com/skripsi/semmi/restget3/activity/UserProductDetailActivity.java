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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.skripsi.semmi.restget3.Interface.DeleteProdukInterface;
import com.skripsi.semmi.restget3.Interface.UserProfileFromAroundMe;
import com.skripsi.semmi.restget3.Model.AllProduct;
import com.skripsi.semmi.restget3.Model.AllUser;
import com.skripsi.semmi.restget3.Model.DeleteData;
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
public class UserProductDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String extra="extra";
    private MaterialDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_detail);

        // ambil data yang udah di parse
        AllProduct allProduct=getIntent().getExtras().getParcelable(extra);


        // New Design
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);


        // define objek yang di xml
        TextView judul= (TextView) findViewById(R.id.produkJudul);
        TextView detail= (TextView) findViewById(R.id.produkDetail);
        ImageView gambar= (ImageView) findViewById(R.id.produkImage);
        ImageView profilepict = (ImageView) findViewById(R.id.imageProfile);
        TextView user = (TextView) findViewById(R.id.userNameProduk);

        //  ambil data dari server terus di tampilin
        judul.setText(allProduct.getProduknama());
        detail.setText(allProduct.getProdukdetail());
        Picasso.with(this)
                .load(allProduct.getProdukImage())
                .into(gambar);
        user.setText(allProduct.getUserName());
        Picasso.with(this)
                .load(allProduct.getUserImage())
                .into(profilepict);

        // set onclik listener
        profilepict.setOnClickListener(this);
        user.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        AllProduct allProduct=getIntent().getExtras().getParcelable(extra);
        String username = allProduct.getUserName();
        String email = allProduct.getProdukEmail();
        String telepon = allProduct.getProdukTelepon();
        switch( v.getId()){
            case R.id.imageProfile:
                goToUserProfile(username);
                break;
            case R.id.userNameProduk:
                goToUserProfile(username);
                break;
            case R.id.fab:
                showContactDialog(email ,telepon);
                break;
        }
    }

    private void showContactDialog(final String email, final String telepon) {
        // Show dialog on bottom about contact info


        // Testing
        Dialog data1 = new Dialog(MaterialDrawableBuilder.IconValue.GMAIL,email);
        Dialog data2 = new Dialog(MaterialDrawableBuilder.IconValue.PHONE_INCOMING,telepon);

        final List<Dialog> items = new ArrayList<Dialog>();
        items.add(data1);
        items.add(data2);

        // Set the adapter for dialog
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
                                            // Start intent to send email to the user
//                                            Intent SendEmailIntent = new Intent(ProductDetailActivity.this, ComposeEmailActivity.class);
//                                            SendEmailIntent.putExtra(ComposeEmailActivity.emailAdressExtra, email);
//                                            startActivity(SendEmailIntent);

                                            // Set Email Address
                                            String[] addresses= new String[1];
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
                                            OpenDialerIntent.setData(Uri.parse("tel:"+telepon));
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
                if (userLogin.equals(allUser.getUsername())) {
                    //  if the user who logged in click  the profile need to go to the user profile page instead of user profile for other user
                    Intent ownProfileIntent = new Intent(UserProductDetailActivity.this, UserProfileNewActivity.class);
                    startActivity(ownProfileIntent);
                    return;
                }
                Intent userProfileIntent = new Intent(UserProductDetailActivity.this, UserProfileFromAroundMeActivity.class);
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraUsername, allUser.getUsername());
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraImage, allUser.getImage());
                userProfileIntent.putExtra(UserProfileFromAroundMeActivity.extraStatus, allUser.getStatus());
                userProfileIntent.putExtra("IDValue", allUser.getId());
                startActivity(userProfileIntent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("career", "from Career detail" + error.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_produk, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AllProduct allProduct=getIntent().getExtras().getParcelable(extra);
        String produkId = allProduct.getProdukId();
        String produkNama = allProduct.getProduknama();
        String produkDetail = allProduct.getProdukdetail();
        String produkEmail = allProduct.getProdukEmail();
        String produkTelepon = allProduct.getProdukTelepon();
        int id = item.getItemId();
        switch(id){
            case R.id.action_delete:
                showDeleteConfirmationDialog(produkId);
                break;
            case R.id.action_edit:
                startEditProductActivity(produkId,produkNama,produkDetail,produkEmail,produkTelepon);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void startEditProductActivity(String produkId, String produkNama, String produkDetail, String produkEmail, String produkTelepon) {
        Intent editProdukIntent = new Intent(this, ProductEditActivity.class);
        editProdukIntent.putExtra(ProductEditActivity.produkNama,produkNama);
        editProdukIntent.putExtra(ProductEditActivity.produkDetail,produkDetail);
        editProdukIntent.putExtra(ProductEditActivity.produkId,produkId);
        editProdukIntent.putExtra(ProductEditActivity.produkEmail,produkEmail);
        editProdukIntent.putExtra(ProductEditActivity.produkTelepon,produkTelepon);
        startActivity(editProdukIntent);

    }

    private void showDeleteConfirmationDialog(final String produkId) {
        new MaterialDialog.Builder(this)
                .title("Delete Produk")
                .content("Apa anda yakin untuk menghapus produk ini")
                .positiveText("ya")
                .negativeText("tidak")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        deleteCareer(produkId);
                        dialog = new MaterialDialog.Builder(UserProductDetailActivity.this)
                                .title("Proses")
                                .content("Connecting to server")
                                .progress(true, 0)
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

    private void deleteCareer(String produkId) {


        RestAdapter restAdapter=new RestAdapter.Builder()
                .setEndpoint(getString(R.string.api))
                .build();
        DeleteProdukInterface dpi = restAdapter.create(DeleteProdukInterface.class);
        dpi.deleteProduk(produkId, new Callback<DeleteData>() {
            @Override
            public void success(DeleteData deleteData, Response response) {
                dialog.setContent("Berhasil Delete Data");
                Intent deleteProductIntent = new Intent(UserProductDetailActivity.this , home_activity.class);
                deleteProductIntent.putExtra("code3","code from delete produk");
                startActivity(deleteProductIntent);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Delete Error", "from Product " + error.getMessage());
            }
        });
    }
}
